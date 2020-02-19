package com.kiranhart.shops.api;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.enums.DefaultFontInfo;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.ShopItem;
import com.kiranhart.shops.shop.Transaction;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:00 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
@SuppressWarnings("all")
public class ShopAPI {

    private static ShopAPI instance;

    // set constructor to private
    private ShopAPI() {
    }

    // constants
    private final static int CENTER_PX = 154;

    /**
     * Get an instance of the shop API
     *
     * @return the Shop API instance
     */
    public static ShopAPI get() {
        if (instance == null) {
            instance = new ShopAPI();
        }
        return instance;
    }

    /**
     * Used to determine of a player has a set of permissions
     *
     * @param sender is the command sender
     * @param perms  is the array of permissions you want to check
     * @return whether or not they have permissions to any of the param perms
     */
    public boolean hasPerm(CommandSender sender, String... perms) {

        if (sender.isOp()) return true;

        for (String s : perms) {
            if (sender.hasPermission(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Used to send a centered message
     *
     * @param player  is the command sender
     * @param message is what you want to send
     */
    public void centerMsg(CommandSender player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

    /**
     * Check if the shop exists by node on file
     *
     * @param shopName is the shop name/category you're checking
     * @return whether or not that shop exists on file
     */
    public boolean doesShopExistsOnFile(String shopName) {
        return Core.getInstance().getShopsFile().getConfig().contains("shops." + shopName.toLowerCase());
    }

    /**
     * Get all of the shops by name
     *
     * @return an array list of all of the shop names under `shops` in the shops.yml
     */
    public ArrayList<String> getAllShopNames() {
        ArrayList<String> shops = new ArrayList<>();
        Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops").getKeys(false).forEach(shop -> shops.add(shop));
        return shops;
    }

    /**
     * Check whether or not there are any shops created
     *
     * @return true/false depending if there are shops on file
     */
    public boolean anyShopsExists() {
        ConfigurationSection section = Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops");
        if (section == null || section.getKeys(false).size() == 0) return false;
        return true;
    }

    /**
     * Create a new shop and store onto the file
     *
     * @param name is the name of the shop you want to create
     */
    public void createNewShop(String name) {
        // check if the shop exists first
        if (doesShopExistsOnFile(name)) {
            return;
        }

        // create the with the default settings
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".title", name);
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".id", UUID.randomUUID().toString());
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".icon", XMaterial.NETHER_STAR.parseMaterial().name());
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".public", false);
        Core.getInstance().getShopsFile().saveConfig();
    }

    /**
     * Get shop title by name
     *
     * @param name is the shop name
     * @return shop title
     */
    public String getShopTitleByName(String name) {
        return ChatColor.translateAlternateColorCodes('&', Core.getInstance().getShopsFile().getConfig().getString("shops." + name.toLowerCase() + ".title"));
    }

    /**
     * Used to remove a shop from the flat file
     *
     * @param name is the name of the shop you're removing
     */
    public void removeShop(String name) {
        // check if the shop exists first
        if (!doesShopExistsOnFile(name)) {
            return;
        }

        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase(), null);
        Core.getInstance().getShopsFile().saveConfig();
    }

    /**
     * Check whether or not the shop has items to sell
     *
     * @param shopName is the shop name your checking
     * @return whether or not the shop has items
     */
    public boolean doesShopHaveItems(String shopName) {
        ConfigurationSection section = Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops." + shopName.toLowerCase() + ".items");
        if (section == null || section.getKeys(false).size() == 0) return false;
        return true;
    }

