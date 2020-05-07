package com.kiranhart.shops;

import com.kiranhart.shops.api.HartUpdater;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.Version;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.api.events.HartInventoryListener;
import com.kiranhart.shops.commands.CommandManager;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.ShopUpdate;
import com.kiranhart.shops.shop.Transaction;
import com.kiranhart.shops.util.Metrics;
import com.kiranhart.shops.util.SettingsManager;
import com.kiranhart.shops.util.helpers.ConfigWrapper;
import com.kiranhart.shops.util.locale.Locale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;

public final class Core extends JavaPlugin {

    private ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final ShopUpdate.MAJOR_UPDATE currentUpdate = ShopUpdate.MAJOR_UPDATE.DISCOUNT_SYSTEM;

    private static Core instance;
    private HartUpdater updater;
    private Metrics metrics;
    private Economy economy = null;

    private HashMap<Settings, Object> settings;
    private Locale locale;

    private ConfigWrapper settingsFile;
    private ConfigWrapper shopsFile;
    private ConfigWrapper transactionFile;
    private ConfigWrapper versionFile;

    private CommandManager commandManager;

    private LinkedList<Shop> shops;
    private LinkedList<Transaction> transactions;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        long start = System.currentTimeMillis();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b========================================="));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eLoading " + getDescription().getName() + " " + getDescription().getVersion()));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis plugin was designed by Kiran Hart any"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fproblems should be reported directly to him."));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b========================================="));

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Checking server version"));
        if (Version.getCurrentVersion().isOlder(Version.v1_8_R1)) {
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b==========================================="));
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDisabling " + getDescription().getName() + " " + getDescription().getVersion()));
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fServer Version is not supported!"));
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b========================================="));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // vault setup
        if (!setupEconomy()) {
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&c COULD NOT FIND VAULT, DISABLING PLUGIN"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up config.yml"));
        // setup the default configuration file
        getConfig().options().copyDefaults(true);
        // save the default configuration file
        saveDefaultConfig();

        this.settings = new HashMap<>();
        this.shops = new LinkedList<>();
        this.transactions = new LinkedList<>();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up VERSION.yml"));
        // setup the version file
        this.versionFile = new ConfigWrapper(this, "", "VERSION.yml");
        this.versionFile.saveConfig();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up Settings.yml"));
        // setup the settings file
        this.settingsFile = new ConfigWrapper(this, "", "Settings.yml");
        this.settingsFile.saveConfig();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up Shops.yml"));
        // setup the shops file
        this.shopsFile = new ConfigWrapper(this, "", "Shops.yml");
        this.shopsFile.saveConfig();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up Transactions.yml"));
        // setup transactions file
        this.transactionFile = new ConfigWrapper(this, "", "Transactions.yml");
        this.transactionFile.saveConfig();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting and loading settings"));
        // load the plugin settings
        Settings.setDefaults();
        Settings.loadSettings();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up language system"));
        // setup the locale
        new Locale(this, getConfig().getString("lang"));
        this.locale = Locale.getLocale(getConfig().getString("lang"));

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Registering events"));
        // register Hart Inventory
        Bukkit.getPluginManager().registerEvents(new HartInventoryListener(), this);

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Loading all shops"));
        ShopAPI.get().loadAllShops();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bShops&8] &e>>&a Setting up the command manager"));
        // commands
        this.commandManager = new CommandManager();
        this.commandManager.init();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b========================================="));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eLoaded " + getDescription().getName() + " " + getDescription().getVersion()));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        shops.forEach(shop -> {
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', shop.getTitle() + "&f(" + shop.getName() + "&f)" + " Shop&f: " + ((shop.isPublic()) ? "&eEnabled" : "&cDisabled")));
        });
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLoaded successfully in " + (System.currentTimeMillis() - start) + "ms"));
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b========================================="));

        // Run the Shop Update
        new ShopUpdate(currentUpdate);

        // update checker
        if ((boolean) SettingsManager.get(Settings.USE_UPDATE_CHECKER)) {
            //Begin the update checker
            getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
                updater = new HartUpdater(this, getDescription().getVersion());
            }, 0, 20 * (int) SettingsManager.get(Settings.UPDATE_DELAY));
        }

        // metrics
        if (getConfig().getBoolean("metrics")) {
            metrics = new Metrics(this, 6807);
        }
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
     * @return the version file
     */
    public ConfigWrapper getVersionFile() {
        return versionFile;
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

    public ConsoleCommandSender getConsole() {
        return console;
    }
}
