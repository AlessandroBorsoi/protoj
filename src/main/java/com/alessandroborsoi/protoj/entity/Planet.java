package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

public class Planet extends Entity {

    @Override
    public void setTexture() {
        this.texture = ResourceManager.getTexture(TextureEnum.STAGE1_LAYER1.toString());
    }

    @Override
    public void setShader() {
        this.shader = ResourceManager.getShader(ShaderEnum.SPRITE.toString());
    }

    @Override
    public void setQuadVAO(int quadVAO) {

    }
}
