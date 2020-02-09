package com.kiranhart.shops.util;

import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.util.SettingsManager;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:23 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Debugger {

    public static void report(Exception e, boolean show) {
        if ((boolean) SettingsManager.get(Settings.USE_DEBUGGER)) {
            if (show) {
                e.printStackTrace();
            }
        }
    }
}
