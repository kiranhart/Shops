package com.kiranhart.shops.commands.subcommands;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:20 a.m.
*/

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.api.events.ShopCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CreateCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_CREATE))  {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_CREATE).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 2) {
            if (ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_EXISTS).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                return;
            }

            ShopCreateEvent shopCreateEvent = new ShopCreateEvent(sender, args[1]);
            Bukkit.getPluginManager().callEvent(shopCreateEvent);

            if (!shopCreateEvent.isCancelled()) {
                ShopAPI.get().createNewShop(args[1]);
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_CREATED).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
            }
        }
    }

    @Override
    public String name() {
        return "create";
    }

    @Override
    public String[] aliases() {
        return new String[] {"make", "newshop", "new"};
    }
}
