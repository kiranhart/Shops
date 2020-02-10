package com.kiranhart.shops.commands.subcommands;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:03 a.m.
*/

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.commands.Subcommand;
import com.kiranhart.shops.inventories.DevInventory;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getInstance().getLocale().getMessage(ShopLang.PLAYER_ONLY).sendPrefixedMessage(sender);
            return;
        }

        Player p = (Player) sender;

        p.openInventory(new DevInventory().getInventory());
 }

    @Override
    public String name() {
        return "default";
    }

    @Override
    public String[] aliases() {
        return new String[]{"dev"};
    }
}
