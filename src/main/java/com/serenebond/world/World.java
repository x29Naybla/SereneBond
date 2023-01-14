package com.serenebond.world;

import com.serenebond.Resources;
import com.serenebond.Settings;
import com.serenebond.entity.Entity;
import com.serenebond.entity.Player;
import com.serenebond.persistent.WritablePersistent;
import com.serenebond.tile.Tile;
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
    public final List<Entity> entities = new ArrayList<>();

    private final Player player = new Player();

    public final int width;
    public final int height;

    public final Tile[][] tiles;

    public World(int width, int height, Tile[][] tiles) {
        this.width = width;
        this.height = height;

        this.tiles = tiles;

        entities.add(player);

        // var position = player.position;
        // position.x = width / 2.0F;
        // position.y = height / 2.0F;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
    }

    public void step(Settings settings) {
        player.step(settings.keysBinds);

        entities.forEach(Entity::step);
    }

    public static World from(String image) throws IOException {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            var buffer = memoryStack.callocInt(3);

            var memory = Resources.image(image).flip();
            var pixels = nstbi_load_from_memory(memAddress(memory), memory.remaining(), memAddress(buffer), memAddress(buffer) + 4, memAddress(buffer) + 8, 4);

            var width = buffer.get(0);
            var height = buffer.get(1);

            var tiles = new Tile[width][height];

            memFree(memory);
            nstbi_image_free(pixels);

            return new World(width, height, tiles);
        }
    }
}
