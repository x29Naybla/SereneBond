package com.serenebond.tile;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class Tiles {
    private static final Map<String, Tile> MAP = new HashMap<>();

    private static final Tile MISSING_ENTRY = new Tile();

    public static final Supplier<Tile> DIRT = of("dirt");
    public static final Supplier<Tile> GRASS = of("grass");
    public static final Supplier<Tile> SAND = of("sand");
    public static final Supplier<Tile> STONE = of("stone");
    public static final Supplier<Tile> WATER = of("water");

    private Tiles() {}

    public static void register(Gson gson) {
    }

    private static Supplier<Tile> of(String tile) {
        return () -> MAP.getOrDefault(tile, MISSING_ENTRY);
    }
}
