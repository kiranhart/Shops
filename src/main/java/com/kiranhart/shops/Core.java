package com.kiranhart.shops;

import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.events.HartInventoryListener;
import com.kiranhart.shops.util.locale.Locale;
import com.kiranhart.shops.util.ConfigWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Core extends JavaPlugin {

    private static Core instance;
    private HashMap<Settings, Object> settings;
    private Locale locale;

    private ConfigWrapper settingsFile;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        // setup the default configuration file
        getConfig().options().copyDefaults(true);
        // save the default configuration file
        saveDefaultConfig();

        settings = new HashMap<>();

        // setup the settings file
        this.settingsFile = new ConfigWrapper(this, "", "Settings.yml");
        this.settingsFile.saveConfig();

        // load the plugin settings
        Settings.setDefaults();
        Settings.loadSettings();

        // setup the locale
        new Locale(this, "en_US");
        this.locale = Locale.getLocale(getConfig().getString("lang"));

        // register Hart Inventory
        Bukkit.getPluginManager().registerEvents(new HartInventoryListener(), this);
    }

    @Override
    public void onDisable() {
    }

    /**
     * @return the instance of the 'Core' class
     */
    public static Core getInstance() {
        return instance;
    }

    /**
     * @return locale instance
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return the settings file
     */
    public ConfigWrapper getSettings() {
        return settingsFile;
    }

    public HashMap<Settings, Object> getLoadedSetting() {
        return this.settings;
    }
}