    /**
     * Used to create an itemstack straight from a configuration file
     *
     * @param config is the config want to get the stack from
     * @param path   is the path to the item.
     * @param name   is the item name replacement
     * @return an ItemStack
     */
    public ItemStack loadFullItemFromConfig(Configuration config, String path, String name) {
        ItemStack stack = XMaterial.matchXMaterial(config.getString(path + ".item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".name").replace("%shopname%", name)));
        ArrayList<String> lore = new ArrayList<>();
        config.getStringList(path + ".lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Used to create an itemstack straight from a configuration file
     *
     * @param config is the config want to get the stack from
     * @param path   is the path to the item.
     * @return an ItemStack
     */
    public ItemStack loadFullItemFromConfig(Configuration config, String path) {
        ItemStack stack = XMaterial.matchXMaterial(config.getString(path + ".item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".name")));
        ArrayList<String> lore = new ArrayList<>();
        config.getStringList(path + ".lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Generate an itemstack for shop icon
     *
     * @param shopname is the shop you want to get the icon of
     * @return an itemstack of the shop icon
     */
    public ItemStack loadShopNameItemFromConfig(String shopname) {
        ItemStack stack = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.edit-list.shop-item.item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.edit-list.shop-item.name").replace("%shopname%", getShopTitleByName(shopname))));
        ArrayList<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.edit-list.shop-item.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.set(stack, shopname, "ShopNameByIcon");
        return stack;
    }

    /**
     * Get a list of all the shop icons
     *
     * @return a list of the shop icon item stacks
     */
    public ArrayList<ItemStack> getListOfShopNameItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        this.getAllShopNames().forEach(name -> items.add(this.loadShopNameItemFromConfig(name)));
        return items;
    }

    /**
     * Load all of the shops regardless if they're configured
     */
    public void loadAllShops() {
        if (this.anyShopsExists()) {
            this.getAllShopNames().forEach(shop -> Core.getInstance().getShops().add(new Shop(shop)));
        }
    }

    /**
     * Check whether or not the shop is public
     *
     * @param shopName is the shop name your checking
     * @return whether or not the shop is public
     */
    public boolean isShopPublic(String shopName) {
        return Core.getInstance().getShopsFile().getConfig().getBoolean("shops." + shopName.toLowerCase() + ".public");
    }

    /**
     * Save a new item to the shop material list
     *
     * @param shopName is the shop you're gonna set it to
     * @param material is the raw material
     * @param sell     is the price the item sells for
     * @param buy      is the price for x1 of the item
     */
    public void addShopItem(String shopName, ItemStack material, double sell, double buy) {
        if (doesShopExistsOnFile(shopName)) {
            String unique = UUID.randomUUID().toString() + System.currentTimeMillis();
            Core.getInstance().getShopsFile().getConfig().set("shops." + shopName.toLowerCase() + ".items." + unique + ".material", material);
            Core.getInstance().getShopsFile().getConfig().set("shops." + shopName.toLowerCase() + ".items." + unique + ".sell-price", sell);
            Core.getInstance().getShopsFile().getConfig().set("shops." + shopName.toLowerCase() + ".items." + unique + ".buy-price", buy);
            Core.getInstance().getShopsFile().saveConfig();
        }
    }

    /**
     * check if given string is a double
     *
     * @param s is the string to check
     * @return if double
     */
    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * check if item stack is air
     *
     * @param stack is the stack being checked
     * @return if the item stack is air
     */
    public boolean isAir(ItemStack stack) {
        if (stack.getType() == Material.AIR || stack == null) {
            return true;
        }
        return false;
    }

    /**
     * get the item in the player hand based on server version
     *
     * @param p is the player
     * @return the itemstack in their hand
     */
    public ItemStack getItemInHand(Player p) {
        if (Version.getCurrentVersion().isNewer(Version.v1_8_R3)) {
            return p.getInventory().getItemInMainHand();
        } else {
            return p.getItemInHand();
        }
    }

    /**
     * set the item in the player hand depending on server version
     *
     * @param p    is the player
     * @param item is the itemstack you're setting the hand to
     */
    public void setItemInHand(Player p, ItemStack item) {
        if (Version.getCurrentVersion().isNewer(Version.v1_8_R3)) {
            p.getInventory().setItemInMainHand(item);
        } else {
            p.getInventory().setItemInHand(item);
        }
    }

    /**
     * Get all shop items by shop name
     *
     * @param shopName the shop name you want items from
     * @return all the shop items if any from shop.
     */
    public LinkedList<ShopItem> getShopItemsFromName(String shopName) {
        LinkedList<ShopItem> items = new LinkedList<>();
        Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops." + shopName.toLowerCase() + ".items").getKeys(false).forEach(itemNode -> {
            double buy = Core.getInstance().getShopsFile().getConfig().getDouble("shops." + shopName.toLowerCase() + ".items." + itemNode + ".buy-price");
            double sell = Core.getInstance().getShopsFile().getConfig().getDouble("shops." + shopName.toLowerCase() + ".items." + itemNode + ".sell-price");
            items.add(new ShopItem(shopName, itemNode, Core.getInstance().getShopsFile().getConfig().getItemStack("shops." + shopName.toLowerCase() + ".items." + itemNode + ".material"), sell, buy));
        });
        return items;
    }

    /**
     * create the purchase inventory items
     *
     * @param item   is the item from the gui you want to create
     * @param amount is the amount total
     * @param sell   is the sell price
     * @param buy    is the buy price
     * @return
     */
    public ItemStack createPurchaseInventoryItem(String item, int amount, double sell, double buy) {
        ItemStack stack = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.purchase-inventory." + item + ".item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.purchase-inventory." + item + ".name").replace("%amount%", String.valueOf(amount))));
        List<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.purchase-inventory." + item + ".lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line.replace("%sell_total%", String.valueOf(sell)).replace("%buy_total%", String.valueOf(buy)))));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Used to perform the purchase for a player
     *
     * @param p is the player you want to add the item to
     * @param shopItem is the shop item
     * @param total is the total amount of items
     */
    public void performPurchase(Player p, ShopItem shopItem, int total) {
        for (int i = 0; i < total; i++) {
            p.getInventory().addItem(shopItem.getMaterial());
        }
    }

    /**
     * Used to save a transaction to the transaction.yml file
     *
     * @param transactions is an array of transactions you want to save
     */
    public void saveTransactions(Transaction... transactions) {
        for (Transaction transaction : transactions) {
            String node = "transactions." + transaction.getId().toString() + transaction.getCurrentMillis() + ".";
            Core.getInstance().getTransactionFile().getConfig().set(node + "id", transaction.getId().toString());
            Core.getInstance().getTransactionFile().getConfig().set(node + "time", transaction.getCurrentMillis());
            Core.getInstance().getTransactionFile().getConfig().set(node + "buyer", transaction.getBuyer().toString());
            Core.getInstance().getTransactionFile().getConfig().set(node + "type", transaction.getTransactionType().getType());
            Core.getInstance().getTransactionFile().getConfig().set(node + "shop-name", transaction.getShop().getName());
            Core.getInstance().getTransactionFile().getConfig().set(node + "shop-id", transaction.getShop().getId().toString());
            Core.getInstance().getTransactionFile().getConfig().set(node + "item", transaction.getShopItem().getMaterial());
            Core.getInstance().getTransactionFile().getConfig().set(node + "buy-price", transaction.getShopItem().getBuyPrice());
            Core.getInstance().getTransactionFile().getConfig().set(node + "sell-price", transaction.getShopItem().getSellPrice());
            Core.getInstance().getTransactionFile().getConfig().set(node + "quantity", transaction.getQuantity());
        }
        Core.getInstance().getTransactionFile().saveConfig();
    }

    /**
     * Get the total amount of items in a player inventory
     *
     * @param player the player you want to check
     * @param stack the stack you're searching for
     * @return the total amount of items found
     */
    public int getAmountOfItems(Player player, ItemStack stack) {
        if (stack == null) return 0;
        int amount = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(stack)) continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    /**
     * Remove a specific amount of items from an inventory
     *
     * @param inventory is the inventory you're removing from
     * @param stack is the stack you're targeting
     * @param amount is the amount you wish to remove
     */
    public void removeItemsFromPlayer(Inventory inventory, ItemStack stack, int amount) {
        if (amount <= 0) return;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack slot = inventory.getItem(i);
            if (slot == null) continue;
            if (stack.isSimilar(slot)) {
                int newAmount = slot.getAmount() - amount;
                if (newAmount > 0) {
                    slot.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(i);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
}
