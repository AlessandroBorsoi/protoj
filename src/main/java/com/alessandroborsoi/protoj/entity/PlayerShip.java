package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.KeyCallback;
import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class PlayerShip extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.PLAYER_SHIP;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.4f;
    private static final float SPEED = 600.0f;
    private static PlayerShip playerShip;

    public static PlayerShip getInstance() {
        if (playerShip == null) {
            playerShip = new PlayerShip();
        }
        return playerShip;
    }

    private PlayerShip() {
        position.x = ProtoJ.WIDTH / 4;
        position.y = ProtoJ.HEIGHT / 2;
        scaleRatio = SCALE_RATIO;
    }

    @Override
    public float getSpriteWidth() {
        return WIDTH;
    }

    @Override
    public float getSpriteHeight() {
        return HEIGHT;
    }

    @Override
    protected String getLayer() {
        return LayerManager.PLAYER;
    }

    @Override
    protected TextureEnum getTextureEnum() {
        return TEXTURE_ENUM;
    }

    @Override
    protected ShaderEnum getShaderEnum() {
        return SHADER_ENUM;
    }

    @Override
    public void update(double dt) {
        if (KeyCallback.isKeyDown(GLFW_KEY_RIGHT)) {
            position.x = position.x < 800.0f ? position.x += (SPEED * dt) : position.x;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_LEFT)) {
            position.x = position.x > 0.0f ? position.x -= (SPEED * dt) : position.x;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_UP)) {
            position.y = position.y > 0.0f ? position.y -= (SPEED * dt) : position.y;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_DOWN)) {
            position.y = position.y < 600.0f ? position.y += (SPEED * dt) : position.y;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_SPACE)) {
            new Bullet(new Vec2(position.x, position.y)).spawn();
        }
    }
}
