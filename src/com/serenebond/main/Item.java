package com.serenebond.main;

import java.awt.image.BufferedImage;

public class Item {
    private String name;
    private String description;
    private int quantity;
    private String ID;
    public BufferedImage sprite;

    public Item(String name, String description, String ID, BufferedImage sprite) {
        this.name = name;
        this.description = description;
        this.quantity = 1;
        this.ID= ID;
        this.sprite = sprite;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getID() {
        return ID;
    }
    public BufferedImage getSprite(){
        return sprite;
    }
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
    public void addQuantity(int add) {
        this.quantity += add;
    }

    public static Item stone = new Item("Stone", "Rock hard", "stone", Game.spritesheet.getSprite(16,176,16,16));
    public static Item sand = new Item("Sand", "It's everywhere", "sand", Game.spritesheet.getSprite(16,192,16,16));
    public static Item dirt = new Item("Dirt", "Dirty stuff", "dirt", Game.spritesheet.getSprite(0,0,16,16));
    public static Item grass = new Item("Grass", "Don't walk on it", "grass", Game.spritesheet.getSprite(0,48,16,16));
    public static Item snow = new Item("Snow", "You can't feel your fingers", "snow", Game.spritesheet.getSprite(0,16,16,16));
    public static Item water = new Item("Water", "Perfect for a hot day", "water", Game.spritesheet.getSprite(48,192,16,16));
}

