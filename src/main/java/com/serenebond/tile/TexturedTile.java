package com.serenebond.tile;

import com.serenebond.main.Game;
import com.serenebond.world.Camera;
import com.serenebond.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public sealed abstract class TexturedTile implements Tile permits FloorTile, WallTile {
    private final BufferedImage image;

    public TexturedTile(int u, int v){
        image = Game.spritesheet.getSprite(u, v, 16, 16);
    }

    @Override
    public void draw(Graphics graphics, int x, int y, World world) {
        graphics.drawImage(image, x * 16 - Camera.x, y * 16 - Camera.y, null);
    }
}
