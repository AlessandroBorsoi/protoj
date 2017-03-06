package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.IEntity;
import com.alessandroborsoi.protoj.entity.Ladybird;
import com.alessandroborsoi.protoj.entity.Planet;
import com.alessandroborsoi.protoj.entity.PlayerShip;
import com.alessandroborsoi.protoj.resource.ResourceManager;

import java.util.List;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

@Log4j2
public class Game {
    private static Game instance;
    @Getter private int score;

    private Game() {
        score = 0;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init() {
        ResourceManager.init();
        new Planet().spawn();
        PlayerShip.getInstance().spawn();
    }

    public void update(double dt) {
        checkCollisions();
        LayerManager.getInstance().update(dt);
//        new Ladybird().spawn();
    }

    private void checkCollisions() {
        List<IEntity> playerEntities = LayerManager.getInstance().getLayers().get(LayerManager.PLAYER).entities;
        List<IEntity> enemiesEntities = LayerManager.getInstance().getLayers().get(LayerManager.ENEMIES).entities;
        for (int i = 0; i < playerEntities.size(); i++) {
            for (int j = 0; j < enemiesEntities.size(); j++) {
                if (collision(playerEntities.get(i), enemiesEntities.get(j))) {
                    ((Ladybird) enemiesEntities.get(j)).destroy();
                    score++;
                }
            }
        }
    }

    private boolean collision(IEntity entityA, IEntity entityB) {
        if (entityA.getPosition().x + entityA.getWidth() < entityB.getPosition().x)
            return false;
        if (entityA.getPosition().y + entityA.getHeight() < entityB.getPosition().y)
            return false;
        if (entityA.getPosition().x > entityB.getPosition().x + entityB.getWidth())
            return false;
        if (entityA.getPosition().y > entityB.getPosition().y + entityB.getHeight())
            return false;
        return true;
    }

    public void render(double alpha) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        LayerManager.getInstance().render(alpha);
    }
}
