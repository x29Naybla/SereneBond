package com.x29Naybla.Tiles;

import com.x29Naybla.Main.Game;
import com.x29Naybla.World.Camera;
import com.x29Naybla.World.Tile;
import com.x29Naybla.World.World;

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
