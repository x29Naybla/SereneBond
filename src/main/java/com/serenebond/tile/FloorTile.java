package com.serenebond.tile;

import com.serenebond.main.Game;
import com.serenebond.world.Camera;
import com.serenebond.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class FloorTile extends TexturedTile {
    private final BufferedImage[][] images = new BufferedImage[3][3];
    private final int order;

    public FloorTile(int u, int v, int uOffset, int vOffset, int order) {
        super(u, v);
        this.order = order;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                images[x][y] = Game.spritesheet.getSprite(uOffset + x * 16, vOffset + y * 16, 16, 16);
            }
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, World world) {
        super.draw(graphics, x, y, world);

        for (int z = -1; z <= 1; z++) {
            for (int w = -1; w <= 1; w++) {

                if ((x != 0 || y != 0) && (x + z) > 0 && (x + z) < World.tiles.length - 1 && (y + w) > 0 && (y + w) < World.tiles[0].length - 1) {
                    var tile = World.tiles[x + z][y + w];

                    if (tile != this && tile instanceof FloorTile floor && floor.order > order) {
                        graphics.drawImage(floor.images[1 - z][1 - w], x * 16 - Camera.x, y * 16 - Camera.y, null);
                    }
                }
            }
        }
    }

    @Override
    public boolean collides() {
        return false;
    }
}
