package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.AnimatedEntity;

import org.lwjgl.opengl.GL11;

public class Text extends AnimatedEntity {
    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    private char[] chars;
    private String string = null;
    private int mode = LEFT_TO_RIGHT;

    public Text(String string) {
        this.setString(string);
        this.type = FONT;
        init();
        setRatio(0.4f);
    }

    public void setString(String string) {
        this.string = string;
        chars = this.string.toCharArray();
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, position.y, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        if (mode == LEFT_TO_RIGHT) {
            for (int i = 0; i < chars.length; i++) {
                drawChar(i);
                GL11.glTranslatef(width + 1, 0, 0);
            }
        }
        if (mode == RIGHT_TO_LEFT) {
            for (int i = chars.length - 1; i >= 0; i--) {
                drawChar(i);
                GL11.glTranslatef(-width + 1, 0, 0);
            }
        }
    }

    private void drawChar(int i) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[chars[i]].getId());
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
