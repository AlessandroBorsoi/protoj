package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.EntityEnum;
import com.alessandroborsoi.protoj.entity.SingleEntity;
import com.alessandroborsoi.protoj.texture.Sprite;

import org.lwjgl.opengl.GL11;

public class Planet extends SingleEntity {

    public Planet() {
    }

    @Override
    protected String getTextureName() {
        return EntityEnum.STAGE1_LAYER1.toString();
    }

    @Override
    public void draw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.draw();
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite(1, 1, 512, 512, 510, 510);
    }
}
