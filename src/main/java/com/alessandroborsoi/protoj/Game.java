package com.alessandroborsoi.protoj;

public class Game {
    private int width;
    private int height;
    private static Game instance;

    private Game(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Game getInstance(int width, int height) {
        if (instance == null) {
            instance = new Game(width, height);
        }
        return instance;
    }

    public void init() {

    }

    public void processInput(float deltaTime) {

    }

    public void update(float deltaTime) {

    }

    public void render() {

    }
}
