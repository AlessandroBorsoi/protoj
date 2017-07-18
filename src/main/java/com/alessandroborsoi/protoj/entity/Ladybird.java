package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class Ladybird extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final String LAYER = LayerManager.ENEMIES;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.LADYBIRD;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SPEED = 100.0f;
    private static final float ANIMATION_SPEED = 10.0f;
    private double accumulator;

    public Ladybird() {
        position.x = ProtoJ.WIDTH;
        position.y = ((float) Math.random()) * ProtoJ.HEIGHT;
        oldPosition = position;
        index = 0;
        rotationAngle = 0.0f;
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
        oldPosition = new Vec2(position);
        accumulator += dt * ANIMATION_SPEED;
        index = (int) accumulator;
        position.x -= SPEED * dt;
        if (position.x < -getWidth())
            this.unspawn();
    }

    @Override
    public void unspawn() {
        super.unspawn();
        new Ladybird().spawn();
    }

    public void destroy() {
        new Explosion(position).spawn();
        this.unspawn();
    }
}
