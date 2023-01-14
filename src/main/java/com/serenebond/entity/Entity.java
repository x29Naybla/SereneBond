package com.serenebond.entity;

import com.serenebond.math.BoundingBox;
import com.serenebond.persistent.ReadablePersistent;
import com.serenebond.persistent.WritablePersistent;
import org.joml.Vector4f;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public sealed class Entity implements WritablePersistent, ReadablePersistent permits Player {
    public final Vector4f position = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);

    private final BoundingBox boundingBox = new BoundingBox();

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(position.x);
        dataOutput.writeFloat(position.y);
        dataOutput.writeFloat(position.z);
        dataOutput.writeFloat(position.w);
    }

    @Override
    public void read(DataInput dataInput) throws IOException {
        position.x = dataInput.readFloat();
        position.y = dataInput.readFloat();
        position.z = dataInput.readFloat();
        position.w = dataInput.readFloat();
    }

    public void step() {
        position.z = lerp(position.z, 0.2375F);
        position.w = lerp(position.w, 0.2375F);

        position.x += position.z;
        position.y += position.w;
    }

    private static float lerp(float x, float z) {
        return x + -x * z;
    }
}
