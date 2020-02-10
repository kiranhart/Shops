package com.kiranhart.shops.api;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.enums.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

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
     * @param player is the command sender
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
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".public",  false);
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".items.1.material", "CAKE");
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".items.1.buy-price", 20D);
        Core.getInstance().getShopsFile().getConfig().set("shops." + name.toLowerCase() + ".items.1.sell-price", 10D);
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
     * @param path is the path to the item.
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

    public ItemStack loadShopNameItemFromConfig(String shopname) {
        ItemStack stack = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.edit-list.shop-item.item")).get().parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.edit-list.shop-item.name").replace("%shopname%", shopname)));
        ArrayList<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("guis.edit-list.shop-item.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
