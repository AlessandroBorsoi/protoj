package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class Explosion extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final String LAYER = LayerManager.FX;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.EXPLOSION_SMALL;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float ANIMATION_SPEED = 30.0f;
    private double accumulator;

    public Explosion(Vec2 position) {
        super(position);
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
        accumulator += dt * ANIMATION_SPEED;
        index = (int) accumulator;
        if (index >= textureEnum.getRows() * textureEnum.getColumns())
            this.unspawn();
    }
}
