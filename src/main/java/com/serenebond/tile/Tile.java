package com.serenebond.tile;

import com.serenebond.world.World;

import java.awt.*;

public sealed interface Tile permits TexturedTile {

    void draw(Graphics graphics, int x, int y, World world);

    boolean collides();
}
