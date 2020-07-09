package com.kiranhart.shops.inventories.edit;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.inventories.HartInventory;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.util.helpers.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/8/2020
 * Time Created: 9:25 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class DiscountInventory extends HartInventory {

    private Shop shop;

    public DiscountInventory(Shop shop) {
        this.shop = shop;
        setTitle(Core.getInstance().getConfig().getString("guis.discount-settings.title").replace("%shopname%", shop.getTitle()));
        setPage(1);
        setRows(3);
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        double oldValue = ShopAPI.get().getDiscount(shop.getName());

        if (slot == 12) {
            double newVal = oldValue += (e.getClick() == ClickType.SHIFT_LEFT) ? 5 : (e.getClick() == ClickType.RIGHT) ? 0.5 : 1;
            if (newVal > 100) newVal = 100;
            ShopAPI.get().setDiscountAmount(shop.getName(), newVal);
            p.openInventory(new DiscountInventory(this.shop).getInventory());
        }

        if (slot == 14) {
            double newVal = oldValue -= (e.getClick() == ClickType.SHIFT_LEFT) ? 5 : (e.getClick() == ClickType.RIGHT) ? 0.5 : 1;
            if (newVal <= 0) newVal = 1;
            ShopAPI.get().setDiscountAmount(shop.getName(), newVal);
            p.openInventory(new DiscountInventory(this.shop).getInventory());
        }

        if (slot == 16) {
            ShopAPI.get().toggleShopDiscount(shop.getName());
            p.openInventory(new DiscountInventory(this.shop).getInventory());
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        fillRows(inventory, XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.discount-settings.border-item")).get().parseItem(), 1, 2, 3);

        // 12 13 14 16
        inventory.setItem(12, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.discount-settings.add"));
        inventory.setItem(13, ShopAPI.get().createDiscountTotalItem(ShopAPI.get().getDiscount(shop.getName())));
        inventory.setItem(14, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.discount-settings.remove"));
        inventory.setItem(16, (ShopAPI.get().hasDiscount(shop.getName())) ? ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.discount-settings.discounton") : ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.discount-settings.discountoff"));
        return inventory;
    }
}
