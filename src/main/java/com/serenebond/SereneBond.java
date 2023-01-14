package com.serenebond;

import com.serenebond.entity.Entity;
import com.serenebond.entity.Player;
import com.serenebond.graphics.EntityFx;
import org.joml.Matrix4d;
import org.joml.Matrix4dStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.opengl.GL11C.*;

public final class SereneBond {
    private final Window window = new Window();

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final Matrix4d projection = new Matrix4d();
    private final Matrix4dStack modelView = new Matrix4dStack(2);

    // FX
    private final EntityFx entityFx = new EntityFx();

    private final List<Entity> entities = new ArrayList<>();

    private State state = State.IN_GAME;

    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> running.set(false)));

        entities.add(new Player());
    }

    public void step() {

        glClearColor(1.0F, 0.0F, 0.0F, 1.0F);

        while (running.getAndSet(!window.shouldClose())) {
            draw();
            window.swapBuffers();
        }
    }

    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT);

        switch (state) {
            case IN_GAME -> {
                entityFx.draw(entities, projection, modelView);
            }

            default -> {

            }
        }
    }

    public void close() {
        window.destroy();
    }
}
