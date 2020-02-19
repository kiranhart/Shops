package com.kiranhart.shops.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/10/2020
 * Time Created: 6:48 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopRemoveEvent extends Event implements Cancellable {

    private CommandSender sender;
    private String shopName;
    private boolean isCancelled;

    public ShopRemoveEvent(CommandSender sender, String shopName) {
        this.sender = sender;
        this.shopName = shopName;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getShopName() {
        return shopName;
    }

    public CommandSender getCommandSender() {
        return sender;
    }
}
