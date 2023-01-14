package com.serenebond.entity;

import com.serenebond.math.BoundingBox;
import org.joml.Vector4f;

public sealed class Entity permits Player {
    public final Vector4f position = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);

    private final BoundingBox boundingBox = new BoundingBox();

    public void step() {
        position.z *= 0.5F;
        position.w *= 0.5F;

        position.x += position.z;
        position.y += position.w;
    }
}
