package com.serenebond;

import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public final class KeysBinds {
    static final Int2BooleanMap MAP = new Int2BooleanOpenHashMap();

    private final Map<String, String> map;

    KeysBinds(Map<String, String> map) {
        this.map = map;
    }

    public boolean held(String unlocalized) {
        return MAP.getOrDefault(map.get(unlocalized).toUpperCase(Locale.ROOT).charAt(0), false);
    }

    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(map);
    }
}
