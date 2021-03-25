package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 12:39 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShops extends Gui {

    List<Shop> shops;
    int clicksToEdit = 0;
    boolean editing = false;

    public GUIShops() {
        setTitle(TextUtils.formatText(Settings.GUI_SHOPS_TITLE.getString()));
        setDefaultItem(Settings.GUI_SHOPS_BG_ITEM.getMaterial().parseItem());
        setUseLockedCells(Settings.GUI_SHOPS_FILL_BG.getBoolean());
        setAcceptsItems(false);
        setAllowDrops(false);
        handleEvents();
        draw();
    }

    private void adjustSize() {
        this.shops = Shops.getInstance().getShopManager().getShops();

        if (Settings.GUI_SHOPS_DYNAMIC.getBoolean()) {
            // TODO loop this
            if (this.shops.size() >= 1 && this.shops.size() <= 9) setRows(1);
            if (this.shops.size() >= 10 && this.shops.size() <= 18) setRows(2);
            if (this.shops.size() >= 19 && this.shops.size() <= 27) setRows(3);
            if (this.shops.size() >= 28 && this.shops.size() <= 36) setRows(4);
            if (this.shops.size() >= 37 && this.shops.size() <= 45) setRows(5);
            if (this.shops.size() >= 46) setRows(6);
        } else {
            setRows(Math.max(1, Settings.GUI_SHOPS_SIZE.getInt()));
        }

        int toDivide = (int) Math.ceil(this.shops.size() / (double) (9 * getRows()));
        pages = (int) Math.max(1, Math.ceil(this.shops.size() / getRows() == 6 ? 45 : toDivide));
    }

    private void draw() {
        reset();
        adjustSize();

        int slot = 0;
        long perPage = getRows() < 6 ? (getRows() * 9L) : 45L;
        List<Shop> data = this.shops.stream().skip((page - 1) * perPage).limit(perPage).collect(Collectors.toList());
        for (Shop shop : data) {
            setItem(Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean() ? slot++ : editing ? slot++ : shop.getSlot(), getShopIcon(shop));
        }
    }

    private void handleEvents() {
        if (!Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean()) {
            setPrivateDefaultAction(e -> {
                if (e.clickType == ClickType.SHIFT_RIGHT && e.player.hasPermission("shops.admin")) clicksToEdit++;
                if (clicksToEdit >= 8 && e.player.hasPermission("shops.admin")) {
                    clicksToEdit = 0;
                    editing = true;
                    setUnlockedRange(0, (9 * getRows()) - 1);
                    setAcceptsItems(true);
                    draw();
                    Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&aYou're now editing this inventory!")).sendPrefixedMessage(e.player);
                }
            });
        }

        setOnClose(close -> {
            for (int i = 0; i < (getRows() * 9) - 1; i++) {

            }
        });
    }

    private ItemStack getShopIcon(Shop shop) {
        ItemStack stack = ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(shop.getDisplayIcon()), Settings.GUI_SHOPS_ITEM_NAME.getString(), Settings.GUI_SHOPS_ITEM_LORE.getStringList(), new HashMap<String, Object>(){{
            put("%shop_display_name%", shop.getDisplayName());
            put("%shop_description%", shop.getDescription());
            put("%shop_is_sell_only%", shop.isSellOnly());
            put("%shop_is_buy_only%", shop.isBuyOnly());
            put("%shop_item_count%", shop.getShopItems().size());
        }});
        stack = NBTEditor.set(stack, shop.getId(), "shops:shop_id");
        return stack;
    }
}