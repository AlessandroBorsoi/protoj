package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Planet;
import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;

import glm.mat._4.Mat4;
import glm.vec._2.Vec2;

public class Game {
    private int width;
    private int height;
    private static Game instance;
    private Planet planet;

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
        ResourceManager.init();
        Mat4 projection = new Mat4();
        projection = projection.ortho(0.0f, ProtoJ.WIDTH, ProtoJ.HEIGHT, 0.0f, -1.0f, 1.0f);
        ResourceManager.getShader(ShaderEnum.SPRITE.toString()).use().setInteger("image", 0, false);
        ResourceManager.getShader(ShaderEnum.SPRITE.toString()).setMatrix4("projection", projection, false);
        planet = new Planet();
    }

    public void processInput(float deltaTime) {

    }

    public void update(float deltaTime) {

    }

    public void render() {
        planet.draw(new Vec2(200.0f, 200.0f));
    }
}
