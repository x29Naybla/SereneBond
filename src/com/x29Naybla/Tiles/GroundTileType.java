package com.x29Naybla.Tiles;

import com.x29Naybla.World.Camera;
import com.x29Naybla.World.Tile;
import com.x29Naybla.World.World;

import java.awt.*;

public class GroundTileType extends SimpleTileType {
    public GroundTileType(int u, int v) {
        super(u, v);
    }

    public void render(Graphics g, Tile tile, World world) {
        super.render(g, tile, world);

        // Draw the blend texture of neighbours
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x != 0 || y != 0) && (tile.x + x) > 0 && (tile.x + x) < world.tiles.length - 1 && (tile.y + y) > 0 && (tile.y + y) < world.tiles[0].length - 1) {
                    Tile n = world.tiles[tile.x + x][tile.y + y];
                    if (n.type != this && n.type instanceof BlendingTileType) {
                        BlendingTileType neighbour = (BlendingTileType) n.type;
                        // I inverted the texture coordinate here so that you can add the textures as they appear on the atlas
                        g.drawImage(neighbour.images[1 - x][1 - y], tile.x * 16 - Camera.x, tile.y * 16 - Camera.y, null);
                    }
                }
            }
        }
    }
}
