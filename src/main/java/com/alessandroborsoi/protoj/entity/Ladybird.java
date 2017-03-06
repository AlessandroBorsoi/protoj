package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

public class Ladybird extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.LADYBIRD;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SPEED = 100.0f;
    private double accumulator;

    public Ladybird() {
        position.x = ProtoJ.WIDTH;
        position.y = ((float) Math.random()) * ProtoJ.HEIGHT;
        oldPosition = position;
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
        return LayerManager.ENEMIES;
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
        accumulator += dt * 6.0;
        if (accumulator > 1.0) {
            index = ++index % 16;
            accumulator = 0.0;
        }
        position.x -= SPEED * dt;
        if (position.x < -getWidth())
            this.unspawn();
    }

    public void destroy() {
        new Explosion(position).spawn();
        this.unspawn();
    }
}
