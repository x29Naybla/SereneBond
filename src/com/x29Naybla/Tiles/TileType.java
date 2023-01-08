package com.x29Naybla.Tiles;

import com.x29Naybla.World.Tile;
import com.x29Naybla.World.World;

import java.awt.*;

abstract public class TileType {
    abstract public void render(Graphics g, Tile tile, World world);

    public boolean hasCollision(){
        return false;
    }
}