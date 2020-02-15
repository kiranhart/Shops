package com.kiranhart.shops.shop;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 11:40 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopItem {

    /**
     * used to store the shop category
     */
    private String category;

    private ItemStack item;
    private double sellPrice;
    private double buyPrice;

    private String id;

    /**
     * default constructor, set values to basic
     */
    public ShopItem() {
        this.category = "Default";
        this.item = XMaterial.PAPER.parseItem();
        this.sellPrice = 0D;
        this.buyPrice = 0D;
    }

    public ShopItem(String shopName, String id, ItemStack material, double sellPrice, double buyPrice) {
        this.category = shopName;
        this.id = id;
        this.item = material;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    /**
     * Get the item category
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get the literal item stack
     *
     * @return the item stack being used (type)
     */
    public ItemStack getMaterial() {
        return item;
    }

    public ItemStack getItem() {
        ItemStack stack = item.clone();
        ItemMeta meta = stack.getItemMeta();
        if (!meta.hasDisplayName()) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.shop.purchase-item.name").replace("%material_name%", StringUtils.capitalize(item.getType().name().toLowerCase().replace("_", " ")))));
        }
        ArrayList<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.shop.purchase-item.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line.replace("%buy_price%", String.valueOf(buyPrice)).replace("%sell_price%", String.valueOf(sellPrice)))));
        meta.setLore(lore);
        stack.setItemMeta(meta);

        stack = NBTEditor.set(stack, sellPrice, "ItemSellPrice");
        stack = NBTEditor.set(stack, buyPrice, "ItemBuyPrice");
        stack = NBTEditor.set(stack, id, "ShopItemID");
        return stack;
    }

    /**
     * Get the sell price
     *
     * @return the selling price of the item
     */
    public double getSellPrice() {
        return sellPrice;
    }

    /**
     * Get the buy price
     *
     * @return the buy price of the item
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Used to set the item category
     *
     * @param category is the category you want to set it to
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Used to set the item stack
     *
     * @param item is the item stack
     */
    public void setMaterial(ItemStack item) {
        this.item = item;
    }

    /**
     * Used to set the sell price of the item
     *
     * @param sellPrice is the sell price
     */
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Used to set the buy price of the item
     *
     * @param buyPrice is the buy price
     */
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getId() {
        return id;
    }
}
