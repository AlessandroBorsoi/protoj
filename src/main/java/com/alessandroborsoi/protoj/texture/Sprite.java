package com.alessandroborsoi.protoj.texture;

import lombok.Data;

@Data
public class Sprite {
    private int rows;
    private int cols;
    private int w;
    private int h;
    private int x;
    private int y;

    public Sprite(int rows, int cols, int w, int h) {
        this.rows = rows;
        this.cols = cols;
        this.w = w;
        this.h = h;
        this.x = 0;
        this.y = 0;
    }
    public Sprite(int rows, int cols, int w, int h, int x, int y) {
        this.rows = rows;
        this.cols = cols;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }
}
