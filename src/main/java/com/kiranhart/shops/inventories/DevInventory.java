package com.kiranhart.shops.inventories;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:05 a.m.
*/

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.api.HartInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class DevInventory extends HartInventory {

    public DevInventory() {
        this.defaultSize = 54;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', "&b&lDev Inventory");
        this.page = 1;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {

    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        inventory.setItem(1, XMaterial.PAPER.parseItem());
        return inventory;
    }
}
