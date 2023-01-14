package com.serenebond.tile;

import com.google.gson.Gson;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public final class Tiles {
    // TODO:: Read from Json.
    public static final Int2ObjectMap<Tile> COLOUR_TO_TILE;

    public static final Tile DIRT = new Tile();
    public static final Tile GRASS = new Tile();
    public static final Tile SAND = new Tile();
    public static final Tile STONE = new Tile();
    public static final Tile WATER = new Tile();

    static {
        var map = new Int2ObjectOpenHashMap<Tile>();
        map.put(0x68422A, DIRT);
        map.put(0x84BA2C, GRASS);
        map.put(0xF2CF79, SAND);
        map.put(0x908583, STONE);
        map.put(0x0094FF, WATER);

        COLOUR_TO_TILE = Int2ObjectMaps.unmodifiable(map);
    }

    private Tiles() {}

    public static void register(Gson gson) {
    }
}
