package com.kiranhart.shops.shop;

import com.cryptomorin.xseries.XMaterial;
import com.kiranhart.shops.Core;
import com.kiranhart.shops.util.helpers.NBTEditor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/27/2020
 * Time Created: 4:55 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Receipt {

    private Player p;
    private Transaction transaction;

    public Receipt(Player p, Transaction transaction) {
        this.p = p;
        this.transaction = transaction;
    }

    public ItemStack getReceipt() {
        ItemStack stack = XMaterial.matchXMaterial(Core.getInstance().getConfig().getString("receipt.item")).orElse(XMaterial.NETHER_STAR).parseItem();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("receipt.name").replace("%player%", p.getName())));
        List<String> lore = new ArrayList<>();
        Core.getInstance().getConfig().getStringList("receipt.lore").forEach(line -> {
            lore.add(ChatColor.translateAlternateColorCodes('&', line
            .replace("%player%", p.getName())
            .replace("%item%", StringUtils.capitalize(transaction.getShopItem().getMaterial().getType().name().replace("_", " ").toLowerCase()))
            .replace("%type%", transaction.getTransactionType().getType())
            .replace("%amount%", String.valueOf(transaction.getQuantity()))
            .replace("%finalprice%", String.valueOf((transaction.getTransactionType() == Transaction.TransactionType.BOUGHT) ? transaction.getQuantity() * transaction.getShopItem().getBuyPrice() : transaction.getQuantity() * transaction.getShopItem().getSellPrice()))));
        });
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.set(stack, transaction.getId().toString() + transaction.getCurrentMillis(),"ShopTransactionID");
        return stack;
    }
}
