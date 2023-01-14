package com.serenebond.tile;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Tile {

    Tile() {}

    public static final class Codec implements JsonDeserializer<Tile> {

        @Override
        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Tile();
        }
    }
}
