package com.kiranhart.shops.inventories.edit;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.inventories.HartInventory;
import com.kiranhart.shops.inventories.shop.ShopContentsInventory;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.util.helpers.XMaterial;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/23/2020
 * Time Created: 1:00 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopEditPriceInventory extends HartInventory {

    private Shop shop;
    private String itemID;

    public ShopEditPriceInventory(Shop shop, String itemID) {
        this.shop = shop;
        this.itemID = itemID;

        this.defaultSize = 27;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.editprice.title"));
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (slot == 11) {
            new AnvilGUI.Builder().onComplete((player, text) -> {
                if (ShopAPI.get().isDouble(text)) {
                    Core.getInstance().getShopsFile().getConfig().set("shops." + shop.getName().toLowerCase() + ".items." + itemID + ".sell-price", Double.parseDouble(text));
                    Core.getInstance().getShopsFile().saveConfig();
                    Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shop.getName())).findFirst().get().updateItems();
                    player.openInventory(new ShopContentsInventory(this.shop).getInventory());
                } else {
                    player.closeInventory();
                    Core.getInstance().getLocale().getMessage(ShopLang.NOT_A_NUMBER).sendPrefixedMessage(p);
                }
                return AnvilGUI.Response.close();
            }).title(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("anvils.setsellprice"))).plugin(Core.getInstance()).item(XMaterial.PAPER.parseItem()).open(p);
        }

        if (slot == 15) {
            new AnvilGUI.Builder().onComplete((player, text) -> {
                if (ShopAPI.get().isDouble(text)) {
                    Core.getInstance().getShopsFile().getConfig().set("shops." + shop.getName().toLowerCase() + ".items." + itemID + ".buy-price", Double.parseDouble(text));
                    Core.getInstance().getShopsFile().saveConfig();
                    Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shop.getName())).findFirst().get().updateItems();
                    player.openInventory(new ShopContentsInventory(this.shop).getInventory());
                } else {
                    player.closeInventory();
                    Core.getInstance().getLocale().getMessage(ShopLang.NOT_A_NUMBER).sendPrefixedMessage(p);
                }
                return AnvilGUI.Response.close();
            }).title(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("anvils.setbuyprice"))).plugin(Core.getInstance()).item(XMaterial.PAPER.parseItem()).open(p);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        inventory.setItem(11, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.editprice.setsellprice"));
        inventory.setItem(13, shop.getShopItems().stream().filter(item -> item.getId().equalsIgnoreCase(this.itemID)).findFirst().get().getMaterial());
        inventory.setItem(15, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.editprice.setbuyprice"));
        return inventory;
    }
}
