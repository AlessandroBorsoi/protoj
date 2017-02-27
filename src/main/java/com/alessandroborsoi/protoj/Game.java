package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Planet;
import com.alessandroborsoi.protoj.entity.PlayerShip;
import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.ShaderEnum;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

@Log4j2
public class Game {
    private int width;
    private int height;
    private static Game instance;
    private Planet planet;
    private PlayerShip playerShip;

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
        Shader shader = ResourceManager.getShader(ShaderEnum.SPRITE.toString());
        shader.use().setInteger("image", 0, false);
        planet = new Planet();
        playerShip = new PlayerShip();
    }

    public void update(double timeSlice) {
        playerShip.update(timeSlice);
    }

    public void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        planet.draw();
        playerShip.draw();
    }
}
