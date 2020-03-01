package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.inventories.ShopInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/13/2020
 * Time Created: 6:55 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class OpenCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_OPEN).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 2) {

            if (ShopAPI.get().anyShopsExists()) {

                if (ShopAPI.get().doesShopExistsOnFile(args[1].toLowerCase())) {
                    if (!ShopAPI.get().doesShopHaveItems(args[1])) {
                        Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NO_ITEMS).sendPrefixedMessage(p);
                    }

                    p.openInventory(new ShopInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1])).findFirst().get()).getInventory());
                } else {
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                }

            } else {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NONE).sendPrefixedMessage(p);
            }
        }

    }

    @Override
    public String name() {
        return "open";
    }

    @Override
    public String[] aliases() {
        return new String[]{"view"};
    }
}