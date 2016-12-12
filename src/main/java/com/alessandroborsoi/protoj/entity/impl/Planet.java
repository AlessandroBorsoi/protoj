package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.Entity;

import org.lwjgl.opengl.GL11;

public class Planet extends Entity {

    public Planet() {
        this.type = PLANET;
        init();
        this.setRatio(0.65f);
        this.rotation = 180;
    }

    @Override
    public void draw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.draw();
    }
}
