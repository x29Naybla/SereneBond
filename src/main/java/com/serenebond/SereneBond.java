package com.serenebond;

import java.util.concurrent.atomic.AtomicBoolean;

public final class SereneBond {
    private final Window window = new Window();

    private final AtomicBoolean running = new AtomicBoolean(true);

    private State state = State.MAIN_MENU;

    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> running.set(false)));
    }

    public void step() {

        while (running.getAndSet(!window.shouldClose())) {
            draw();
            window.swapBuffers();
        }
    }

    public void draw() {
        switch (state) {

            default -> {

            }
        }
    }

    public void close() {
        window.destroy();
    }
}
