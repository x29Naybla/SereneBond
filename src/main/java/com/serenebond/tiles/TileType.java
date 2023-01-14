package com.serenebond.tiles;

import com.serenebond.world.Tile;
import com.serenebond.world.World;

import java.awt.*;

abstract public class TileType {
    abstract public void render(Graphics g, Tile tile, World world);

    public boolean hasCollision(){
        return false;
    }
}