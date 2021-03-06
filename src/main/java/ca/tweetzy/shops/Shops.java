package ca.tweetzy.shops;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.database.DataMigrationManager;
import ca.tweetzy.core.database.DatabaseConnector;
import ca.tweetzy.core.database.MySQLConnector;
import ca.tweetzy.core.gui.GuiManager;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.shops.api.UpdateChecker;
import ca.tweetzy.shops.commands.*;
import ca.tweetzy.shops.database.DataManager;
import ca.tweetzy.shops.database.migrations._1_InitialMigration;
import ca.tweetzy.shops.economy.EconomyManager;
import ca.tweetzy.shops.listeners.PlayerListener;
import ca.tweetzy.shops.listeners.ShopListeners;
import ca.tweetzy.shops.managers.ShopManager;
import ca.tweetzy.shops.settings.LocaleSettings;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.Shop;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 7:09 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@SuppressWarnings("unused")
public class Shops extends TweetyPlugin {

    @Getter
    private static Shops instance;

    @Getter
    private final GuiManager guiManager = new GuiManager(this);

    @Getter
    private final Config data = new Config(this, "data.yml");

    @Getter
    private final HashMap<UUID, Shop> outOfGuiAccess = new HashMap<>();

    @Getter
    private final HashMap<UUID, ArrayList<CartItem>> playerCart = new HashMap<>();

    @Getter
    private CommandManager commandManager;

    @Getter
    private ShopManager shopManager;

    @Getter
    private EconomyManager economyManager;

    @Getter
    private DatabaseConnector databaseConnector;

    @Getter
    private DataManager dataManager;

    @Getter
    private UpdateChecker.UpdateStatus updateStatus;

    protected Metrics metrics;

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        TweetyCore.registerPlugin(this, 2, XMaterial.DIAMOND.name());

        // Stop the plugin if the server version is not 1.8 or higher
        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_7)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Setup the settings file
        Settings.setup();

        // Setup the locale
        setLocale(Settings.LANG.getString());
        LocaleSettings.setup();

        this.economyManager = new EconomyManager(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListeners(), this);

        // Load the data file
        this.data.load();
        if (!this.data.isSet("shop data version")) {
            this.data.set("shop data version", 2.0);
            this.data.save();
        }

        // Setup the database if enabled
        if (Settings.DATABASE_USE.getBoolean()) {
            this.databaseConnector = new MySQLConnector(this, Settings.DATABASE_HOST.getString(), Settings.DATABASE_PORT.getInt(), Settings.DATABASE_NAME.getString(), Settings.DATABASE_USERNAME.getString(), Settings.DATABASE_PASSWORD.getString(), Settings.DATABASE_USE_SSL.getBoolean());
            this.dataManager = new DataManager(this.databaseConnector, this);
            DataMigrationManager dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager, new _1_InitialMigration());
            dataMigrationManager.runMigrations();
        }

        // managers
        this.shopManager = new ShopManager();
        this.guiManager.init();
        this.shopManager.loadShops(false, Settings.DATABASE_USE.getBoolean());
        this.shopManager.loadCustomGuiItems(false, Settings.DATABASE_USE.getBoolean());

        // Commands
        this.commandManager = new CommandManager(this);
        this.commandManager.addCommand(new CommandShop()).addSubCommands(
                new CommandCreate(),
                new CommandRemove(),
                new CommandEdit(),
                new CommandSet(),
                new CommandAddItem(),
                new CommandList(),
                new CommandCart(),
                new CommandOpen(),
                new CommandSettings(),
                new CommandReload(),
                new CommandConvert()
        );

        // Perform the update check
        getServer().getScheduler().runTaskLaterAsynchronously(this, () -> this.updateStatus = new UpdateChecker(this, 75600, getConsole()).check().getStatus(), 1L);

        // Metrics, don't ask them if they want it enabled since bStats has it's on system
        this.metrics = new Metrics(this, 6807);
    }

    @Override
    public void onPluginDisable() {
        instance = null;
    }

    @Override
    public void onConfigReload() {
        Settings.setup();
        setLocale(Settings.LANG.getString());
        LocaleSettings.setup();
        this.economyManager = new EconomyManager(this);
        this.shopManager.loadShops(true, Settings.DATABASE_USE.getBoolean());
        this.shopManager.loadCustomGuiItems(true, Settings.DATABASE_USE.getBoolean());
    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    String IS_SONGODA_DOWNLOAD = "%%__SONGODA__%%";
    String SONGODA_NODE = "%%__SONGODA_NODE__%%";
    String TIMESTAMP = "%%__TIMESTAMP__%%";
    String USER = "%%__USER__%%";
    String USERNAME = "%%__USERNAME__%%";
    String RESOURCE = "%%__RESOURCE__%%";
    String NONCE = "%%__NONCE__%%";
}
