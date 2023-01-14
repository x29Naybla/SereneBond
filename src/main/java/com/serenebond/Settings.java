package com.serenebond;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public final class Settings {
    public final KeysBinds keysBinds;

    private Settings(KeysBinds keysBinds) {
        this.keysBinds = keysBinds;
    }

    public static Settings of(Path directory, Gson gson) {
        var path = directory.resolve("settings.json");

        if (Files.isRegularFile(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                return gson.fromJson(reader, Settings.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            var settings = new Settings(new KeysBinds(Map.of(
                    "walk_up",
                    "w",
                    "walk_left",
                    "a",
                    "walk_down",
                    "s",
                    "walk_right",
                    "d"
            )));

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                gson.toJson(settings, writer);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return settings;
        }

        return null;
    }

    public static final class Codec implements JsonDeserializer<Settings>, JsonSerializer<Settings> {

        @Override
        public Settings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var map = json.getAsJsonObject().getAsJsonObject("key_binds").entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getAsString()));

            return new Settings(new KeysBinds(map));
        }

        @Override
        public JsonElement serialize(Settings src, Type typeOfSrc, JsonSerializationContext context) {
            var root = new JsonObject();

            var keyBinds = new JsonObject();
            src.keysBinds.getMap().entrySet().forEach(entry -> keyBinds.addProperty(entry.getKey(), entry.getValue()));

            root.add("key_binds", keyBinds);

            return root;
        }
    }
}
