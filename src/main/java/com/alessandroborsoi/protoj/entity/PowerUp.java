package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import java.util.Random;

import glm.vec._2.Vec2;
import lombok.Getter;

public class PowerUp extends Entity {
    public static final int P_GREEN = 0;
    public static final int P_BLUE = 1;
    public static final int P_PURPLE = 2;
    public static final int P_RED = 3;
    public static final int S = 4;
    public static final int M = 5;
    public static final int B = 6;
    public static final int C = 7;
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final String LAYER = LayerManager.POWER_UP;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.POWER_UP;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.4f;
    private static final float SPEED = 50.0f;
    @Getter private int type;
    private double accumulator;

    public PowerUp(int type) {
        this.type = type;
        position.x = ProtoJ.WIDTH;
        position.y = ((float) Math.random()) * ProtoJ.HEIGHT;
        oldPosition = position;
        scaleRatio = SCALE_RATIO;
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
        accumulator += dt * 10.0;
        if (accumulator > 1.0) {
            index = ++index % 8 + type * TEXTURE_ENUM.getRows();
            accumulator = 0.0;
        }
        position.x -= SPEED * dt;
        if (position.x < -getWidth())
            this.unspawn();
    }

    public void destroy() {
        new Implosion(position).spawn();
        this.unspawn();
    }

    @Override
    public void unspawn() {
        super.unspawn();
        new PowerUp(new Random().nextInt(8)).spawn();
    }
}
