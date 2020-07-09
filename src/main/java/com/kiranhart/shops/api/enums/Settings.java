package com.kiranhart.shops.api.enums;

import com.kiranhart.shops.Core;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:31 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public enum Settings {

    USE_DEBUGGER("use-debugger", true),
    USE_UPDATE_CHECKER("use-update-checker", true),
    UPDATE_DELAY("update-delay", 600),

    DYNAMIC_SHOP_SIZE("dynamic-shop-size", true),
    SHOW_PRIVATE_SHOP_IN_SELECT("show-private-shop-in-select", false),
    ALLOW_PURCHASE_FROM_PRIVATE_SHOP("allow-purchase-from-private-shop", false),
    ALLOW_ADMIN_PURCHASE_OVERRIDE("allow-admin-purchase-override", true),
    SAVE_TRANSACTION_TO_FILE_RIGHT_AWAY("save-transaction-to-file-right-away", true),
    SEND_DISCORD_MSG_ON_TRANSACTION("send-discord-msg-on-transaction", true),
    GIVE_RECEIPT_ON_PURCHASE("give-receipt-on-purchaase", true),
    USE_AUTO_BACK_BUTTONS_ON_SHOP("use-auto-back-buttons-on-shop", true),

    INCREMENT_FIRST("increment-first", 1),
    INCREMENT_SECOND("increment-second", 5),

    DECREMENT_FIRST("decrement-first", 1),
    DECREMENT_SECOND("decrement-second", 5),

    MAX_PURCHASE_ITEMS("max-purchase-items", 1200),
    ALLOW_PURCHASE_WITH_FULL_INVENTORY("allow-purchase-with-full-inventory", false),


    ;

    private String setting;
    private Object data;

    Settings(String setting, Object data) {
        this.setting = setting;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public String getSetting() {
        return setting;
    }

    public static void setDefaults() {
        for (Settings value : Settings.values()) {
            if (!Core.getInstance().getSettings().getConfig().contains(value.getSetting())) {
                Core.getInstance().getSettings().getConfig().set(value.getSetting(), value.getData());
            }
        }
        Core.getInstance().getSettings().saveConfig();
    }

    public static void loadSettings() {
        for (Settings value : Settings.values()) {
            Core.getInstance().getLoadedSetting().put(value, Core.getInstance().getSettings().getConfig().get(value.getSetting()));
        }
    }
}
