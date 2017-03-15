package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.KeyCallback;
import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class PlayerSpeed extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final String LAYER = LayerManager.FX;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.PLAYER_SPEED;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.8f;
    private static final float ANIMATION_SPEED = 30.0f;
    private PlayerShip playerShip;
    private float accumulator;

    public PlayerSpeed() {
        playerShip = PlayerShip.getInstance();
        scaleRatio = SCALE_RATIO;
        blackFilter = true;
    }

    @Override
    protected float getSpriteWidth() {
        return WIDTH;
    }

    @Override
    protected float getSpriteHeight() {
        return HEIGHT;
    }

    @Override
    protected String getLayer() {
        return LAYER;
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
    public void update(float dt) {
        oldPosition = new Vec2(position);
        position = new Vec2(playerShip.getPosition());
        position.x -= playerShip.getWidth() / 1.3f;
        position.y += playerShip.getHeight() / 10;
        if (KeyCallback.moveForward) {
            accumulator += dt * ANIMATION_SPEED;
            index = ((int) accumulator);
            if (index >= textureEnum.getRows() * textureEnum.getColumns())
                this.unspawn();
        } else {
            this.unspawn();
        }
    }
}
