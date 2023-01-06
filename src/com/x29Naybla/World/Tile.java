package com.x29Naybla.World;

import com.x29Naybla.Tiles.TileType;

import java.awt.*;

public class Tile {
    public final TileType type;
    public final int x, y;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void render(Graphics g, World world) {
        type.render(g, this, world);
    }
}
