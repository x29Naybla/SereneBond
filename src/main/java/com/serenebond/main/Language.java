package com.serenebond.main;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class Language {
    public static final Language EMPTY = new Language(Map.of());

    public static final String[] OPTIONS = {
            "0", "1", "2", "3"
    };

    public static final String[] PAUSE = {
            "0", "1"
    };

    private final Map<String, String> map;

    private Language(Map<String, String> map) {
        this.map = map;
    }

    public String get(String unlocalized) {
        return map.getOrDefault(unlocalized, "missing_localization");
    }

    public String getFormatted(String unlocalized, Object... objects) {
        return get(unlocalized).formatted(objects);
    }

    public static final class Codec implements JsonDeserializer<Language> {

        @Override
        public Language deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var map = json.getAsJsonObject().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getAsString()));

            return new Language(Collections.unmodifiableMap(map));
        }
    }
}
