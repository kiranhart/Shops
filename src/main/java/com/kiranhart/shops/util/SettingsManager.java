package com.kiranhart.shops.util;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.enums.Settings;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:42 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class SettingsManager {

    /**
     * Get the setting
     *
     * @param setting is the setting you want to get
     * @return the setting if found within the map
     */
    public static Object get(Settings setting) {
        if (Core.getInstance().getLoadedSetting().containsKey(setting)) {
            return Core.getInstance().getLoadedSetting().get(setting);
        }
        return false;
    }

}
