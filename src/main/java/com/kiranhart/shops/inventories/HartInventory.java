package com.kiranhart.shops.inventories;

import com.kiranhart.shops.util.Debugger;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.IntStream;

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

    public void onClick(InventoryClickEvent e) {
    }

    public void onClick(InventoryClickEvent e, int slot) {
    }

    public void onOpen(InventoryOpenEvent e) {
    }

    public void onClose(InventoryCloseEvent e) {
    }

    protected void setTitle(String title) {
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', title);
    }

    public void setSize(int size) {
        this.defaultSize = size;
    }

    public void setRows(int rows) {
        switch (rows) {
            case 1:
                setSize(9);
                break;
            case 2:
                setSize(18);
                break;
            case 3:
                setSize(27);
                break;
            case 4:
                setSize(36);
                break;
            case 5:
                setSize(45);
                break;
            case 6:
                setSize(54);
                break;
        }
    }

    protected HartInventory setPage(int page) {
        if (this.page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
        return this;
    }

    protected void fillRow(Inventory inventory, ItemStack stack, int row) {
        switch (row) {
            case 1:
                IntStream.rangeClosed(0, 8).forEach(slot -> inventory.setItem(slot, stack));
                break;
            case 2:
                IntStream.rangeClosed(9, 17).forEach(slot -> inventory.setItem(slot, stack));
                break;
            case 3:
                IntStream.rangeClosed(18, 26).forEach(slot -> inventory.setItem(slot, stack));
                break;
            case 4:
                IntStream.rangeClosed(27, 25).forEach(slot -> inventory.setItem(slot, stack));
                break;
            case 5:
                IntStream.rangeClosed(36, 44).forEach(slot -> inventory.setItem(slot, stack));
                break;
            case 6:
                IntStream.rangeClosed(45, 53).forEach(slot -> inventory.setItem(slot, stack));
                break;
        }
    }

    protected void fill(Inventory inventory, ItemStack stack) {
        IntStream.range(0, inventory.getSize()).forEach(slot -> inventory.setItem(slot, stack));
    }

    protected void fillRange(Inventory inventory, ItemStack stack, int start, int end) {
        IntStream.range(start, end).forEach(slot -> inventory.setItem(slot, stack));
    }

    protected void multiFill(Inventory inventory, ItemStack stack, int... slots) {
        IntStream.of(slots).forEach(slot -> inventory.setItem(slot, stack));
    }

    protected void mirrorFill(Inventory inventory, ItemStack stack, int leftCorner) {
        Arrays.asList(leftCorner, leftCorner + 8).forEach(slot -> inventory.setItem(slot, stack));
    }

    protected void multiMirrorFill(Inventory inventory, ItemStack stack, int... corners) {
        for (int corner : corners) {
            Arrays.asList(corner, corner + 8).forEach(slot -> inventory.setItem(slot, stack));
        }
    }

    public static class HartInventoryListeners implements Listener {

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
}
