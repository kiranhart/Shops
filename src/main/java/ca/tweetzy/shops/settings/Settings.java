package ca.tweetzy.shops.settings;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.shops.Shops;

import java.util.Arrays;
import java.util.Collections;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Settings {

    static final Config config = Shops.getInstance().getCoreConfig();

    public static final ConfigSetting LANG = new ConfigSetting(config, "lang", "en_US", "Default language file");
    public static final ConfigSetting USE_TAX = new ConfigSetting(config, "tax.enabled", true, "Should tax be applied to the purchase?");
    public static final ConfigSetting TAX_AMOUNT = new ConfigSetting(config, "tax.amount", 13.0D, "Tax percentage as decimal");
    public static final ConfigSetting USE_CART_SYSTEM = new ConfigSetting(config, "use cart system", true, "Should the cart system be used?");
    public static final ConfigSetting ONLY_ALLOW_BASE_ITEM_SALE = new ConfigSetting(config, "misc.only allow sale of base items", false, "Enabling this will prevent players from selling", "items that have custom names, lore, enchants", "or data. Unless you know what your", "doing this should stay off.");

    public static final ConfigSetting ECONOMY_PROVIDER = new ConfigSetting(config, "economy provider", "Vault", "Valid Economies: ", "Vault (default)", "PlayerPoints", "RevEnchants");

    /*
    =========== DATABASE OPTIONS ===========
     */
    public static final ConfigSetting DATABASE_USE = new ConfigSetting(config, "database.use database", false, "Should the plugin use a database to store shop data?");
    public static final ConfigSetting DATABASE_HOST = new ConfigSetting(config, "database.host", "localhost", "What is the connection url/host");
    public static final ConfigSetting DATABASE_PORT = new ConfigSetting(config, "database.port", 3306, "What is the port to database (default is 3306)");
    public static final ConfigSetting DATABASE_NAME = new ConfigSetting(config, "database.name", "plugin_dev", "What is the name of the database?");
    public static final ConfigSetting DATABASE_USERNAME = new ConfigSetting(config, "database.username", "root", "What is the name of the user connecting?");
    public static final ConfigSetting DATABASE_PASSWORD = new ConfigSetting(config, "database.password", "Password1.", "What is the password to the user connecting?");
    public static final ConfigSetting DATABASE_USE_SSL = new ConfigSetting(config, "database.use ssl", true, "Should the database connection use ssl?");

    /*
    =========== DISCORD OPTIONS ===========
     */
    public static final ConfigSetting DISCORD_USE = new ConfigSetting(config, "discord.enabled", true, "Should the discord messages be enabled?");
    public static final ConfigSetting DISCORD_ALERT_ON_SELL = new ConfigSetting(config, "discord.alert on item sell", true, "Should a message be sent to the discord server when a user sells an item?");
    public static final ConfigSetting DISCORD_ALERT_ON_BUY = new ConfigSetting(config, "discord.alert on item buy", true, "Should a message be sent to the discord server when a user buys an item?");
    public static final ConfigSetting DISCORD_WEBHOOKS = new ConfigSetting(config, "discord.webhooks", Collections.singletonList("https://discord.com/api/webhooks/828398321407361054/IecxENcYA58qdJX4zzxfJIPsDS364raMtzA6_CPZ7hEp93Ou1LBp32gQ8nEkxHM1O8mR"), "A list of webhook urls (channels) you want a message sent to");
    public static final ConfigSetting DISCORD_MSG_USERNAME = new ConfigSetting(config, "discord.user.username", "Shops", "The name of the user who will send the message");
    public static final ConfigSetting DISCORD_MSG_PFP = new ConfigSetting(config, "discord.user.avatar picture", "https://cdn.kiranhart.com/spigot/shops/icon.png", "The avatar image of the discord user");
    public static final ConfigSetting DISCORD_MSG_USE_RANDOM_COLOUR = new ConfigSetting(config, "discord.msg.use random colour", true, "colour of the message bar");
    public static final ConfigSetting DISCORD_MSG_DEFAULT_COLOUR = new ConfigSetting(config, "discord.msg.default colour", "137-100-100", "The color of the embed, it needs to be in hsb format.", "Separate the numbers with a -");
    public static final ConfigSetting DISCORD_MSG_SELL_TITLE = new ConfigSetting(config, "discord.msg.item sold title", "Item Sold", "The title of the message when a user sells an item.");
    public static final ConfigSetting DISCORD_MSG_BUY_TITLE = new ConfigSetting(config, "discord.msg.item bought title", "Item Bought", "The title of the message when a user buys an item");
    public static final ConfigSetting DISCORD_MSG_BUY_CART_TITLE = new ConfigSetting(config, "discord.msg.item bought from cart title", "Item(s) Bought", "The title of th message when a user buys items from the cart");

    public static final ConfigSetting DISCORD_MSG_FIELD_PLAYER_NAME = new ConfigSetting(config, "discord.msg.player.name", "Player");
    public static final ConfigSetting DISCORD_MSG_FIELD_PLAYER_VALUE = new ConfigSetting(config, "discord.msg.player.value", "%player%");
    public static final ConfigSetting DISCORD_MSG_FIELD_PLAYER_INLINE = new ConfigSetting(config, "discord.msg.player.inline", true);

    public static final ConfigSetting DISCORD_MSG_FIELD_QTY_NAME = new ConfigSetting(config, "discord.msg.qty.name", "Quantity");
    public static final ConfigSetting DISCORD_MSG_FIELD_QTY_VALUE = new ConfigSetting(config, "discord.msg.qty.value", "%quantity%");
    public static final ConfigSetting DISCORD_MSG_FIELD_QTY_INLINE = new ConfigSetting(config, "discord.msg.qty.inline", true);

    public static final ConfigSetting DISCORD_MSG_FIELD_PRICE_NAME = new ConfigSetting(config, "discord.msg.price.name", "Price");
    public static final ConfigSetting DISCORD_MSG_FIELD_PRICE_VALUE = new ConfigSetting(config, "discord.msg.price.value", "$%price%");
    public static final ConfigSetting DISCORD_MSG_FIELD_PRICE_INLINE = new ConfigSetting(config, "discord.msg.price.inline", true);

    public static final ConfigSetting DISCORD_MSG_FIELD_ITEM_NAME = new ConfigSetting(config, "discord.msg.item.name", "Purchased Item");
    public static final ConfigSetting DISCORD_MSG_FIELD_ITEM_VALUE = new ConfigSetting(config, "discord.msg.item.value", "%item_name%");
    public static final ConfigSetting DISCORD_MSG_FIELD_ITEM_INLINE = new ConfigSetting(config, "discord.msg.item.inline", true);

    public static final ConfigSetting DISCORD_MSG_FIELD_ITEMS_NAME = new ConfigSetting(config, "discord.msg.cart items.name", "Purchased Items");
    public static final ConfigSetting DISCORD_MSG_FIELD_ITEMS_VALUE = new ConfigSetting(config, "discord.msg.cart items.value", "%purchased_items%");
    public static final ConfigSetting DISCORD_MSG_FIELD_ITEMS_INLINE = new ConfigSetting(config, "discord.msg.cart items.inline", true);

    /*
   =========== GLOBAL BUTTONS FOR GUIS ===========
    */
    public static final ConfigSetting GUI_BACK_BTN_ITEM = new ConfigSetting(config, "guis.global items.back button.item", "ARROW", "Settings for the back button");
    public static final ConfigSetting GUI_BACK_BTN_NAME = new ConfigSetting(config, "guis.global items.back button.name", "&e<< Back");
    public static final ConfigSetting GUI_BACK_BTN_LORE = new ConfigSetting(config, "guis.global items.back button.lore", Arrays.asList("&7Click the button to go", "&7back to the previous page."));

    public static final ConfigSetting GUI_CLOSE_BTN_ITEM = new ConfigSetting(config, "guis.global items.close button.item", "BARRIER", "Settings for the close button");
    public static final ConfigSetting GUI_CLOSE_BTN_NAME = new ConfigSetting(config, "guis.global items.close button.name", "&cClose");
    public static final ConfigSetting GUI_CLOSE_BTN_LORE = new ConfigSetting(config, "guis.global items.close button.lore", Collections.singletonList("&7Click to close this menu."));

    public static final ConfigSetting GUI_NEXT_BTN_ITEM = new ConfigSetting(config, "guis.global items.next button.item", "ARROW", "Settings for the next button");
    public static final ConfigSetting GUI_NEXT_BTN_NAME = new ConfigSetting(config, "guis.global items.next button.name", "&eNext >>");
    public static final ConfigSetting GUI_NEXT_BTN_LORE = new ConfigSetting(config, "guis.global items.next button.lore", Arrays.asList("&7Click the button to go", "&7to the next page."));

    public static final ConfigSetting GUI_REFRESH_BTN_ITEM = new ConfigSetting(config, "guis.global items.refresh button.item", "NETHER_STAR", "Settings for the refresh page");
    public static final ConfigSetting GUI_REFRESH_BTN_NAME = new ConfigSetting(config, "guis.global items.refresh button.name", "&6&LRefresh Page");
    public static final ConfigSetting GUI_REFRESH_BTN_LORE = new ConfigSetting(config, "guis.global items.refresh button.lore", Arrays.asList("&7Click to refresh the currently", "&7available auction listings."));

    /*
   =========== SHOPS MAIN GUI OPTIONS ===========
    */
    public static final ConfigSetting GUI_SHOPS_TITLE = new ConfigSetting(config, "guis.shops.title", "&eShops", "The name of the inventory");
    public static final ConfigSetting GUI_SHOPS_DYNAMIC = new ConfigSetting(config, "guis.shops.dynamic", true, "Should the inventory size according to the total amount of shops?");
    public static final ConfigSetting GUI_SHOPS_SIZE = new ConfigSetting(config, "guis.shops.size", 6, "If dynamic is false, it will be use this size (min 2, max 6)");
    public static final ConfigSetting GUI_SHOPS_AUTO_ARRANGE = new ConfigSetting(config, "guis.shops.auto arrange", true, "Should the items auto arrange themselves inside the gui, or should items use their specified slots?");
    public static final ConfigSetting GUI_SHOPS_FILL_BG = new ConfigSetting(config, "guis.shops.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOPS_BG_ITEM = new ConfigSetting(config, "guis.shops.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOPS_ITEM_NAME = new ConfigSetting(config, "guis.shops.shop name", "%shop_display_name%", "Valid Placeholders", "%shop_display_name%", "%shop_id%");
    public static final ConfigSetting GUI_SHOPS_ITEM_LORE = new ConfigSetting(config, "guis.shops.shop lore", Arrays.asList(
            "%shop_description%",
            "&7Sell Only: &e%shop_is_sell_only%",
            "&7Buy Only: &e%shop_is_buy_only%",
            "&7Total Items: &e%shop_item_count%",
            "",
            "&7Click to buy items from this shop."
    ), "Valid Placeholders", "%shop_description%", "%shop_item_count%", "%shop_is_sell_only%", "%shop_is_buy_only%");

    /*
   =========== SHOPS EDIT GUI OPTIONS ===========
    */
    public static final ConfigSetting GUI_SHOP_EDIT_TITLE = new ConfigSetting(config, "guis.shop edit.title", "&eEditing %shop_id% Shop", "The name of the inventory", "Valid Placeholders", "%shop_id%");
    public static final ConfigSetting GUI_SHOP_EDIT_FILL_BG = new ConfigSetting(config, "guis.shop edit.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_EDIT_BG_ITEM = new ConfigSetting(config, "guis.shop edit.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_NAME = new ConfigSetting(config, "guis.shop edit.items.display icon.name", "&eCurrent Shop Icon");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_LORE = new ConfigSetting(config, "guis.shop edit.items.display icon.lore", Collections.singletonList("&7Click to change the display icon"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_ITEM = new ConfigSetting(config, "guis.shop edit.items.display name.item", XMaterial.OAK_SIGN.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_NAME = new ConfigSetting(config, "guis.shop edit.items.display name.name", "%shop_display_name%");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_LORE = new ConfigSetting(config, "guis.shop edit.items.display name.lore", Collections.singletonList("&7Click to change the display name"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_ITEM = new ConfigSetting(config, "guis.shop edit.items.description.item", XMaterial.WRITABLE_BOOK.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_NAME = new ConfigSetting(config, "guis.shop edit.items.description.name", "&eShop Description");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_LORE = new ConfigSetting(config, "guis.shop edit.items.description.lore", Arrays.asList(
            "%shop_description%",
            "",
            "&7Click to change the description"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_ITEM = new ConfigSetting(config, "guis.shop edit.items.contents.item", XMaterial.CHEST.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_NAME = new ConfigSetting(config, "guis.shop edit.items.contents.name", "&eShop Contents");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_LORE = new ConfigSetting(config, "guis.shop edit.items.contents.lore", Arrays.asList(
            "&7Total Items&f: &e%shop_item_count%",
            "&7Click to change item prices to remove them."
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.name", "&aShop Public");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.lore", Collections.singletonList("&7Click to make shop &cprivate"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.name", "&cShop Private");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.lore", Collections.singletonList("&7Click to make shop &apublic"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.name", "&aShop is Sell Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.lore", Collections.singletonList("&7Click to disable sell only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.name", "&cShop isn't Sell Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.lore", Collections.singletonList("&7Click to enable sell only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.name", "&aShop is Buy Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.lore", Collections.singletonList("&7Click to disable buy only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.name", "&cShop isn't Buy Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.lore", Collections.singletonList("&7Click to enable buy only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_ITEM = new ConfigSetting(config, "guis.shop edit.items.sell bonus.item", XMaterial.SUNFLOWER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_NAME = new ConfigSetting(config, "guis.shop edit.items.sell bonus.name", "&eSell Bonus");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_LORE = new ConfigSetting(config, "guis.shop edit.items.sell bonus.lore", Arrays.asList(
            "&7Current Bonus&f: &a%shop_sell_bonus%%",
            "&7Status&f: &e%shop_sell_bonus_enable%",
            "",
            "&7Left-Click to adjust percentage",
            "&7Right-Click to toggle it on/off"
    ), "Valid Placeholders", "%shop_sell_bonus%", "%shop_sell_bonus_enable%");

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_ITEM = new ConfigSetting(config, "guis.shop edit.items.buy discount.item", XMaterial.SUNFLOWER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_NAME = new ConfigSetting(config, "guis.shop edit.items.buy discount.name", "&eBuy Discount");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_LORE = new ConfigSetting(config, "guis.shop edit.items.buy discount.lore", Arrays.asList(
            "&7Current Discount&f: &a%shop_buy_discount%%",
            "&7Status&f: &e%shop_buy_discount_enable%",
            "",
            "&7Left-Click to adjust percentage",
            "&7Right-Click to toggle it on/off"
    ), "Valid Placeholders", "%shop_buy_discount%", "%shop_buy_discount_enable%");


    // Permission to see toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.name", "&aRequire Permission to See Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.lore", Collections.singletonList("&7Toggle permission to see to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.name", "&cRequire Permission to See Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.lore", Collections.singletonList("&7Toggle permission to see to &aOn"));

    // Permission to see toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.name", "&aRequire Permission to Sell Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.lore", Collections.singletonList("&7Toggle permission to sell to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.name", "&cRequire Permission to Sell Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.lore", Collections.singletonList("&7Toggle permission to sell to &aOn"));

    // Permission to buy toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.name", "&aRequire Permission to Buy Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.lore", Collections.singletonList("&7Toggle permission to buy to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.name", "&cRequire Permission to Buy Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.lore", Collections.singletonList("&7Toggle permission to buy to &aOn"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.see permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.see permission.name", "&eSee Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.see permission.lore", Arrays.asList(
            "&7Click to change the see permission",
            "",
            "&7Current Permission&f: &e%shop_see_permission%"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.sell permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.sell permission.name", "&eSell Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.sell permission.lore", Arrays.asList(
            "&7Click to change the sell permission",
            "",
            "&7Current Permission&f: &e%shop_sell_permission%"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.buy permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.buy permission.name", "&eBuy Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.buy permission.lore", Arrays.asList(
            "&7Click to change the buy permission",
            "",
            "&7Current Permission&f: &e%shop_buy_permission%"
    ));

    /*
    =========== SHOP LIST GUI OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_LIST_TITLE = new ConfigSetting(config, "guis.shop list.title", "&eListing Shops", "The name of the inventory");
    public static final ConfigSetting GUI_SHOP_LIST_FILL_BG = new ConfigSetting(config, "guis.shop list.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_LIST_BG_ITEM = new ConfigSetting(config, "guis.shop list.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_LIST_ITEM_NAME = new ConfigSetting(config, "guis.shop list.shop name", "%shop_display_name%", "Valid Placeholders", "%shop_display_name%", "%shop_id%");
    public static final ConfigSetting GUI_SHOP_LIST_ITEM_LORE = new ConfigSetting(config, "guis.shop list.shop lore", Arrays.asList(
            "&7ID: &e%shop_id%",
            "&7Display Name: &e%shop_display_name%",
            "&7Public: &e%shop_is_public%",
            "&7Sell Only: &e%shop_is_sell_only%",
            "&7Buy Only: &e%shop_is_buy_only%",
            "&7Total Items: &e%shop_item_count%",
            "",
            "&7Click to edit this shop"
    ), "Valid Placeholders", "%shop_display_name%", "%shop_id%", "%shop_is_public%", "%shop_item_count%", "%shop_is_sell_only%", "%shop_is_buy_only%");

    /*
    =========== SHOP CONTENTS OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_CONTENTS_TITLE = new ConfigSetting(config, "guis.shop contents.title", "&e%shop_display_name%", "The name of the inventory", "Valid Placeholders", "%shop_id%", "%shop_display_name%");
    public static final ConfigSetting GUI_SHOP_CONTENTS_FILL_BG = new ConfigSetting(config, "guis.shop contents.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_CONTENTS_BG_ITEM = new ConfigSetting(config, "guis.shop contents.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_CONTENTS_ITEM_NAME = new ConfigSetting(config, "guis.shop contents.item name", "%shop_item_name%", "Valid Placeholders", "%shop_item_name%");
    public static final ConfigSetting GUI_SHOP_CONTENTS_ITEM_LORE = new ConfigSetting(config, "guis.shop contents.item lore", Arrays.asList(
            "&7Left-click to purchase this item.",
            "&7Right-click to add x1 to your cart.",
            "",
            "&7Sell Price&f: &a$%shop_item_sell_price%",
            "&7Buy Price&f: &a$%shop_item_buy_price%",
            "&7Base Quantity&f: &a%shop_item_quantity%"
    ), "Valid Placeholders", "%shop_item_sell_price%", "%shop_item_buy_price%", "%shop_item_quantity%");

    public static final ConfigSetting GUI_SHOP_CONTENTS_ITEM_LORE_EDIT = new ConfigSetting(config, "guis.shop contents.item lore editing", Arrays.asList(
            "&7Left-click to edit the buy price",
            "&7Right-click to edit the sell price",
            "&7Shift Left-click to toggle buy only",
            "&7Shift Right-click to toggle sell only",
            "",
            "&7Sell Price&f: &a$%shop_item_sell_price%",
            "&7Buy Price&f: &a$%shop_item_buy_price%",
            "&7Sell Only&f: &a%shop_item_sell_only%",
            "&7Buy Only&f: &a%shop_item_buy_only%"
    ), "Valid Placeholders", "%shop_item_sell_price%", "%shop_item_buy_price%", "%shop_item_sell_only%", "%shop_item_buy_only%");

    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_ITEM = new ConfigSetting(config, "guis.shop contents.cart item", XMaterial.CHEST.name());
    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_NAME = new ConfigSetting(config, "guis.shop contents.cart name", "&eView Cart");
    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_LORE = new ConfigSetting(config, "guis.shop contents.cart lore", Arrays.asList(
            "&7Click to view your cart",
            "",
            "&7Cart Items&f: &a%shop_cart_item_count%",
            "&7Cart Total&f: &a$%shop_cart_total%"
    ), "Valid Placeholders", "%shop_cart_item_count%", "%shop_cart_total%");

    /*
   =========== SHOP CONTENTS OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_CART_TITLE = new ConfigSetting(config, "guis.cart.title", "&eYour Cart", "The name of the inventory");
    public static final ConfigSetting GUI_SHOP_CART_FILL_BG = new ConfigSetting(config, "guis.cart.fill background", false, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_CART_BG_ITEM = new ConfigSetting(config, "guis.cart.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_CART_BAR_ITEM = new ConfigSetting(config, "guis.cart.bar item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_ITEM = new ConfigSetting(config, "guis.cart.items.clear.item", XMaterial.RED_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_NAME = new ConfigSetting(config, "guis.cart.items.clear.name", "&cClear Cart");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_LORE = new ConfigSetting(config, "guis.cart.items.clear.lore", Collections.singletonList("&7Click to clear the cart"));

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_ITEM = new ConfigSetting(config, "guis.cart.items.confirm.item", XMaterial.LIME_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_NAME = new ConfigSetting(config, "guis.cart.items.confirm.name", "&aConfirm Purchase");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_LORE = new ConfigSetting(config, "guis.cart.items.confirm.lore", Arrays.asList(
            "&7Left-Click to make purchase",
            "&7Right-Click to sell contents"
    ));

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_ITEM = new ConfigSetting(config, "guis.cart.items.info.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_NAME = new ConfigSetting(config, "guis.cart.items.info.name", "&eCart Information");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_LORE = new ConfigSetting(config, "guis.cart.items.info.lore", Arrays.asList(
            "&7Total Items&f: &e%shop_cart_item_count%",
            "",
            "&7Sub-Total&f: &a$%shop_cart_sub_total%",
            "&7Tax&f: &a%shop_cart_tax%%",
            "&7Discounts&f: &a- $%shop_cart_discounts%",
            "",
            "&7Total&f: &a$%shop_cart_total%"
    ));

    public static final ConfigSetting GUI_SHOP_CART_ITEM_LORE = new ConfigSetting(config, "guis.cart.items.item lore", Arrays.asList(
            "&7Quantity&f: &ax%shop_item_quantity%",
            "&7Price&f: &a$%shop_item_price%"
    ));

    /*
    =========== SHOP ITEM SELECT OPTIONS ===========
     */
    public static final ConfigSetting GUI_ITEM_SELECT_TITLE = new ConfigSetting(config, "guis.item select.title", "&eItem Selection", "The name of the inventory");
    public static final ConfigSetting GUI_ITEM_SELECT_FILL_BG = new ConfigSetting(config, "guis.item select.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_ITEM_SELECT_BG_ITEM = new ConfigSetting(config, "guis.item select.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_ITEM_SELECT_USE_DEFAULT_SLOTS = new ConfigSetting(config, "guis.item select.use default slots", true, "Should items be placed in their default slots?");
    public static final ConfigSetting GUI_ITEM_SELECT_ALLOW_SHIFT_CLICK_STACK = new ConfigSetting(config, "guis.item select.shift click to +/- 64", true, "When an increment/decrement button is shift clicked, should it add 64 to the total quantity?");

    public static final ConfigSetting GUI_ITEM_SELECT_INC_ONE = new ConfigSetting(config, "guis.item select.increments.one", 1, "How much should the quantity increase on the first pane click");
    public static final ConfigSetting GUI_ITEM_SELECT_INC_TWO = new ConfigSetting(config, "guis.item select.increments.two", 5, "How much should the quantity increase on the second pane click");
    public static final ConfigSetting GUI_ITEM_SELECT_INC_THREE = new ConfigSetting(config, "guis.item select.increments.three", 10, "How much should the quantity increase on the third pane click");
    public static final ConfigSetting GUI_ITEM_SELECT_DECR_ONE = new ConfigSetting(config, "guis.item select.decrements.one", 1, "How much should the quantity decrease on the first pane click");
    public static final ConfigSetting GUI_ITEM_SELECT_DECR_TWO = new ConfigSetting(config, "guis.item select.decrements.two", 5, "How much should the quantity decrease on the second pane click");
    public static final ConfigSetting GUI_ITEM_SELECT_DECR_THREE = new ConfigSetting(config, "guis.item select.decrements.three", 10, "How much should the quantity decrease on the third pane click");

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_ITEM = new ConfigSetting(config, "guis.item select.items.add to cart.item", XMaterial.CHEST.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_NAME = new ConfigSetting(config, "guis.item select.items.add to cart.name", "&eAdd to cart");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_LORE = new ConfigSetting(config, "guis.item select.items.add to cart.lore", Arrays.asList(
            "&7Click to add this item to your",
            "&7cart and continue shopping."
    ));

    public static final ConfigSetting GUI_SHOP_ITEM_SELECT_ITEMS_INFO_ITEM = new ConfigSetting(config, "guis.item select.items.info.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_ITEM_SELECT_ITEMS_INFO_NAME = new ConfigSetting(config, "guis.item select.items.info.name", "&eItem Information");
    public static final ConfigSetting GUI_SHOP_ITEM_SELECT_ITEMS_INFO_LORE = new ConfigSetting(config, "guis.item select.items.info.lore", Arrays.asList(
            "&7Stack Quantity&f: &e%item_stack_quantity%",
            "&7Quantity&f: &e%item_quantity%",
            "&7Base Price&f: &e%item_price%",
            "",
            "&7Sub-Total&f: &a$%item_sub_total%",
            "&7Tax&f: &a%item_tax%%",
            "&7Discounts&f: &a- $%item_discounts%",
            "",
            "&7Total&f: &a$%item_total%"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_SELL_BUY_ITEM = new ConfigSetting(config, "guis.item select.items.sell and buy.item", XMaterial.SUNFLOWER.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_SELL_BUY_NAME = new ConfigSetting(config, "guis.item select.items.sell and buy.name", "&ePurchase / Sell");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_SELL_BUY_LORE = new ConfigSetting(config, "guis.item select.items.sell and buy.lore", Arrays.asList(
            "&7Left-click to purchase item(s)",
            "&7Right-click to sell item(s)"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_ONE_ITEM = new ConfigSetting(config, "guis.item select.items.increment one.item", XMaterial.LIME_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_ONE_NAME = new ConfigSetting(config, "guis.item select.items.increment one.name", "&a+%inc_one_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_ONE_LORE = new ConfigSetting(config, "guis.item select.items.increment one.lore", Collections.singletonList(
            "&7Click to add &e%inc_one_amount% &7to the quantity"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_TWO_ITEM = new ConfigSetting(config, "guis.item select.items.increment two.item", XMaterial.LIME_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_TWO_NAME = new ConfigSetting(config, "guis.item select.items.increment two.name", "&a+%inc_two_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_TWO_LORE = new ConfigSetting(config, "guis.item select.items.increment two.lore", Collections.singletonList(
            "&7Click to add &e%inc_two_amount% &7to the quantity"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_THREE_ITEM = new ConfigSetting(config, "guis.item select.items.increment three.item", XMaterial.LIME_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_THREE_NAME = new ConfigSetting(config, "guis.item select.items.increment three.name", "&a+%inc_three_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_INC_THREE_LORE = new ConfigSetting(config, "guis.item select.items.increment three.lore", Collections.singletonList(
            "&7Click to add &e%inc_three_amount% &7to the quantity"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_ONE_ITEM = new ConfigSetting(config, "guis.item select.items.decrement one.item", XMaterial.RED_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_ONE_NAME = new ConfigSetting(config, "guis.item select.items.decrement one.name", "&c-%decr_one_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_ONE_LORE = new ConfigSetting(config, "guis.item select.items.decrement one.lore", Collections.singletonList(
            "&7Click to remove &e%decr_one_amount% &7from the quantity"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_TWO_ITEM = new ConfigSetting(config, "guis.item select.items.decrement two.item", XMaterial.RED_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_TWO_NAME = new ConfigSetting(config, "guis.item select.items.decrement two.name", "&c-%decr_two_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_TWO_LORE = new ConfigSetting(config, "guis.item select.items.decrement two.lore", Collections.singletonList(
            "&7Click to remove &e%decr_two_amount% &7from the quantity"
    ));

    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_THREE_ITEM = new ConfigSetting(config, "guis.item select.items.decrement three.item", XMaterial.RED_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_THREE_NAME = new ConfigSetting(config, "guis.item select.items.decrement three.name", "&c-%decr_three_amount%");
    public static final ConfigSetting GUI_ITEM_SELECT_ITEMS_DECR_THREE_LORE = new ConfigSetting(config, "guis.item select.items.decrement three.lore", Collections.singletonList(
            "&7Click to remove &e%decr_three_amount% &7from the quantity"
    ));

    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
