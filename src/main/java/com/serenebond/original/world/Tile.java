package com.serenebond.original.world;

import com.serenebond.original.tiles.TileType;

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
