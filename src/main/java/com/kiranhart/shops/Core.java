package com.kiranhart.shops;

import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.commands.CommandManager;
import com.kiranhart.shops.api.events.HartInventoryListener;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.Transaction;
import com.kiranhart.shops.util.helpers.ConfigWrapper;
import com.kiranhart.shops.util.locale.Locale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;

public final class Core extends JavaPlugin {

    private static Core instance;
    private Economy economy = null;

    private HashMap<Settings, Object> settings;
    private Locale locale;

    private ConfigWrapper settingsFile;
    private ConfigWrapper shopsFile;
    private ConfigWrapper transactionFile;

    private CommandManager commandManager;

    private LinkedList<Shop> shops;
    private LinkedList<Transaction> transactions;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // setup the default configuration file
        getConfig().options().copyDefaults(true);
        // save the default configuration file
        saveDefaultConfig();

        this.settings = new HashMap<>();
        this.shops = new LinkedList<>();
        this.transactions = new LinkedList<>();

        // setup the settings file
        this.settingsFile = new ConfigWrapper(this, "", "Settings.yml");
        this.settingsFile.saveConfig();

        // setup the shops file
        this.shopsFile = new ConfigWrapper(this, "", "Shops.yml");
        this.shopsFile.saveConfig();

        // setup transactions file
        this.transactionFile = new ConfigWrapper(this, "", "Transactions.yml");
        this.transactionFile.saveConfig();

        // load the plugin settings
        Settings.setDefaults();
        Settings.loadSettings();

        // setup the locale
        new Locale(this, "en_US");
        this.locale = Locale.getLocale(getConfig().getString("lang"));

        // register Hart Inventory
        Bukkit.getPluginManager().registerEvents(new HartInventoryListener(), this);

        ShopAPI.get().loadAllShops();

        // commands
        this.commandManager = new CommandManager();
        this.commandManager.init();
    }

    @Override
    public void onDisable() {
        //save any stored transactions
        transactions.forEach(transaction -> ShopAPI.get().saveTransactions(transaction));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
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
     * @return the transactions file
     */
    public ConfigWrapper getTransactionFile() {
        return transactionFile;
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

    /**
     * Get a collection of transactions (will be empty if save right away is true)
     *
     * @return a list containing all the transactions since the plugin was enabled
     * this will also save empty if auto save is on, as it save each of them.
     */
    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * get vault economy
     *
     * @return economy
     */
    public Economy getEconomy() {
        return economy;
    }
}
