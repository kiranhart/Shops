package com.kiranhart.shops.commands.subcommands;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/12/2020
 * Time Created: 2:53 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class AddItemCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        if (!ShopAPI.get().hasPerm(p, ShopPerm.CMD_ALL, ShopPerm.CMD_ADDITEM)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_ADDITEM).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 2) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                return;
            }
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_ADDITEM).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 3) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                return;
            }

            if (!ShopAPI.get().isDouble(args[2])) {
                Core.getInstance().getLocale().getMessage(ShopLang.NOT_A_NUMBER).sendPrefixedMessage(p);
                return;
            }

            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_ADDITEM).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 4) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                return;
            }

            if (!ShopAPI.get().isDouble(args[2])) {
                Core.getInstance().getLocale().getMessage(ShopLang.NOT_A_NUMBER).sendPrefixedMessage(p);
                return;
            }

            if (!ShopAPI.get().isDouble(args[3])) {
                Core.getInstance().getLocale().getMessage(ShopLang.NOT_A_NUMBER).sendPrefixedMessage(p);
                return;
            }

            if (ShopAPI.get().isAir(ShopAPI.get().getItemInHand(p))) {
                Core.getInstance().getLocale().getMessage(ShopLang.AIR).sendPrefixedMessage(p);
                return;
            }

            ShopAPI.get().addShopItem(args[1].toLowerCase(), ShopAPI.get().getItemInHand(p), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1])).findFirst().get().updateItems();
        }
    }

    @Override
    public String name() {
        return "additem";
    }

    @Override
    public String[] aliases() {
        return new String[] {"newitem", "add"};
    }
}
