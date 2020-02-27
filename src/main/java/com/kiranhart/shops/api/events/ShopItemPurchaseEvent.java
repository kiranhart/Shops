package com.kiranhart.shops.api.events;
/*
    Created by Kiran Hart
    Date: February / 19 / 2020
    Time: 11:04 a.m.
*/

import com.kiranhart.shops.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopItemPurchaseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean isCancelled;
    private Player player;
    private ShopItem item;

    public ShopItemPurchaseEvent(Player player, ShopItem item) {
        this.player = player;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public ShopItem getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
