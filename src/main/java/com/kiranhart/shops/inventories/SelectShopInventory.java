package com.kiranhart.shops.inventories;

import com.google.common.collect.Lists;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.HartInventory;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/15/2020
 * Time Created: 8:54 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class SelectShopInventory extends HartInventory {

    private List<List<Shop>> chunks;

    public SelectShopInventory() {
        this.page = 1;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.allshops.title"));
        this.chunks = Lists.partition(Core.getInstance().getShops(), 45);

        if ((boolean) SettingsManager.get(Settings.DYNAMIC_SHOP_SIZE)) {
            if (Core.getInstance().getShops().size() <= 9) this.defaultSize = 9;
            if (Core.getInstance().getShops().size() >= 10 && Core.getInstance().getShops().size() <= 18)
                this.defaultSize = 18;
            if (Core.getInstance().getShops().size() >= 19 && Core.getInstance().getShops().size() <= 27)
                this.defaultSize = 27;
            if (Core.getInstance().getShops().size() >= 28 && Core.getInstance().getShops().size() <= 36)
                this.defaultSize = 36;
            if (Core.getInstance().getShops().size() >= 37 && Core.getInstance().getShops().size() <= 45)
                this.defaultSize = 45;
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        chunks.get(this.page - 1).forEach(shop -> {
            if (!shop.isPublic()) {
                if ((boolean) SettingsManager.get(Settings.SHOW_PRIVATE_SHOP_IN_SELECT)) {
                    inventory.setItem(inventory.firstEmpty(), shop.getIcon());
                }
            } else {
                inventory.setItem(inventory.firstEmpty(), shop.getIcon());
            }
        });
        return inventory;
    }
}
