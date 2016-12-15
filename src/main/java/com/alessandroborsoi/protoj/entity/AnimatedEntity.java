package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.texture.Texture;

public abstract class AnimatedEntity extends Entity {
    protected Texture[] animationTextures;
    // This field hold the blast charge..
    protected float animationCursor;
    protected float animationSpeed = 4.4f;
    // This field set if player is charging a blast
    private boolean displayAnimation;

    public void init() {
//        this.animationTextures = ProtoJ.textureLoader.getAnimation(this.type);
//        this.original_width = animationTextures[0].getWidth();
//        this.original_height = animationTextures[0].getHeight();
    }

    public void startAnimation() {
        this.displayAnimation = true;
    }

    public void stopAnimation() {
        this.displayAnimation = false;
        this.animationCursor = 0;
    }
}
