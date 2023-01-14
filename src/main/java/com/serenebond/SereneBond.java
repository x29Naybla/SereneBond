package com.serenebond;

public final class SereneBond {
    private final Window window = new Window();

    private State state = State.MAIN_MENU;

    public void step() {

        while (!window.shouldClose()) {
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
