package com.serenebond.tile;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public final class Tiles {
    public static final Tile DIRT = null;
    public static final Tile GRASS = null;
    public static final Tile SAND = null;
    public static final Tile STONE = null;
    public static final Tile WATER = null;

    private Tiles() {}

    private static final class Codec implements JsonDeserializer<Tile> {

        private Codec() {}

        @Override
        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }
}
