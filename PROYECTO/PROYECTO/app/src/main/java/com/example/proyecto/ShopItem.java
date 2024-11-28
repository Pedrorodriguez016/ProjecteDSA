package com.example.proyecto;

public class ShopItem {
    private String name;
    private double price;
    private int imageResourceId;

    public ShopItem(String name, double price, int imageResourceId) {

        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;

    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getImageResourceId() { return imageResourceId; }
}