package com.serenebond;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static java.util.Objects.requireNonNull;
import static org.lwjgl.system.MemoryUtil.memCalloc;
import static org.lwjgl.system.MemoryUtil.memRealloc;

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

    public static ByteBuffer image(String path) throws IOException {
        try (ReadableByteChannel channel = Channels.newChannel(get(path))) {
            var buffer = memCalloc(8192);

            while (channel.read(buffer) != -1) {
                if (buffer.remaining() == 0) {
                    buffer = memRealloc(buffer, buffer.capacity() * 2);
                }
            }

            return buffer;
        }
    }
}
