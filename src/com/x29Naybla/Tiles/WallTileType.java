package com.x29Naybla.Tiles;

public class WallTileType extends SimpleTileType{
    public WallTileType(int u, int v) {
        super(u, v);
    }

    boolean hasCollision(){
        return true;
    }
}
