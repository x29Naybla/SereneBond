package com.serenebond;

import com.google.gson.GsonBuilder;
import com.serenebond.original.main.Game;
import com.serenebond.tile.Tile;
import com.serenebond.tile.Tiles;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        launchOriginal();

        var options = new Options();
        options.addOption("directory", true, "Runtime directory");

        try {
            var parser = new DefaultParser();
            var commandLine = parser.parse(options, args);

            var directory = Paths.get(commandLine.getOptionValue("directory", "run"));
            if (!Files.isDirectory(directory)) {
                try {
                    Files.createDirectory(directory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            var gson = new GsonBuilder()
                    .registerTypeAdapter(Settings.class, new Settings.Codec())
                    .registerTypeAdapter(Tile.class, new Tile.Codec())
                    .setPrettyPrinting().
                    create();

            var settings = Settings.of(directory, gson);

            Tiles.register(gson);

            if (!glfwInit()) {
                throw new RuntimeException("Failed to initialize GLFW.");
            }

            var sereneBond = new SereneBond(settings);
            sereneBond.step();
            sereneBond.close();

            glfwTerminate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private static void launchOriginal() {
        Game game = new Game();
        game.start();
        game.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseWheelListener(game);
    }
}
