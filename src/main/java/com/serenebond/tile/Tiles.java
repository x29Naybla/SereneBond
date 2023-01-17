package com.serenebond.tile;

import com.serenebond.main.Item;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;

public final class Tiles {
    private static final Int2ObjectMap<Tile> COLOUR_TO_TILE;

    public static final Tile GRASS = new FloorTile(0, 48, 64, 0, 3);
    public static final Tile DIRT = new FloorTile(0, 0, 64, 48, 1);
    public static final Tile SAND = new FloorTile(16, 192, 112, 0, 2);
    public static final Tile STONE = new FloorTile(16, 176, 64, 96, 0);
    public static final Tile SNOW = new FloorTile(0, 16, 112, 48,4);
    public static final Tile COBBLESTONE = new FloorTile(37, 144, 112, 96, 0);

    // Foliage.
    public static final Tile PLANT = new FloorTile(0, 32, 64, 0, 3);
    public static final Tile RED_FLOWER = new FloorTile(0, 64, 64, 0, 3);
    public static final Tile ORANGE_FLOWER = new FloorTile(0, 80, 64, 0, 3);
    public static final Tile YELLOW_FLOWER = new FloorTile(0, 96, 64, 0, 3);
    public static final Tile LIGHT_BLUE_FLOWER = new FloorTile(0, 112, 64, 0, 3);
    public static final Tile DARK_BLUE_FLOWER = new FloorTile(0, 128, 64, 0, 3);
    public static final Tile PURPLE_FLOWER = new FloorTile(0, 144, 64, 0, 3);
    public static final Tile PINK_FLOWER = new FloorTile(0, 160, 64, 0, 3);
    public static final Tile WHITE_FLOWER = new FloorTile(0, 176, 64, 0, 3);

    // Can't walk on.
    public static final Tile MAGMA = new WallTile(32, 176);
    public static final Tile LAVA = new WallTile(48, 176);
    public static final Tile WATER = new WallTile(48, 192);

    static {
        // Colours are Big Endian.
        Int2ObjectOpenHashMap<Tile> map = new Int2ObjectOpenHashMap<>();
        map.put(0xFF84BA2C, GRASS);
        map.put(0xFF68422A, DIRT);
        map.put(0xFFF2CF79, SAND);
        map.put(0xFF908583, STONE);
        map.put(0xFFCAD4DB, SNOW);
        map.put(0xFF6A5A5A, COBBLESTONE);

        map.put(0xFF96C42C, PLANT);
        map.put(0xFFE0311A, RED_FLOWER);
        map.put(0xFFEC9914, ORANGE_FLOWER);
        map.put(0xFFECE814, YELLOW_FLOWER);
        map.put(0xFF14ECE5, LIGHT_BLUE_FLOWER);
        map.put(0xFF0575FF, DARK_BLUE_FLOWER);
        map.put(0xFFDB07F3, PURPLE_FLOWER);
        map.put(0xFFEF8FBC, PINK_FLOWER);
        map.put(0xFFF0E1F2, WHITE_FLOWER);

        map.put(0xFFFFBE1C, MAGMA);
        map.put(0xFFED2D18, LAVA);
        map.put(0xFF0094FF, WATER);

        COLOUR_TO_TILE = Int2ObjectMaps.unmodifiable(map);
    }

    private Tiles() {}

    public static Tile getTile(int colour) {
        return COLOUR_TO_TILE.getOrDefault(colour, GRASS);
    }

    // TODO:: Redo.
    public static Map<String, Tile> itemsTileType = new HashMap<>();
    static {
        itemsTileType.put(Item.stone.getID(), STONE);
        itemsTileType.put(Item.sand.getID(), SAND);
        itemsTileType.put(Item.dirt.getID(), DIRT);
        itemsTileType.put(Item.grass.getID(), GRASS);
        itemsTileType.put(Item.snow.getID(), SNOW);
        itemsTileType.put(Item.water.getID(), WATER);
    }
}
