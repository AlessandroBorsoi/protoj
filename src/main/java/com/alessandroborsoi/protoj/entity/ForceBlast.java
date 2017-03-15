package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;

public class ForceBlast extends Entity {
    private static final String LAYER = LayerManager.BULLETS;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.FORCE_BLAST;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.5f;
    private static final float SPEED = 1000.0f;

    public ForceBlast(Vec2 position, int type) {
        super(position);
        scaleRatio = SCALE_RATIO * type;
    }

    @Override
    protected String getLayer() {
        return LAYER;
    }

    @Override
    protected float getSpriteWidth() {
        return TEXTURE_ENUM.getWidth();
    }

    @Override
    protected float getSpriteHeight() {
        return TEXTURE_ENUM.getHeight();
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
        position.x += SPEED * dt;
        if (position.x > ProtoJ.WIDTH) {
            this.unspawn();
        }
    }
}
