package com.serenebond;

import com.serenebond.graphics.EntityGraphics;
import com.serenebond.graphics.WorldGraphics;
import com.serenebond.world.World;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL46.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL46.glClear;

public final class SereneBond {
    private final Window window = new Window();

    private final AtomicBoolean running = new AtomicBoolean(true);

    // Graphics.
    private final EntityGraphics entityGraphics = new EntityGraphics();
    private final WorldGraphics worldGraphics = new WorldGraphics();

    // Matrices.
    private final Matrix4f projection = new Matrix4f();
    private final Matrix4fStack modelView = new Matrix4fStack(2);

    private final Settings settings;

    private State state = State.IN_GAME;

    private World world;

    SereneBond(Settings settings) {
        this.settings = settings;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> running.set(false)));

        try {
            world = World.from("object/template/world.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void step() {
        // Fixed Time-step.
        var previous = System.nanoTime();
        var delta = 0.0D;

        while (running.getAndSet(!window.shouldClose())) {
            var fps = 1000000000.0D / 60.0D;

            var now = System.nanoTime();
            var elapsed = now - previous;

            previous = now;

            delta += elapsed;
            while (delta >= fps) {
                delta -= fps;

                if (world != null) {
                    world.step(settings);
                }

                draw();

                window.swapBuffers();
            }

            if (elapsed < fps) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(TimeUnit.NANOSECONDS.toMillis((long) (fps - elapsed)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT);

        var aspectRatio = window.getAspectRatio();
        var fieldOfView = 2.79253F;

        projection.identity();
        projection.ortho(-fieldOfView * aspectRatio, fieldOfView * aspectRatio, -fieldOfView, fieldOfView, 1.0F, -1.0F);

        switch (state) {
            case IN_GAME -> {
                entityGraphics.draw(world.entities, projection, modelView);

                worldGraphics.draw(world, projection, modelView);
            }

            default -> {

            }
        }
    }

    public void close() {
        window.destroy();
    }
}
