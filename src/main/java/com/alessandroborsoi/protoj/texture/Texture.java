package com.alessandroborsoi.protoj.texture;

import lombok.Data;

@Data
public class Texture {
    private int id;
    private int height;
    private int width;

    public Texture(int id, int height, int width) {
        this.id = id;
        this.height = height;
        this.width = width;
    }
}
