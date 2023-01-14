package com.serenebond.original.tiles;

public class WallTileType extends SimpleTileType{
    public WallTileType(int u, int v) {
        super(u, v);
    }

    public boolean hasCollision(){
        return true;
    }
}
