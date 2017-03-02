package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.IEntity;
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
    @Getter
    private Layer background = new Layer();
    @Getter
    private Layer player = new Layer();
    @Getter
    private Layer enemies = new Layer();
    @Getter
    private Layer foreground = new Layer();
    @Getter
    private int score;

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
        Shader shader = ResourceManager.getShader(ShaderEnum.SPRITE.getName());
        shader.use().setInteger("image", 0);
        background.add(new Planet());
        player.add(PlayerShip.getInstance());
        enemies.add(new Ladybird());
    }

    public void update(double timeSlice) {
        checkCollisions();
        background.update(timeSlice);
        enemies.update(timeSlice);
        player.update(timeSlice);
        foreground.update(timeSlice);
    }

    private void checkCollisions() {
        List<IEntity> playerEntities = player.entities;
        List<IEntity> enemiesEntities = enemies.entities;
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
//        if (entityA.position.x + entityA.width < entityB.position.x)
//            return false;
//        if (entityA.position.y + entityA.height < entityB.position.y)
//            return false;
//        if (entityA.position.x > entityB.position.x + entityB.width)
//            return false;
//        if (entityA.position.y > entityB.position.y + entityB.height)
//            return false;
//        return true;


        if (((Ladybird) entityB).getPosX() < 500.0f)
            return true;
        return false;
    }

    public void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        background.render();
        enemies.render();
        player.render();
        foreground.render();
    }
}
