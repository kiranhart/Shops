package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import org.bukkit.command.CommandSender;

;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/27/2020
 * Time Created: 5:21 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class BuyOnlyCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_BUY_ONLY)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_BUY_ONLY).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 2) {

            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }

            Core.getInstance().getShopsFile().getConfig().set("shops." + args[1].toLowerCase() + ".buyonly", !ShopAPI.get().isBuyOnly(args[1].toLowerCase()));
            Core.getInstance().getShopsFile().saveConfig();
            Core.getInstance().getLocale().getMessage((ShopAPI.get().isBuyOnly(args[1].toLowerCase())) ? ShopLang.SHOP_BUY_ONLY_ON : ShopLang.SHOP_BUY_ONLY_OFF).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
        }
    }

    @Override
    public String name() {
        return "buyonly";
    }

    @Override
    public String[] aliases() {
        return new String[]{"setbuyonly", "togglebuyonly"};
    }
}
