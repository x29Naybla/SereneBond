package com.serenebond.entity;

import com.serenebond.KeysBinds;

public final class Player extends Entity {

    public void step(KeysBinds keysBinds) {

        if (keysBinds.pressed("walk_up")) {
            position.w += 0.01F;
        }

        if (keysBinds.pressed("walk_left")) {
            position.z -= 0.01F;
        }

        if (keysBinds.pressed("walk_down")) {
            position.w -= 0.01F;
        }

        if (keysBinds.pressed("walk_right")) {
            position.z += 0.01F;
        }
    }
}
