package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.Entity;
import com.alessandroborsoi.protoj.texture.Sprite;

import org.lwjgl.opengl.GL11;

public class Planet extends Entity {

    public Planet() {
        this.type = PLANET;
        this.setRatio(0.65f);
        this.rotation = 180;
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
