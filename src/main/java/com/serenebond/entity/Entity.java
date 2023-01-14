package com.serenebond.entity;

import com.serenebond.math.BoundingBox;
import org.joml.Vector4d;

public sealed class Entity permits Player {
    private final Vector4d position = new Vector4d();

    private final BoundingBox boundingBox = new BoundingBox();

    public void step() {
        position.x += (position.z *= 0.1D);
        position.y += (position.w *= 0.1D);
    }

    public void move(double x, double y) {
        position.z += x;
        position.y += y;
    }
}
