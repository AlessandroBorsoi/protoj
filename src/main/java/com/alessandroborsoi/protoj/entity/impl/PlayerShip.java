package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.AnimatedEntity;

import org.lwjgl.opengl.GL11;

public class PlayerShip extends AnimatedEntity {
    private final static float MAX_SPEED = 230;

    public PlayerShip() {
//        concentrateAnimation.spawn(new Vector2f(0, 0), new Vector2f(0, 0), Prototyp.fx);
//        accelerateEntrity.spawn(new Vector2f(0, 0), new Vector2f(0, 0), Prototyp.fx);
        this.type = PLAYER_SHIP;
        init();
        setRatio(0.25f);
    }

    @Override
    public void draw() {
        GL11.glLoadIdentity();
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glTranslatef(position.x, position.y, 0);
        int animationFrame = (int) ((speed.y * 4) / MAX_SPEED);
        animationFrame += 4;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[animationFrame].getId());
        GL11.glColor4f(1, 1, 1, 1f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(textureRight, textureUp); //Upper right
            GL11.glVertex2f(width, -height);

            GL11.glTexCoord2f(textureLeft, textureUp); //Upper left
            GL11.glVertex2f(-width, -height);

            GL11.glTexCoord2f(textureLeft, textureDown); //Lower left
            GL11.glVertex2f(-width, height);

            GL11.glTexCoord2f(textureRight, textureDown); // Lower right
            GL11.glVertex2f(width, height);
        }
        GL11.glEnd();
    }
}
