package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class Implosion extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final String LAYER = LayerManager.FX;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.IMPLOSION;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.4f;
    private static final float ANIMATION_SPEED = 30.0f;
    private double accumulator;

    public Implosion(Vec2 position) {
        super(position);
        scaleRatio = SCALE_RATIO;
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
        accumulator += dt * ANIMATION_SPEED;
        index = ((int) accumulator);
        if (index >= textureEnum.getRows() * textureEnum.getColumns())
            this.unspawn();
    }
}
