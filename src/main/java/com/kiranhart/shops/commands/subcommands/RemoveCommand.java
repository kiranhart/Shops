package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.api.events.ShopRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/10/2020
 * Time Created: 6:34 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class RemoveCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_REMOVE)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_REMOVE).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 2) {
            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }

            ShopRemoveEvent removeEvent = new ShopRemoveEvent(sender, args[1]);
            Bukkit.getPluginManager().callEvent(removeEvent);

            if (!removeEvent.isCancelled()) {
                ShopAPI.get().removeShop(args[1]);
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_REMOVED).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
            }
        }
    }

    @Override
    public String name() {
        return "remove";
    }

    @Override
    public String[] aliases() {
        return new String[]{"delete"};
    }
}
