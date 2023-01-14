package com.serenebond.original.tiles;

import com.serenebond.original.world.Tile;
import com.serenebond.original.world.World;

import java.awt.*;

abstract public class TileType {
    abstract public void render(Graphics g, Tile tile, World world);

    public boolean hasCollision(){
        return false;
    }
}