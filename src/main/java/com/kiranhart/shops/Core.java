package com.kiranhart.shops;

import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.commands.CommandManager;
import com.kiranhart.shops.events.HartInventoryListener;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.util.helpers.ConfigWrapper;
import com.kiranhart.shops.util.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;

public final class Core extends JavaPlugin {

    private static Core instance;
    private HashMap<Settings, Object> settings;
    private Locale locale;

    private ConfigWrapper settingsFile;
    private ConfigWrapper shopsFile;
    private CommandManager commandManager;

    private LinkedList<Shop> shops;

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

        this.settings = new HashMap<>();
        this.shops = new LinkedList<>();

        // setup the settings file
        this.settingsFile = new ConfigWrapper(this, "", "Settings.yml");
        this.settingsFile.saveConfig();

        // setup the shops file
        this.shopsFile = new ConfigWrapper(this, "", "Shops.yml");
        this.shopsFile.saveConfig();

        // load the plugin settings
        Settings.setDefaults();
        Settings.loadSettings();

        // setup the locale
        new Locale(this, "en_US");
        this.locale = Locale.getLocale(getConfig().getString("lang"));

        // register Hart Inventory
        Bukkit.getPluginManager().registerEvents(new HartInventoryListener(), this);

        // commands
        this.commandManager = new CommandManager();
        this.commandManager.init();
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

    /**
     * @return the shops file
     */
    public ConfigWrapper getShopsFile() {
        return shopsFile;
    }

    /**
     * @return a hashmap of loaded settings
     */
    public HashMap<Settings, Object> getLoadedSetting() {
        return this.settings;
    }

    /**
     * Get a collection of shops
     *
     * @return shops
     */
    public LinkedList<Shop> getShops() {
        return shops;
    }
}
