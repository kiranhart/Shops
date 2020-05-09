package com.kiranhart.shops.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.enums.BorderNumbers;
import com.kiranhart.shops.api.enums.Settings;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.events.ShopItemPurchaseEvent;
import com.kiranhart.shops.shop.Receipt;
import com.kiranhart.shops.shop.Shop;
import com.kiranhart.shops.shop.ShopItem;
import com.kiranhart.shops.shop.Transaction;
import com.kiranhart.shops.util.Debugger;
import com.kiranhart.shops.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/14/2020
 * Time Created: 5:23 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PurchaseInventory extends HartInventory {

    private Shop shop;
    private ShopItem shopItem;
    private int total;

    public PurchaseInventory(Shop shop, ShopItem shopItem, int total) {
        this.shop = shop;
        this.shopItem = shopItem;
        this.total = (total <= 0) ? 1 : total;
        this.defaultTitle = ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.purchase-inventory.title"));
        this.defaultSize = 54;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        Transaction transaction;

        try {
            switch (slot) {
                case 20:
                    if (e.getClick() == ClickType.LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total + (int) SettingsManager.get(Settings.INCREMENT_FIRST)).getInventory());
                    }

                    if (e.getClick() == ClickType.SHIFT_LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total + 64).getInventory());
                    }
                    break;
                case 29:
                    if (e.getClick() == ClickType.LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total + (int) SettingsManager.get(Settings.INCREMENT_SECOND)).getInventory());
                    }

                    if (e.getClick() == ClickType.SHIFT_LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total + 64).getInventory());
                    }
                    break;
                case 24:
                    if (e.getClick() == ClickType.LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total - (int) SettingsManager.get(Settings.DECREMENT_FIRST)).getInventory());
                    }

                    if (e.getClick() == ClickType.SHIFT_LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total - 64).getInventory());
                    }
                    break;
                case 33:
                    if (e.getClick() == ClickType.LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total - (int) SettingsManager.get(Settings.DECREMENT_SECOND)).getInventory());
                    }

                    if (e.getClick() == ClickType.SHIFT_LEFT) {
                        p.openInventory(new PurchaseInventory(this.shop, this.shopItem, this.total - 64).getInventory());
                    }
                    break;
                case 30:
                    if (ShopAPI.get().itemCount(p, this.shopItem.getMaterial()) <= 0) {
                        return;
                    }

                    if (ShopAPI.get().isBuyOnly(this.shop.getName())) {
                        return;
                    }

                    int amount = ShopAPI.get().itemCount(p, this.shopItem.getMaterial());

                    ShopAPI.get().removeItems(p.getInventory(), shopItem.getMaterial(), amount);
                    p.updateInventory();

                    Core.getInstance().getEconomy().depositPlayer(p, shopItem.getSellPrice() * amount);

                    Core.getInstance().getLocale().getMessage(ShopLang.MONEY_ADD).processPlaceholder("amount", shopItem.getSellPrice() * amount).sendPrefixedMessage(p);
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_SOLD).processPlaceholder("total", amount).sendPrefixedMessage(p);

                    // save transaction depending on settings
                    transaction = new Transaction(
                            this.shop,
                            this.shopItem,
                            p.getUniqueId(),
                            amount,
                            Transaction.TransactionType.SOLD);

                    if ((boolean) SettingsManager.get(Settings.SEND_DISCORD_MSG_ON_TRANSACTION)) {
                        ShopAPI.get().sendDiscordMessage(p, transaction);
                    }

                    if ((boolean) SettingsManager.get(Settings.GIVE_RECEIPT_ON_PURCHASE)) {
                        ShopAPI.get().addItemToPlayerInventory(p, new Receipt(p,transaction).getReceipt());
                    }

                    if ((boolean) SettingsManager.get(Settings.SAVE_TRANSACTION_TO_FILE_RIGHT_AWAY)) {
                        ShopAPI.get().saveTransactions(transaction);
                    } else {
                        Core.getInstance().getTransactions().add(transaction);
                    }

                    p.openInventory(new PurchaseInventory(this.shop, this.shopItem, 0).getInventory());
                    break;
                case 31:

                    double discountPrice = shopItem.getBuyPrice() * this.total;

                    if (ShopAPI.get().hasDiscount(shop.getName())) {
                        double discountAmount = ShopAPI.get().getDiscount(shop.getName());
                        double discountTotal = discountPrice * (discountAmount / 100);
                        discountPrice -= discountTotal;
                    }

                    if (Core.getInstance().getEconomy().getBalance(p) >= discountPrice) {

                        ShopItemPurchaseEvent purchaseEvent = new ShopItemPurchaseEvent(p, this.shopItem);
                        Bukkit.getPluginManager().callEvent(purchaseEvent);
                        if (!purchaseEvent.isCancelled()) {

                            Core.getInstance().getEconomy().withdrawPlayer(p, discountPrice);
                            for (int i = 0; i< this.total; i++) {
                                ShopAPI.get().addItemToPlayerInventory(p, shopItem.getMaterial());
                            }
                            Core.getInstance().getLocale().getMessage(ShopLang.MONEY_REMOVE).processPlaceholder("amount", discountPrice).sendPrefixedMessage(p);
                            Core.getInstance().getLocale().getMessage(ShopLang.SHOP_BOUGHT).processPlaceholder("total", this.total).sendPrefixedMessage(p);

                            // save transaction depending on settings
                            transaction = new Transaction(
                                    this.shop,
                                    this.shopItem,
                                    p.getUniqueId(),
                                    this.total,
                                    Transaction.TransactionType.BOUGHT);

                            if ((boolean) SettingsManager.get(Settings.SEND_DISCORD_MSG_ON_TRANSACTION)) {
                                ShopAPI.get().sendDiscordMessage(p, transaction);
                            }

                            if ((boolean) SettingsManager.get(Settings.GIVE_RECEIPT_ON_PURCHASE)) {
                                ShopAPI.get().addItemToPlayerInventory(p, new Receipt(p,transaction).getReceipt());
                            }

                            if ((boolean) SettingsManager.get(Settings.SAVE_TRANSACTION_TO_FILE_RIGHT_AWAY)) {
                                ShopAPI.get().saveTransactions(transaction);
                            } else {
                                Core.getInstance().getTransactions().add(transaction);
                            }

                            p.openInventory(new PurchaseInventory(this.shop, this.shopItem, 0).getInventory());
                        }
                    }
                    break;
                case 32:
                    if (ShopAPI.get().itemCount(p, this.shopItem.getMaterial()) <= 0) {
                        return;
                    }

                    if (ShopAPI.get().isBuyOnly(this.shop.getName())) {
                        return;
                    }

                    int finalRemove = (ShopAPI.get().itemCount(p, this.shopItem.getMaterial()) < this.total) ? ShopAPI.get().itemCount(p, this.shopItem.getMaterial()) : this.total;

                    ShopAPI.get().removeItems(p.getInventory(), shopItem.getMaterial(), finalRemove);
                    p.updateInventory();

                    Core.getInstance().getEconomy().depositPlayer(p, shopItem.getSellPrice() * finalRemove);

                    Core.getInstance().getLocale().getMessage(ShopLang.MONEY_ADD).processPlaceholder("amount", shopItem.getSellPrice() * finalRemove).sendPrefixedMessage(p);
                    Core.getInstance().getLocale().getMessage(ShopLang.SHOP_SOLD).processPlaceholder("total", finalRemove).sendPrefixedMessage(p);

                    // save transaction depending on settings
                    transaction = new Transaction(
                            this.shop,
                            this.shopItem,
                            p.getUniqueId(),
                            finalRemove,
                            Transaction.TransactionType.SOLD);

                    if ((boolean) SettingsManager.get(Settings.SEND_DISCORD_MSG_ON_TRANSACTION)) {
                        ShopAPI.get().sendDiscordMessage(p, transaction);
                    }

                    if ((boolean) SettingsManager.get(Settings.GIVE_RECEIPT_ON_PURCHASE)) {
                        ShopAPI.get().addItemToPlayerInventory(p, new Receipt(p,transaction).getReceipt());
                    }

                    if ((boolean) SettingsManager.get(Settings.SAVE_TRANSACTION_TO_FILE_RIGHT_AWAY)) {
                        ShopAPI.get().saveTransactions(transaction);
                    } else {
                        Core.getInstance().getTransactions().add(transaction);
                    }

                    p.openInventory(new PurchaseInventory(this.shop, this.shopItem, 0).getInventory());
                    break;
                case 49:
                    p.closeInventory();
                    break;
                case 45:
                    p.openInventory(new ShopInventory(Core.getInstance().getShops().stream().filter(shop -> shop.getName().equalsIgnoreCase(this.shop.getName())).findFirst().get()).getInventory());
                    break;
            }
        } catch (Exception ex) {
            Debugger.report(ex, false);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.defaultSize, this.defaultTitle);

        ItemStack border = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("guis.purchase-inventory.border-item")).get().parseItem();
        BorderNumbers.FIFTY_FOUR.getSlots().forEach(slot -> inventory.setItem(slot, border));

        // actual item
        inventory.setItem(13, shopItem.getMaterial());

        //increment items
        inventory.setItem(20, ShopAPI.get().createPurchaseInventoryItem("increment", (int) SettingsManager.get(Settings.INCREMENT_FIRST), 0, 0));
        inventory.setItem(29, ShopAPI.get().createPurchaseInventoryItem("increment", (int) SettingsManager.get(Settings.INCREMENT_SECOND), 0, 0));

        //decrement items
        inventory.setItem(24, ShopAPI.get().createPurchaseInventoryItem("decrement", (int) SettingsManager.get(Settings.DECREMENT_FIRST), 0, 0));
        inventory.setItem(33, ShopAPI.get().createPurchaseInventoryItem("decrement", (int) SettingsManager.get(Settings.DECREMENT_SECOND), 0, 0));

        //sell
        inventory.setItem(30, ShopAPI.get().createPurchaseInventoryItem("sellall", 0, 0, 0));
        inventory.setItem(31, ShopAPI.get().createPurchaseInventoryItem((ShopAPI.get().isBuyOnly(shop.getName())) ? "iteminfobuyonly" : "iteminfo", this.total, shopItem.getSellPrice() * this.total, shopItem.getBuyPrice() * this.total));
        inventory.setItem(32, ShopAPI.get().createPurchaseInventoryItem("selltotal", this.total, 0, 0));

        // back item
        inventory.setItem(45, ShopAPI.get().createPurchaseInventoryItem("back-item", 0, 0, 0));
        // close item
        inventory.setItem(49, ShopAPI.get().createPurchaseInventoryItem("close-item", 0, 0, 0));
        return inventory;
    }
}
