package com.serenebond;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class Resources {

    private Resources() {}

    public static InputStream get(String path) throws IOException {
        return requireNonNull(Resources.class.getClassLoader().getResource(path)).openStream();
    }

    public static Stream<URL> getAll(String directory) {
        return Resources.class.getClassLoader().resources(directory);
    }
}
