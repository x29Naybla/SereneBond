package com.serenebond.graphics;

import com.serenebond.Resources;
import com.serenebond.tile.Tile;
import com.serenebond.world.World;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.memCalloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public final class WorldGraphics {
    private final int vao = glCreateVertexArrays();
    private final int vbo;
    private final int ebo;

    private final int pipeline = glCreateProgramPipelines();
    private final int fragment;
    private final int vertex;

    private final int projectionLocation;
    private final int modelViewLocation;


    // Element count.
    private int count;

    {
        int vbo;
        int ebo;
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            var buffer = memoryStack.callocInt(2);

            glCreateBuffers(buffer);

            vbo = buffer.get(0);
            ebo = buffer.get(1);
        }

        this.vbo = vbo;
        this.ebo = ebo;

        glVertexArrayVertexBuffer(vao, 0, vbo, 0, 16);
        glVertexArrayElementBuffer(vao, ebo);

        glVertexArrayAttribBinding(vao, 0, 0);

        // Program pipeline.
        var fragment = 0;
        var vertex = 0;
        try {
            fragment = glCreateShaderProgramv(GL_FRAGMENT_SHADER, Resources.glsl("resource/shader/world_fragment.glsl"));
            vertex = glCreateShaderProgramv(GL_VERTEX_SHADER, Resources.glsl("resource/shader/world_vertex.glsl"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        projectionLocation = glGetUniformLocation(vertex, "projection");
        modelViewLocation = glGetUniformLocation(vertex, "model_view");

        glUseProgramStages(pipeline, GL_FRAGMENT_SHADER_BIT, this.fragment = fragment);
        glUseProgramStages(pipeline, GL_VERTEX_SHADER_BIT, this.vertex = vertex);

        glValidateProgramPipeline(pipeline);
    }

    public void draw(World world, Matrix4f projection, Matrix4fStack modelView) {
        batch(world.tiles);

        glBindProgramPipeline(pipeline);

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            var buffer = memoryStack.callocFloat(16);

            glProgramUniformMatrix4fv(vertex, projectionLocation, false, projection.get(buffer));
            glProgramUniformMatrix4fv(vertex, modelViewLocation, false, modelView.get(buffer));
        }

        glBindVertexArray(vao);

        glEnableVertexArrayAttrib(vao, 0);

        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);

        glBindVertexArray(0);

        glBindProgramPipeline(0);
    }

    public void batch(Tile[][] tiles) {
        var size = tiles.length * tiles[0].length;

        var buffer = memCalloc(22 * size, 4);

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                buffer.putFloat(0.0F + x).putFloat(0.0F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(1.0F + x).putFloat(0.0F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(1.0F + x).putFloat(1.0F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(0.0F + x).putFloat(1.0F + y).putFloat(0.0F).putFloat(1.0F);

            }
        }

        for (int i = 0; i < size; i++) {
            var offset = 4 * i;

            buffer
                    .putInt(offset).putInt(offset + 1).putInt(offset + 2)
                    .putInt(offset + 2).putInt(offset + 3).putInt(offset);
        }

        buffer.flip();

        var vertices = 16 * 4 * size;
        var elements = 6 * 4 * size;

        count = elements / 4;

        buffer.limit(vertices);
        glNamedBufferData(vbo, buffer, GL_DYNAMIC_DRAW);
        buffer.position(vertices);

        buffer.limit(vertices + elements);
        glNamedBufferData(ebo, buffer, GL_DYNAMIC_DRAW);
        buffer.position(0);

        memFree(buffer);
    }
}
