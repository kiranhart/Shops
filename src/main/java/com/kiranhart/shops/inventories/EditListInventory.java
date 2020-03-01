package com.kiranhart.shops.inventories;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:31 a.m.
*/

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.BorderNumbers;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EditListInventory extends HartInventory {

    private List<List<ItemStack>> chunks;

    public EditListInventory() {
        this.page = 1;
        this.defaultSize =  54;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.edit-list.title"));
        if (ShopAPI.get().anyShopsExists()) {
            this.chunks = Lists.partition(ShopAPI.get().getListOfShopNameItems(), 28);
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

        // check if they clicked a shop icon
        if (NBTEditor.contains(e.getCurrentItem(), "ShopNameByIcon")) {
            String shopName = NBTEditor.getString(e.getCurrentItem(), "ShopNameByIcon");

            if (ShopAPI.get().doesShopExistsOnFile(shopName)) {
                p.openInventory(new EditInventory(shopName).getInventory());
            }
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        // setup the border
        ItemStack border = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.edit-list.border-item")).get().parseItem();
        BorderNumbers.FIFTY_FOUR.getSlots().forEach(slot -> inventory.setItem(slot, border));

        // setup the pagination stuff
        inventory.setItem(48, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.previous-page"));
        inventory.setItem(49, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.close-item"));
        inventory.setItem(50, ShopAPI.get().loadFullItemFromConfig(Core.getInstance().getConfig(), "guis.edit-list.next-page"));

        if (ShopAPI.get().anyShopsExists()) {
            chunks.get(this.page - 1).forEach(stack -> inventory.setItem(inventory.firstEmpty(), stack));
        }

        return inventory;
    }
}
