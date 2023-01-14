package com.serenebond;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.Objects.requireNonNull;

public final class Resources {

    private Resources() {}

    public static InputStream get(String path) throws IOException {
        return requireNonNull(Resources.class.getClassLoader().getResource(path)).openStream();
    }

    public static String glsl(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(get(path)))) {
            var builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            return builder.toString();
        }
    }
}
