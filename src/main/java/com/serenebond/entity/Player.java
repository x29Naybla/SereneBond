package com.serenebond.entity;

import com.serenebond.KeysBinds;

public final class Player extends Entity {

    public void step(KeysBinds keysBinds) {

        if (keysBinds.held("walk_up")) {
            position.w += 0.01F;
        }

        if (keysBinds.held("walk_left")) {
            position.z -= 0.01F;
        }

        if (keysBinds.held("walk_down")) {
            position.w -= 0.01F;
        }

        if (keysBinds.held("walk_right")) {
            position.z += 0.01F;
        }
    }
}
