package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.inventories.EditInventory;
import com.kiranhart.shops.inventories.ShopContentsInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/25/2020
 * Time Created: 12:46 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ContentsCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        if (!ShopAPI.get().hasPerm(p, ShopPerm.CMD_ALL, ShopPerm.CMD_CONTENTS)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_CONTENTS).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 2) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }

            Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1])).findFirst().ifPresent(found -> {
                p.openInventory(new ShopContentsInventory(found).getInventory());
            });
        }
    }

    @Override
    public String name() {
        return "contents";
    }

    @Override
    public String[] aliases() {
        return new String[]{"editcontents"};
    }
}
