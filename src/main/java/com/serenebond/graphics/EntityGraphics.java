package com.serenebond.graphics;

import com.serenebond.Resources;
import com.serenebond.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.memCalloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public final class EntityGraphics {
    private final int vao = glCreateVertexArrays();

    private final int pipeline = glCreateProgramPipelines();
    private final int fragment;
    private final int vertex;

    private final int projectionLocation;
    private final int modelViewLocation;

    {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            int vbo;
            int ebo;

            {
                var buffer = memoryStack.callocInt(2);

                glCreateBuffers(buffer);

                vbo = buffer.get(0);
                ebo = buffer.get(1);
            }

            {
                var buffer = memCalloc(22, 4);
                buffer.putFloat(-0.5F).putFloat(-0.5F).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(0.5F).putFloat(-0.5F).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(0.5F).putFloat(0.5F).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(-0.5F).putFloat(0.5F).putFloat(0.0F).putFloat(1.0F);

                buffer
                        .putInt(0).putInt(1).putInt(2)
                        .putInt(2).putInt(3).putInt(0);

                buffer.flip();

                var vertices = 16 * 4;
                var elements = 6 * 4;

                buffer.limit(vertices);
                glNamedBufferStorage(vbo, buffer, 0);
                buffer.position(vertices);

                buffer.limit(vertices + elements);
                glNamedBufferStorage(ebo, buffer, 0);
                buffer.position(0);

                memFree(buffer);
            }

            glVertexArrayVertexBuffer(vao, 0, vbo, 0, 16);
            glVertexArrayElementBuffer(vao, ebo);

            glVertexArrayAttribBinding(vao, 0, 0);
        }

        var fragment = 0;
        var vertex = 0;
        try {
            fragment = glCreateShaderProgramv(GL_FRAGMENT_SHADER, Resources.glsl("resources/shader/entity_fragment.glsl"));
            vertex = glCreateShaderProgramv(GL_VERTEX_SHADER, Resources.glsl("resources/shader/entity_vertex.glsl"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        projectionLocation = glGetUniformLocation(vertex, "projection");
        modelViewLocation = glGetUniformLocation(vertex, "model_view");

        glUseProgramStages(pipeline, GL_FRAGMENT_SHADER_BIT, this.fragment = fragment);
        glUseProgramStages(pipeline, GL_VERTEX_SHADER_BIT, this.vertex = vertex);

        glValidateProgramPipeline(pipeline);
    }

    public void draw(List<Entity> list, Matrix4f projection, Matrix4fStack modelView) {
        glBindProgramPipeline(pipeline);

        // TODO:: Switch to instancing.
        list.forEach(entity -> {
            modelView.pushMatrix();
            modelView.identity();

            var position = entity.position;
            modelView.translate(position.x, position.y, 0.0F);

            try (MemoryStack memoryStack = MemoryStack.stackPush()) {
                var buffer = memoryStack.callocFloat(16);

                glProgramUniformMatrix4fv(vertex, projectionLocation, false, projection.get(buffer));
                glProgramUniformMatrix4fv(vertex, modelViewLocation, false, modelView.get(buffer));
            }

            glBindVertexArray(vao);

            glEnableVertexArrayAttrib(vao, 0);

            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            glDisableVertexArrayAttrib(vao, 0);

            glBindVertexArray(0);

            modelView.popMatrix();
        });

        glBindProgramPipeline(0);
    }
}
