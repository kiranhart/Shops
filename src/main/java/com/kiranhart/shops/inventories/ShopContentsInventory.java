package com.kiranhart.shops.inventories;

import com.google.common.collect.Lists;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.HartInventory;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.ShopItem;
import com.kiranhart.shops.util.SettingsManager;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/22/2020
 * Time Created: 10:34 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopContentsInventory extends HartInventory {

    private Shop shop;
    private List<List<ShopItem>> chunks;

    public ShopContentsInventory(Shop shop) {
        this.shop = shop;
        this.page = 1;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.shopcontent.title").replace("%shop_title%", this.shop.getTitle()));
        this.chunks = Lists.partition(shop.getShopItems(), 45);

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
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (slot == 49) p.closeInventory();

        // pagination
        if (this.page >= 1 && slot == 48) p.openInventory(this.setPage(this.page - 1).getInventory());
        if (this.page >= 1 && slot == 50) p.openInventory(this.setPage(this.page + 1).getInventory());

        if (NBTEditor.contains(e.getCurrentItem(), "ShopItemID")) {

            String id = NBTEditor.getString(e.getCurrentItem(), "ShopItemID");

            // left
            if (e.getClick() == ClickType.LEFT) {
                p.openInventory(new ShopEditPriceInventory(this.shop, id).getInventory());
            }

            // middle
            if (e.getClick() == ClickType.MIDDLE) {
                Core.getInstance().getShopsFile().getConfig().set("shops." + this.shop.getName().toLowerCase() + ".items." + id, null);
                Core.getInstance().getShopsFile().saveConfig();
                Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shop.getName())).findFirst().get().updateItems();
                p.openInventory(new EditInventory(this.shop.getName().toLowerCase()).getInventory());
                Core.getInstance().getLocale().getMessage(ShopLang.SHOP_ITEM_REMOVED).sendPrefixedMessage(p);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);
        chunks.get(this.page - 1).forEach(item -> inventory.setItem(inventory.firstEmpty(), item.getItem(Core.getInstance().getConfig().getString("guis.shopcontent.removemsg"), Core.getInstance().getConfig().getString("guis.shopcontent.editmsg"))));

        if (this.defaultSize == 54) {
            inventory.setItem(48, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shopcontent.previous-page"));
            inventory.setItem(49, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shopcontent.close-item"));
            inventory.setItem(50, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.shopcontent.next-page"));
        }

        return inventory;
    }
}
