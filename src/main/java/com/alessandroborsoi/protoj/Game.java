package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Entity;
import com.alessandroborsoi.protoj.entity.Ladybird;
import com.alessandroborsoi.protoj.entity.Planet;
import com.alessandroborsoi.protoj.entity.PlayerShip;
import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.ShaderEnum;

import java.util.List;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

@Log4j2
public class Game {
    private static Game instance;
    @Getter private Layer background = new Layer();
    @Getter private Layer player = new Layer();
    @Getter private Layer enemies = new Layer();

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
        background.add(new Planet());
        player.add(PlayerShip.getInstance());
        enemies.add(new Ladybird());
    }

    public void update(double timeSlice) {
        checkCollisions();
        background.update(timeSlice);
        enemies.update(timeSlice);
        player.update(timeSlice);
    }

    private void checkCollisions() {
        List<Entity> playerEntities = player.entities;
        List<Entity> enemyEntities = enemies.entities;
        for (int i = 0; i < playerEntities.size(); i++) {
            for (int j = 0; j < enemyEntities.size(); j++) {
                if (collision(playerEntities.get(i), enemyEntities.get(j))) {
                    ((Ladybird) enemyEntities.get(j)).destroy();
                }
            }
        }
    }

    private boolean collision(Entity entityA, Entity entityB) {
        if (((Ladybird) entityB).getPosX() < 0.75f)
            return true;
        return false;
    }

    public void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        background.render();
        enemies.render();
        player.render();
    }
}
