package com.serenebond.original.tiles;

import com.serenebond.original.main.Game;
import com.serenebond.original.world.Camera;
import com.serenebond.original.world.Tile;
import com.serenebond.original.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimpleTileType extends TileType {
    private final BufferedImage image;

    public SimpleTileType(int u, int v){

        this.image = Game.spritesheet.getSprite(u, v, 16, 16);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void render(Graphics g, Tile tile, World world) {
        g.drawImage(getImage(), tile.x * 16 - Camera.x, tile.y * 16 - Camera.y, null);
    }
}
