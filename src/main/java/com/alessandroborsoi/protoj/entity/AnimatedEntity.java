package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.texture.Coordinates;

public abstract class AnimatedEntity extends Entity {
    protected Coordinates[] animation;

    protected AnimatedEntity() {
        this.animation = getCoordinates(sprite);
    }
}
