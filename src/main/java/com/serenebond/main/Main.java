package com.serenebond.main;

import com.serenebond.SereneBond;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseWheelListener(game);

        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW.");
        }

        var sereneBond = new SereneBond();
        sereneBond.step();
        sereneBond.close();

        glfwTerminate();
    }
}
