package com.kiranhart.shops.events;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 9:37 a.m.
*/

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopCreateEvent extends Event implements Cancellable {

    private CommandSender sender;
    private String shopName;
    private boolean isCancelled;

    public ShopCreateEvent(CommandSender sender, String shopName) {
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
