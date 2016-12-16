package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.texture.Coordinates;

public abstract class SingleEntity extends Entity {
    protected Coordinates image;

    protected SingleEntity() {
        this.image = getCoordinates(sprite)[0];
    }
}
