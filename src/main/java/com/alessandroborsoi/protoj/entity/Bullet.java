package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

public class Bullet extends Entity {
    private static final float WIDTH = 32.0f;
    private static final float HEIGHT = 32.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.BULLET_SET1;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.5f;
    private static final float SPEED = 1000.0f;

    public Bullet(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
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
        posX += SPEED * dt;
        if (posX > ProtoJ.WIDTH) {
            this.unspawn();
        }
    }
}
