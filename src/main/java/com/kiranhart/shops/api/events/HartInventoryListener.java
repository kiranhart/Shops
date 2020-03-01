package com.kiranhart.shops.api.events;

import com.kiranhart.shops.inventories.HartInventory;
import com.kiranhart.shops.util.Debugger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class HartInventoryListener implements Listener {

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        try {
            if (e.getInventory().getHolder() instanceof HartInventory) {
                HartInventory gui = (HartInventory) e.getInventory().getHolder();
                gui.onOpen(e);
            }
        } catch (Exception ex) {
            Debugger.report(ex, false);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            if (e.getInventory().getHolder() instanceof HartInventory) {
                HartInventory gui = (HartInventory) e.getInventory().getHolder();
                gui.onClick(e);
                gui.onClick(e, e.getRawSlot());
            }
        } catch (Exception ex) {
            Debugger.report(ex, false);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        try {
            if (e.getInventory().getHolder() instanceof HartInventory) {
                HartInventory gui = (HartInventory) e.getInventory().getHolder();
                gui.onClose(e);
            }
        } catch (Exception ex) {
            Debugger.report(ex, false);
        }
    }
}