package com.kiranhart.shops.inventories.shop;

import com.google.common.collect.Lists;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.inventories.HartInventory;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.ShopItem;
import com.kiranhart.shops.util.SettingsManager;
import com.kiranhart.shops.util.helpers.NBTEditor;
import com.kiranhart.shops.util.helpers.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/13/2020
 * Time Created: 6:35 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopInventory extends HartInventory {

    private Shop shop;
    private List<List<ShopItem>> chunks;

    public ShopInventory(Shop shop) {
        this.page = 1;
        this.shop = shop;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', ShopAPI.get().getShopTitleByName(shop.getName()) + " &eShop");
        chunks = Lists.partition(shop.getShopItems(), 45);

        if ((boolean) SettingsManager.get(Settings.DYNAMIC_SHOP_SIZE)) {
            if (shop.getShopItems().size() <= 9) this.defaultSize = 9;
            if (shop.getShopItems().size() >= 10 && shop.getShopItems().size() <= 18) this.defaultSize = 18;
            if (shop.getShopItems().size() >= 19 && shop.getShopItems().size() <= 27) this.defaultSize = 27;
            if (shop.getShopItems().size() >= 28 && shop.getShopItems().size() <= 36) this.defaultSize = 36;
            if (shop.getShopItems().size() >= 37 && shop.getShopItems().size() <= 45) this.defaultSize = 45;
        }
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        // pagination
        if (this.page >= 1 && slot == 48) p.openInventory(this.setPage(this.page - 1).getInventory());
        if (this.page >= 1 && slot == 50) p.openInventory(this.setPage(this.page + 1).getInventory());

        if (slot == 49) {
            p.closeInventory();
        }

        if (e.getCurrentItem().isSimilar(ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"))) {
            p.openInventory(new SelectShopInventory().getInventory());
        }

        if (this.shop.isPublic()) {
            ShopItem clickedItem = this.extractShopItem(e.getCurrentItem());
            if (clickedItem != null) {
                p.openInventory(new PurchaseInventory(this.shop, clickedItem, 1).getInventory());
            }
        } else {
            if ((boolean) SettingsManager.get(Settings.ALLOW_PURCHASE_FROM_PRIVATE_SHOP)) {
                ShopItem clickedItem = this.extractShopItem(e.getCurrentItem());
                if (clickedItem != null) {
                    p.openInventory(new PurchaseInventory(this.shop, clickedItem, 1).getInventory());
                }
            } else {
                if ((boolean) SettingsManager.get(Settings.ALLOW_ADMIN_PURCHASE_OVERRIDE) && ShopAPI.get().hasPerm(p, ShopPerm.ADMIN)) {
                    ShopItem clickedItem = this.extractShopItem(e.getCurrentItem());
                    if (clickedItem != null) {
                        p.openInventory(new PurchaseInventory(this.shop, clickedItem, 1).getInventory());
                    }
                } else {
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_PRIVATE).sendPrefixedMessage(p);
                }
            }
        }
    }

    @Override
    public Inventory getInventory() {

        int oldSize = this.defaultSize;

        if ((boolean) SettingsManager.get(Settings.USE_AUTO_BACK_BUTTONS_ON_SHOP)) {
            if (this.defaultSize == 9) {
                this.defaultSize = 18;
            } else if (this.defaultSize == 18) {
                this.defaultSize = 27;
            } else if (this.defaultSize == 27) {
                this.defaultSize = 36;
            } else if (this.defaultSize == 36) {
                this.defaultSize = 45;
            } else if (this.defaultSize == 45) {
                this.defaultSize = 54;
            }
        }

        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        chunks.get(this.page - 1).forEach(shopItem -> inventory.setItem(inventory.firstEmpty(), shopItem.getItem()));

        // 9 18 27 36 45 54
        if ((boolean) SettingsManager.get(Settings.USE_AUTO_BACK_BUTTONS_ON_SHOP)) {
            if (oldSize == 9) {
                fillRow(inventory, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), 2);
                inventory.setItem(13, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"));
            } else if (oldSize == 18) {
                fillRow(inventory, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), 3);
                inventory.setItem(22, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"));
            } else if (oldSize == 27) {
                fillRow(inventory, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), 4);
                inventory.setItem(31, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"));
            } else if (oldSize == 36) {
                fillRow(inventory, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), 5);
                inventory.setItem(41, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"));
            }
        }


        if (this.defaultSize == 54) {
            fillRow(inventory, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), 6);
            inventory.setItem(48, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.previous-page"));
            inventory.setItem(49, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.close-item"));
            inventory.setItem(50, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.next-page"));
            // back item
            if ((boolean) SettingsManager.get(Settings.USE_AUTO_BACK_BUTTONS_ON_SHOP)) {
                inventory.setItem(45, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shop.back-item"));
            }
        }

        return inventory;
    }

    private ShopItem extractShopItem(ItemStack stack) {
        if (!ShopAPI.get().isAir(stack)) {
            if (NBTEditor.contains(stack, "ShopItemID")) {
                String id = NBTEditor.getString(stack, "ShopItemID");
                return shop.getShopItems().stream().filter(shopItem -> shopItem.getId().equalsIgnoreCase(id)).findFirst().get();
            }
        }
        return null;
    }
}
