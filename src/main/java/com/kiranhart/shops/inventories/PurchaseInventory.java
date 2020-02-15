package com.kiranhart.shops.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.HartInventory;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.BorderNumbers;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.shop.ShopItem;
import com.kiranhart.shops.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/14/2020
 * Time Created: 5:23 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PurchaseInventory extends HartInventory {

    private ShopItem shopItem;
    private int total;

    public PurchaseInventory(ShopItem shopItem, int total) {
        this.shopItem = shopItem;
        this.total = (total <= 0) ? 1 : total;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.purchase-inventory.title"));
        this.defaultSize = 54;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        switch (slot) {
            case 20:
                p.openInventory(new PurchaseInventory(this.shopItem, this.total + 1).getInventory());
                break;
            case 29:
                p.openInventory(new PurchaseInventory(this.shopItem, this.total + 5).getInventory());
                break;
            case 24:
                p.openInventory(new PurchaseInventory(this.shopItem, this.total - 1).getInventory());
                break;
            case 33:
                p.openInventory(new PurchaseInventory(this.shopItem, this.total - 5).getInventory());
                break;
            case 31:
                if (Core.getInstance().getEconomy().getBalance(p) >= shopItem.getBuyPrice() * this.total) {
                    Core.getInstance().getEconomy().withdrawPlayer(p, shopItem.getBuyPrice() * this.total);
                    ShopAPI.get().performPurchase(p, shopItem, total);
                }
                break;
            case 49:
                p.closeInventory();
                break;
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        ItemStack border = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.purchase-inventory.border-item")).get().parseItem();
        BorderNumbers.FIFTY_FOUR.getSlots().forEach(slot -> inventory.setItem(slot, border));

        // actual item
        inventory.setItem(13, shopItem.getMaterial());

        //increment items
        inventory.setItem(20, ShopAPI.get().createPurchaseInventoryItem("increment", (int) SettingsManager.get(Settings.INCREMENT_FIRST),0,0));
        inventory.setItem(29, ShopAPI.get().createPurchaseInventoryItem("increment", (int) SettingsManager.get(Settings.INCREMENT_SECOND),0,0));

        //decrement items
        inventory.setItem(24, ShopAPI.get().createPurchaseInventoryItem("decrement", (int) SettingsManager.get(Settings.DECREMENT_FIRST),0,0));
        inventory.setItem(33, ShopAPI.get().createPurchaseInventoryItem("decrement", (int) SettingsManager.get(Settings.DECREMENT_SECOND),0,0));

        //sell
        inventory.setItem(30, ShopAPI.get().createPurchaseInventoryItem("sellall", 0,0,0));
        inventory.setItem(31, ShopAPI.get().createPurchaseInventoryItem("iteminfo", this.total,shopItem.getSellPrice() * this.total,shopItem.getBuyPrice() * this.total));
        inventory.setItem(32, ShopAPI.get().createPurchaseInventoryItem("selltotal", this.total,0,0));

        // close item
        inventory.setItem(49, ShopAPI.get().createPurchaseInventoryItem("close-item", 0 ,0,0));
        return inventory;
    }
}
