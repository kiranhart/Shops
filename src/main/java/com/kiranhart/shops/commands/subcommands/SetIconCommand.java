package com.kiranhart.shops.commands.subcommands;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/27/2020
 * Time Created: 5:21 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class SetIconCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_SET_ICON)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        if (args.length == 1) {
            Core.getInstance().getLocale().getMessage(ShopLang.COMMAND_ARGS_SETICON).sendPrefixedMessage(p);
            return;
        }

        if (args.length == 2) {

            if (!ShopAPI.get().doesShopExistsOnFile(args[1])) {
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_INVALID).processPlaceholder("shopname", args[1]).sendPrefixedMessage(p);
                return;
            }

            if (ShopAPI.get().isAir(ShopAPI.get().getItemInHand(p))) {
                Core.getInstance().getLocale().getMessage(ShopLang.AIR).sendPrefixedMessage(p);
                return;
            }

            ShopAPI.get().setShopIcon(args[1].toLowerCase(), ShopAPI.get().getItemInHand(p));
            Core.getInstance().getLocale().getMessage(ShopLang.SHOP_ICON_UPDATED).processPlaceholder("item", StringUtils.capitalize(XMaterial.matchXMaterial(ShopAPI.get().getItemInHand(p)).parseMaterial().name().replace("_", " ").toLowerCase())).sendPrefixedMessage(p);
        }
    }

    @Override
    public String name() {
        return "seticon";
    }

    @Override
    public String[] aliases() {
        return new String[]{"icon", "changeicon", "updateicon"};
    }
}
