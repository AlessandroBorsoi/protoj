package com.alessandroborsoi.protoj.texture;

public interface TextureLoader {
    void init();

    Texture getTexture(int textureId);

    Texture[] getAnimation(int animationId);
}
