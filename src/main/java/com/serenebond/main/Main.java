package com.serenebond.main;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseWheelListener(game);
    }
}
