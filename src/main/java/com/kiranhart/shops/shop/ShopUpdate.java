package com.kiranhart.shops.shop;

import com.kiranhart.shops.Core;
import org.bukkit.ChatColor;

import java.util.EnumSet;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/4/2020
 * Time Created: 4:32 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopUpdate {

    public enum MAJOR_UPDATE {

        DISCOUNT_SYSTEM("1.0.9"),
        DISCOUNT_SYSTEM_RELEASE("1.1.0")


        ;

        private String version;
        MAJOR_UPDATE(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

    public ShopUpdate(MAJOR_UPDATE defaultVersion) {
        if (!Core.getInstance().getVersionFile().getConfig().contains("version")) {
            Core.getInstance().getVersionFile().getConfig().set("version", Core.getInstance().getDescription().getVersion());
            Core.getInstance().getVersionFile().saveConfig();

            // run each update
            EnumSet.allOf(MAJOR_UPDATE.class).forEach(this::runUpdate);
        } else {
            Core.getInstance().getVersionFile().getConfig().set("version", Core.getInstance().getDescription().getVersion());
            Core.getInstance().getVersionFile().saveConfig();
            runUpdate(defaultVersion);
        }
    }

    private void runUpdate(MAJOR_UPDATE update) {
        if (update == MAJOR_UPDATE.DISCOUNT_SYSTEM) {
            if (Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops") != null) {
                Core.getInstance().getShopsFile().getConfig().getConfigurationSection("shops").getKeys(false).forEach(shop -> {
                    if (!Core.getInstance().getShopsFile().getConfig().contains("shops." + shop  + ".discount.enabled")) {
                        Core.getInstance().getShopsFile().getConfig().set("shops." + shop  + ".discount.enabled", false);
                        Core.getInstance().getShopsFile().getConfig().set("shops." + shop  + ".discount.amount", 0.0);
                    }
                });
                Core.getInstance().getShopsFile().saveConfig();
            }
            Core.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Finished Shop Update: &b" + update.name() + "&f(&6" + update.getVersion() + "&f)"));
        }
    }
}
