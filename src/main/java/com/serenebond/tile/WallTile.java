package com.serenebond.tile;

public final class WallTile extends TexturedTile {

    public WallTile(int u, int v) {
        super(u, v);
    }

    @Override
    public boolean collides() {
        return true;
    }
}
