package com.serenebond.entities;

import com.serenebond.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void tick(){

    }
    public void render(Graphics g){

        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
