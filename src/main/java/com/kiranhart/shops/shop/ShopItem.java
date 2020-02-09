package com.kiranhart.shops.shop;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

    /**
     * default constructor, set values to basic
     */
    public ShopItem() {
        this.category = "Default";
        this.item = XMaterial.PAPER.parseItem();
        this.sellPrice = 0D;
        this.buyPrice = 0D;
    }

    /**
     * used to get a shop item from a category
     *
     * @param category is the the category of the item
     */
    public ShopItem(String category) {
        this.category = category;
    }

    /**
     * used to get a shop item from item and price
     *
     * @param item      is the literal item stack
     * @param sellPrice is the price the item sells for
     * @param buyPrice  is the price the item buys for
     */
    public ShopItem(ItemStack item, double sellPrice, double buyPrice) {
        this.category = "Default";
        this.item = item;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    /**
     * used to get a shop item from item and price and category
     *
     * @param category  is the the category of the item
     * @param item      is the literal item stack
     * @param sellPrice is the price the item sells for
     * @param buyPrice  is the price the item buys for
     */
    public ShopItem(String category, ItemStack item, double sellPrice, double buyPrice) {
        this.category = category;
        this.item = item;
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
     * @return the item stack being used
     */
    public ItemStack getItem() {
        return item;
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
    public void setItem(ItemStack item) {
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
}
