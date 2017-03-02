package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

public class PlayerSpeed extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.PLAYER_SPEED;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.4f;
    private PlayerShip playerShip;
    private boolean on;
    private double accumulator;

    public PlayerSpeed() {
        scaleRatio = SCALE_RATIO;
        playerShip = PlayerShip.getInstance();
        posX = playerShip.getPosX() - playerShip.getWidth();
        posY = playerShip.getPosY();
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
    public void update(double timeSlice) {
        posX = playerShip.getPosX() - playerShip.getWidth();
        posY = playerShip.getPosY();
    }
}
