package com.serenebond.world;

import com.serenebond.Resources;
import com.serenebond.main.Game;
import com.serenebond.tile.Tile;
import com.serenebond.tile.Tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    public static Tile[][] tiles;
    public static int Width,Height;
    public static int ActW, ActH;
    public int spawnX,spawnY;

    public World(String path){
        try {
            BufferedImage world = ImageIO.read(Resources.getResource(path));
            int[] pixels = new int[world.getWidth() * world.getHeight()];
            Width = world.getWidth();
            Height = world.getHeight();
            tiles = new Tile[world.getWidth()][world.getHeight()];
            world.getRGB(0, 0, world.getWidth(), world.getHeight(), pixels, 0, world.getWidth());
            for(int xx = 0; xx < world.getWidth();xx++){
                for(int yy = 0; yy < world.getHeight();yy++){
                    int curPixel = pixels[xx + yy * world.getWidth()];

                    //Use our tile type registry to look up the color
                    tiles[xx][yy] = Tiles.getTile(curPixel);

                    if(curPixel == 0xFF000000){
                        //player
                        Game.player.setX(xx*16);
                        Game.player.setY(yy*16);

                        spawnX = xx*16;
                        spawnY = yy*16;
                    }

                    ActW = (world.getWidth()*16) -18;
                    ActH = (Height*yy) / 16;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
    public static boolean isFree(int xnext, int ynext){
        int x1 = xnext / 16;
        int y1 = ynext / 16;

        int x2 = (xnext+15) / 16;
        int y2 = ynext / 16;

        int x3 = xnext / 16;
        int y3 = (ynext+15) / 16;

        int x4 = (xnext+15) / 16;
        int y4 = (ynext+15) / 16;

        return !(tiles[x1][y1].collides() ||
                tiles[x2][y2].collides() ||
                tiles[x3][y3].collides() ||
                tiles[x4][y4].collides());
    }

    public void render(Graphics g){
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;

        int xfinal = xstart + (Game.Width >> 4);
        int yfinal = ystart + (Game.Height >> 4);

        for(int xx = xstart; xx <= xfinal; xx++){
            for(int yy = ystart; yy <= yfinal; yy++){
                if(xx < 0 || yy < 0 || xx >= Width || yy >= Height)
                    continue;
                Tile tile = tiles[xx][yy];
                tile.draw(g, xx, yy, this);
            }
        }
    }
}
