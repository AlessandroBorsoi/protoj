package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Ladybird;
import com.alessandroborsoi.protoj.entity.Planet;
import com.alessandroborsoi.protoj.entity.PlayerShip;
import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.ShaderEnum;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

@Log4j2
public class Game {
    private static Game instance;
    @Getter private Layer layer = new Layer();

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init() {
        ResourceManager.init();
        Shader shader = ResourceManager.getShader(ShaderEnum.SPRITE.toString());
        shader.use().setInteger("image", 0, false);
        layer.add(new Planet());
        layer.add(PlayerShip.getInstance());
        layer.add(new Ladybird());
    }

    public void update(double timeSlice) {
        layer.update(timeSlice);
    }

    public void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        layer.render();
    }
}
