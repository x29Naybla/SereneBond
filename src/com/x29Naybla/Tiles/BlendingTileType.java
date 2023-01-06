package com.x29Naybla.Tiles;

import com.x29Naybla.Main.Game;

import java.awt.image.BufferedImage;

public class BlendingTileType extends GroundTileType {
    public final BufferedImage[][] images = new BufferedImage[3][3];

    public BlendingTileType(int u, int v, int blendU, int blendV, int order) {
        super(u, v);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                images[x][y] = Game.spritesheet.getSprite(blendU + x * 16, blendV + y * 16, 16, 16);
            }
        }
    }
}
