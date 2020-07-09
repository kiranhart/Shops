package com.kiranhart.shops.inventories.edit;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.BorderNumbers;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.inventories.HartInventory;
import com.kiranhart.shops.inventories.shop.ShopContentsInventory;
import com.kiranhart.shops.util.helpers.XMaterial;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/12/2020
 * Time Created: 1:09 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class EditInventory extends HartInventory {

    private String shopName;

    public EditInventory(String shopName) {
        this.shopName = shopName;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.edit-shop.title").replace("%shopname%", this.shopName));
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (slot == 49) p.closeInventory();

        if (slot == 33) {
            Core.getInstance().getShopsFile().getConfig().set("shops." + this.shopName.toLowerCase() + ".public", !ShopAPI.get().isShopPublic(this.shopName));
            Core.getInstance().getShopsFile().saveConfig();
            Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shopName)).findFirst().get().update();
            p.openInventory(new EditInventory(this.shopName).getInventory());
        }

        if (slot == 20) {
            Core.getInstance().getShopsFile().getConfig().set("shops." + this.shopName.toLowerCase() + ".sellonly", !ShopAPI.get().isSellOnly(this.shopName));
            Core.getInstance().getShopsFile().saveConfig();
            p.openInventory(new EditInventory(this.shopName).getInventory());
        }

        if (slot == 22) {
            Core.getInstance().getShopsFile().getConfig().set("shops." + this.shopName.toLowerCase() + ".buyonly", !ShopAPI.get().isBuyOnly(this.shopName));
            Core.getInstance().getShopsFile().saveConfig();
            p.openInventory(new EditInventory(this.shopName).getInventory());
        }

        if (slot == 24) {
            p.openInventory(new DiscountInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shopName)).findFirst().get()).getInventory());
        }

        if (slot == 31) {
            ShopAPI.get().removeShop(this.shopName);
            p.closeInventory();
            Core.getInstance().getLocale().getMessage(ShopLang.SHOP_REMOVED).processPlaceholder("shopname", this.shopName).sendPrefixedMessage(p);
        }

        if (slot == 29) {
            p.openInventory(new ShopContentsInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shopName)).findFirst().get()).getInventory());
        }

        if (slot == 13) {
            // open anvil thing
            new AnvilGUI.Builder().onComplete((player, text) -> {
                Core.getInstance().getShopsFile().getConfig().set("shops." + this.shopName.toLowerCase() + ".title", text);
                Core.getInstance().getShopsFile().saveConfig();
                player.openInventory(this.getInventory());
                Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shopName)).findFirst().get().update();
                return AnvilGUI.Response.close();
            }).text(ChatColor.translateAlternateColorCodes('&', "&bEnter new item name")).title(ChatColor.translateAlternateColorCodes('&', "&bEnter new Item Name")).plugin(Core.getInstance()).open(p);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        // setup borders
        ItemStack border = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.edit-shop.border-item")).get().parseItem();
        BorderNumbers.FIFTY_FOUR.getSlots().forEach(slot -> inventory.setItem(slot, border));

        // set feature icons
        inventory.setItem(13, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.setname", ShopAPI.get().getShopTitleByName(this.shopName)));
        inventory.setItem(29, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.edititems"));
        inventory.setItem(31, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.deleteshop"));



        if (ShopAPI.get().isBuyOnly(this.shopName)) {
            inventory.setItem(22, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.buyonlyon", ShopAPI.get().getShopTitleByName(this.shopName)));
        } else {
            inventory.setItem(22, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.buyonlyoff", ShopAPI.get().getShopTitleByName(this.shopName)));
        }

        if (ShopAPI.get().isSellOnly(this.shopName)) {
            inventory.setItem(20, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.sellonlyon", ShopAPI.get().getShopTitleByName(this.shopName)));
        } else {
            inventory.setItem(20, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.sellonlyoff", ShopAPI.get().getShopTitleByName(this.shopName)));
        }

        inventory.setItem(24, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.discount"));

        if (ShopAPI.get().isShopPublic(this.shopName)) {
            inventory.setItem(33, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.shop-enabled", ShopAPI.get().getShopTitleByName(this.shopName)));
        } else {
            inventory.setItem(33, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.shop-disable", ShopAPI.get().getShopTitleByName(this.shopName)));
        }

        inventory.setItem(49, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-shop.close-item"));
        return inventory;
    }

}
