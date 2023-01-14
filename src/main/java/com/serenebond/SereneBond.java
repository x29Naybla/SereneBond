package com.serenebond;

import com.serenebond.entity.Entity;
import com.serenebond.entity.Player;
import com.serenebond.graphics.EntityGraphics;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;

public final class SereneBond {
    private final Window window = new Window();

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final Matrix4f projection = new Matrix4f();
    private final Matrix4fStack modelView = new Matrix4fStack(2);

    // Graphics.
    private final EntityGraphics entityGraphics = new EntityGraphics();

    private final List<Entity> entities = new ArrayList<>();

    private final Player player = new Player();

    private final Settings settings;

    private State state = State.IN_GAME;

    SereneBond(Settings settings) {
        this.settings = settings;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> running.set(false)));

        entities.add(player);
    }

    public void step() {
        while (running.getAndSet(!window.shouldClose())) {
            entities.forEach(Entity::step);

            player.step(settings.keysBinds);

            draw();
            window.swapBuffers();
        }
    }

    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT);

        var aspectRatio = window.getAspectRatio();
        var fieldOfView = 0.785398F;

        projection.identity();
        projection.ortho(-fieldOfView * aspectRatio, fieldOfView * aspectRatio, -fieldOfView, fieldOfView, 1.0F, -1.0F);

        switch (state) {
            case IN_GAME -> {
                entityGraphics.draw(entities, projection, modelView);
            }

            default -> {

            }
        }
    }

    public void close() {
        window.destroy();
    }
}
