package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.inventories.EditListInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/10/2020
 * Time Created: 6:27 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ListCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        if (!ShopAPI.get().hasPerm(p, ShopPerm.CMD_ALL, ShopPerm.CMD_LIST)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(p);
            return;
        }

        p.openInventory(new EditListInventory().getInventory());
    }

    @Override
    public String name() {
        return "list";
    }

    @Override
    public String[] aliases() {
        return new String[] {"listall"};
    }
}
