package com.kiranhart.shops.commands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.subcommands.*;
import com.kiranhart.shops.inventories.SelectShopInventory;
import com.kiranhart.shops.util.Debugger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:09 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandManager implements CommandExecutor {

    private ArrayList<Subcommand> commands = new ArrayList<>();

    private final String MAIN = "shops";

    public void init() {
        Core.getInstance().getCommand(MAIN).setExecutor(this);
        commands.add(new HelpCommand());
        commands.add(new CreateCommand());
        commands.add(new RemoveCommand());
        commands.add(new ListCommand());
        commands.add(new EditCommand());
        commands.add(new AddItemCommand());
        commands.add(new OpenCommand());
        commands.add(new SetIconCommand());
        commands.add(new BuyOnlyCommand());
        commands.add(new SetnameCommand());
        commands.add(new ContentsCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(ShopPerm.BASE)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return true;
        }

        if (command.getName().equalsIgnoreCase(MAIN)) {

            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (ShopAPI.get().anyShopsExists()) {
                        if (Core.getInstance().getShops().size() != 0) {
                            p.openInventory(new SelectShopInventory().getInventory());
                        } else {
                            Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NONE).sendPrefixedMessage(p);
                        }
                    } else {
                        Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NONE).sendPrefixedMessage(p);
                    }
                } else {
                    Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_HELP).sendPrefixedMessage(sender);
                }
                return true;
            }

            Subcommand target = this.getSubcommand(args[0]);

            if (target == null) {
                Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_INVALID).sendPrefixedMessage(sender);
                return true;
            }

            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(args));
            list.remove(0);

            try {
                target.onCommand(sender, args);
            } catch (Exception e) {
                Debugger.report(e, false);
            }
        }

        return true;
    }

    private Subcommand getSubcommand(String name) {
        Iterator<Subcommand> subcommands = this.commands.iterator();
        while (subcommands.hasNext()) {
            Subcommand sc = subcommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int i = 0; i < length; ++i) {
                String alias = aliases[i];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }
}
