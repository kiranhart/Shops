package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 3/22/2020
 * Time Created: 12:53 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class SetnameCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_SETNAME))  {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_SETNAME).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 2) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }
            return;
        }

        if (args.length >= 3) {

            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                builder.append(args[i] + " ");
            }

            Core.getInstance().getShopsFile().getConfig().set("shops." + args[1].toLowerCase() + ".title", builder.toString().trim());
            Core.getInstance().getShopsFile().saveConfig();
            Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1].toLowerCase())).findFirst().get().update();
            Core.getInstance().getLocale().getMessage(ShopLang.SHOP_SETNAME).processPlaceholder("shopname", builder.toString().trim()).sendPrefixedMessage(sender);
        }
    }

    @Override
    public String name() {
        return "setname";
    }

    @Override
    public String[] aliases() {
        return new String[] {"rename", "updatename"};
    }
}
