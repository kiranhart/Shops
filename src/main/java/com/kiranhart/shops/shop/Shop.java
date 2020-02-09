package com.kiranhart.shops.shop;

import java.util.LinkedList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 11:38 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public abstract class Shop {

    /**
     * get the title of the inventory window
     */
    protected String title = "Default Title";

    /**
     * get the category of the shop, used to store in config as well
     */
    protected String category = "Food";

    /**
     * a linked list to store shop items as it's better for frequent add/subtraction of elements
     */
    protected LinkedList<ShopItem> shopItems = new LinkedList<>();

    /**
     * load all of the shop items from the configuration file
     */
    abstract void loadShopItems();

    /**
     * get the shops items
     *
     * @return the loaded shop items
     */
    abstract LinkedList<ShopItem> getShopItems();


    /**
     * used to save a shop
     */
    abstract void save();
}
