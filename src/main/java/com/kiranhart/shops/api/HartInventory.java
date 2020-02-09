package com.kiranhart.shops.api;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/8/2020
 * Time Created: 11:45 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public abstract class HartInventory implements InventoryHolder {

    protected String defaultTitle = ChatColor.translateAlternateColorCodes('&', "&ebDefault Title");
    protected int defaultSize = 54;
    protected int page = 1;

    public void onClick(InventoryClickEvent e) {}

    public void onClick(InventoryClickEvent e, int slot) {}

    public void onOpen(InventoryOpenEvent e) {}

    public void onClose(InventoryCloseEvent e) {}

    protected HartInventory setPage(int page) {
        if (this.page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
        return this;
    }
}

