package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.inventories.shop.ShopInventory;
import org.bukkit.Bukkit;
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

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_OPEN))  {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_OPEN).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 2) {
            if(!(sender instanceof Player)) {
                Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
                return;
            }

            Player p = (Player) sender;

            if (ShopAPI.get().anyShopsExists()) {

                if (ShopAPI.get().doesShopExistsOnFile(args[1].toLowerCase())) {
                    if (!ShopAPI.get().doesShopHaveItems(args[1])) {
                        Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NO_ITEMS).sendPrefixedMessage(sender);
                    }

                    p.openInventory(new ShopInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1])).findFirst().get()).getInventory());
                } else {
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                }

            } else {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NONE).sendPrefixedMessage(p);
            }
        }

        if (args.length == 3) {

            if (!ShopAPI.get().hasPerm(sender, ShopPerm.ADMIN))  {
                Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
                return;
            }
            
            if (ShopAPI.get().anyShopsExists()) {

                if (ShopAPI.get().doesShopExistsOnFile(args[1].toLowerCase())) {
                    if (!ShopAPI.get().doesShopHaveItems(args[1])) {
                        Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NO_ITEMS).sendPrefixedMessage(sender);
                    }

                    Player target = Bukkit.getPlayerExact(args[2]);
                    if (target != null) {
                        target.openInventory(new ShopInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(args[1])).findFirst().get()).getInventory());
                    } else {
                        Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_OFFLINE).sendPrefixedMessage(sender);
                    }
                } else {
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(sender);
                }

            } else {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_NONE).sendPrefixedMessage(sender);
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
