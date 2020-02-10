package com.kiranhart.shops.inventories;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:31 a.m.
*/

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.HartInventory;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.BorderNumbers;
import com.kiranhart.shops.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EditListInventory extends HartInventory {

    public EditListInventory() {
        this.page = 1;
        this.defaultSize =  54;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.edit-list.title"));
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        // setup the border
        ItemStack border = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.edit-list.border")).get().parseItem();
        BorderNumbers.FIFTY_FOUR.getSlots().forEach(slot -> inventory.setItem(slot, border));

        // setup the pagination stuff
        inventory.setItem(48, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.previous-page"));
        inventory.setItem(49, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.close-item"));
        inventory.setItem(50, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.next-page"));



        return inventory;
    }
}
