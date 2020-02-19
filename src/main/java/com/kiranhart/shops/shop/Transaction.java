package com.kiranhart.shops.shop;
/*
    Created by Kiran Hart
    Date: February / 19 / 2020
    Time: 10:42 a.m.
*/

import java.util.UUID;

public class Transaction {

    public enum TransactionType {

        SOLD("Sold"), BOUGHT("Bought");

        private String type;

        TransactionType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private UUID id;

    private Shop shop;
    private ShopItem shopItem;
    private UUID buyer;
    private int quantity;
    private TransactionType transactionType;
    private long currentMillis;

    public Transaction(Shop shop, ShopItem shopItem, UUID buyer, int quantity, TransactionType transactionType) {
        this.shop = shop;
        this.shopItem = shopItem;
        this.buyer = buyer;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.id = UUID.randomUUID();
        this.currentMillis = System.currentTimeMillis();
    }

    public long getCurrentMillis() {
        return currentMillis;
    }

    public UUID getId() {
        return id;
    }

    public Shop getShop() {
        return this.shop;
    }

    public ShopItem getShopItem() {
        return this.shopItem;
    }

    public UUID getBuyer() {
        return this.buyer;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }
}