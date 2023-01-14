package com.serenebond.world;

import com.serenebond.Resources;
import com.serenebond.original.entities.Entity;
import com.serenebond.persistent.WritablePersistent;
import org.lwjgl.system.MemoryStack;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.stb.STBImage.nstbi_image_free;
import static org.lwjgl.stb.STBImage.nstbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;

public final class World implements WritablePersistent {
    private final List<Entity> entities = new ArrayList<>();

    public final int width;
    public final int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
    }

    public static World from(String image) throws IOException {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            var buffer = memoryStack.callocInt(3);

            var memory = Resources.image(image).flip();
            var pixels = nstbi_load_from_memory(memAddress(memory), memory.remaining(), memAddress(buffer), memAddress(buffer) + 4, memAddress(buffer) + 8, 4);

            var width = buffer.get(0);
            var height = buffer.get(1);

            memFree(memory);
            nstbi_image_free(pixels);

            return new World(width, height);
        }
    }
}
