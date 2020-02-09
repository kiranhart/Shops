package com.kiranhart.shops.events;

import com.kiranhart.shops.HartInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class HartInventoryListener implements Listener {

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof HartInventory) {
            HartInventory gui = (HartInventory) e.getInventory().getHolder();
            gui.onOpen(e);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof HartInventory) {
            HartInventory gui = (HartInventory) e.getInventory().getHolder();
            gui.onClick(e);
            gui.onClick(e, e.getRawSlot());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof HartInventory) {
            HartInventory gui = (HartInventory) e.getInventory().getHolder();
            gui.onClose(e);
        }
    }
}