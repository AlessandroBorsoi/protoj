package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class Explosion extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.EXPLOSION_SMALL;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private double accumulator;

    public Explosion(Vec2 position) {
        this.position = position;
        index = 0;
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
        return LayerManager.BACKGROUND;
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
        accumulator += dt * 60.0;
        if (accumulator > 1.0) {
            accumulator = 0.0;
            ++index;
        }
        if (index == 32)
            this.unspawn();
    }
}
