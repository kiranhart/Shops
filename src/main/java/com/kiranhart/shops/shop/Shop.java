package com.kiranhart.shops.shop;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 11:38 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Shop {

    private String name;
    private String title;
    private UUID id;
    private boolean isPublic;
    private LinkedList<ShopItem> shopItems;

    public Shop(String name) {
        this.name = name;

        if (ShopAPI.get().doesShopExistsOnFile(this.name)) {
            this.title = Core.getInstance().getShopsFile().getConfig().getString("shops." + this.name.toLowerCase() + ".title");
            this.id = UUID.fromString(Objects.requireNonNull(Core.getInstance().getShopsFile().getConfig().getString("shops." + this.name.toLowerCase() + ".id")));
            this.isPublic = Core.getInstance().getShopsFile().getConfig().getBoolean("shops." + this.name.toLowerCase() + ".public");
            this.shopItems = (hasItems()) ? loadShopItems() : new LinkedList<>();
        } else {
            this.title = "Default Title";
            this.id = UUID.randomUUID();
            this.isPublic = false;
            this.shopItems = new LinkedList<>();
        }
    }

    public void updateItems() {
        this.shopItems = loadShopItems();
    }

    public void update() {
            this.title = Core.getInstance().getShopsFile().getConfig().getString("shops." + this.name.toLowerCase() + ".title");
            this.id = UUID.fromString(Objects.requireNonNull(Core.getInstance().getShopsFile().getConfig().getString("shops." + this.name.toLowerCase() + ".id")));
            this.isPublic = Core.getInstance().getShopsFile().getConfig().getBoolean("shops." + this.name.toLowerCase() + ".public");
    }

    public boolean hasItems() {
        ConfigurationSection sec = Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops." + this.name.toLowerCase() + ".items");
        if (sec == null || sec.getKeys(false).size() == 0) return false;
        return true;
    }

    public LinkedList<ShopItem> loadShopItems() {
        return ShopAPI.get().getShopItemsFromName(this.name.toLowerCase());
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public LinkedList<ShopItem> getShopItems() {
        return this.shopItems;
    }

    public ItemStack getIcon() {
        ItemStack stack = Core.getInstance().getShopsFile().getConfig().getItemStack("shops." + this.name.toLowerCase() + ".icon");
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.allshops.shop-icon.name").replace("%shop_name%", this.name).replace("%shop_title%", ShopAPI.get().getShopTitleByName(this.name))));
        ArrayList<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.allshops.shop-icon.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.set(stack, this.name, "ShopNameByIcon");
        return stack;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setShopItems(LinkedList<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }
}
