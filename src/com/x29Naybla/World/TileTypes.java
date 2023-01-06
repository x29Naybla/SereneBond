package com.x29Naybla.World;

import com.x29Naybla.Tiles.*;

import java.util.HashMap;
import java.util.Map;

public class TileTypes {
    public static Map<Integer, TileType> tiles = new HashMap<>();

    public static TileType Tile_F_Grass = register(new BlendingTileType(16, 0, 80, 48, 2), 0xFF84BA2C);
    public static TileType Tile_F_Dirt = register(new BlendingTileType(0, 0, 80, 0,3), 0xFF68422A);
    public static TileType Tile_F_Plant = register(new BlendingTileType(32, 0, 80, 48, 2),0xFF96C42C);
    public static TileType Tile_F_Sand = register(new BlendingTileType(48, 0, 128, 48, 1), 0xFFF2CF79);
    public static TileType Tile_F_Stone = register(new BlendingTileType(64, 0, 128, 0,4), 0xFF908583);
    public static TileType Tile_F_RedFlower = register(new BlendingTileType(0, 16, 80, 48, 2), 0xFFE0311A);
    public static TileType Tile_F_OrangeFlower = register(new BlendingTileType(16, 16, 80, 48, 2), 0xFFEC9914);
    public static TileType Tile_F_YellowFlower = register(new BlendingTileType(32, 16, 80, 48, 2), 0xFFECE814);
    public static TileType Tile_F_LightBlueFlower = register(new BlendingTileType(48, 16, 80, 48, 2), 0xFF14ECE5);
    public static TileType Tile_F_PurpleFlower  = register(new BlendingTileType(0, 32, 80, 48, 2), 0xFFDB07F3);
    public static TileType Tile_F_DarkBlueFlower = register(new BlendingTileType(16, 32, 80, 48, 2), 0xFF0575FF);
    public static TileType Tile_F_WhiteFlower = register(new BlendingTileType(32, 32, 80, 48, 2), 0xFFF0E1F2);
    public static TileType Tile_F_PinkFlower = register(new BlendingTileType(48, 32, 80, 48, 2), 0xFFEF8FBC);

    public static TileType Tile_W_Magma = register(new WallTileType(64,48),0xFFFFBE1C);
    public static TileType Tile_W_Lava = register(new WallTileType(64,64),0xFFED2D18);


    private static TileType register(TileType tile, int rgba) {
        tiles.put(rgba, tile);
        return tile;
    }
}
