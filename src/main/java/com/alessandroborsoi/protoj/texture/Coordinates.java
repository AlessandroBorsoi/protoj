package com.alessandroborsoi.protoj.texture;

import com.alessandroborsoi.protoj.util.Vector2f;

import lombok.Data;

@Data
public class Coordinates {
    private Vector2f[] coordinates;

    public Coordinates() {
        coordinates = new Vector2f[4];
        for (int i = 0; i < 4; i++) {
            coordinates[i] = new Vector2f();
        }
    }
}
