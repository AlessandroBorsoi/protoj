package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.texture.Coordinates;

public abstract class AnimatedEntity extends Entity {
    protected Coordinates[] animation;

    public AnimatedEntity() {
        int rows = sprite.getRows();
        int cols = sprite.getCols();
        int x = sprite.getX();
        int w = sprite.getW();
        int y = sprite.getY();
        int h = sprite.getH();
        this.animation = new Coordinates[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coordinates coordinates = new Coordinates();
                coordinates.getCoordinates()[0].x = x + w * j;
                coordinates.getCoordinates()[0].y = y + h * i;
                coordinates.getCoordinates()[1].x = x + w * (j + 1);
                coordinates.getCoordinates()[1].y = y + h * i;
                coordinates.getCoordinates()[2].x = x + w * (j + 1);
                coordinates.getCoordinates()[2].y = y + h * (i + 1);
                coordinates.getCoordinates()[3].x = x + w * j;
                coordinates.getCoordinates()[3].y = y + h * (i + 1);
                animation[i * cols + j] = coordinates;
            }
        }
    }
}
