package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.KeyCallback;
import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class ForceCharge extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final String LAYER = LayerManager.FX;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.FORCE_CHARGE;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 1.0f;
    private static final float ANIMATION_SPEED = 30.0f;
    private final PlayerShip playerShip;
    private float accumulator;

    public ForceCharge() {
        playerShip = PlayerShip.getInstance();
        position = new Vec2(playerShip.getPosition());
        position.x += playerShip.getWidth() / 1.3f;
        this.scaleRatio = SCALE_RATIO;
        blackFilter = true;
    }

    @Override
    protected String getLayer() {
        return LAYER;
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
        position.x += playerShip.getWidth() / 1.3f;
        if (KeyCallback.forceBlastCharge) {
            accumulator += dt * ANIMATION_SPEED;
            index = (int) accumulator;
        } else {
            this.unspawn();
        }
    }
}
