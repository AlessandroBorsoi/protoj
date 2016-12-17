package com.alessandroborsoi.protoj.entity.impl;

import com.alessandroborsoi.protoj.entity.AnimatedEntity;
import com.alessandroborsoi.protoj.entity.EntityEnum;
import com.alessandroborsoi.protoj.texture.Sprite;

import org.lwjgl.opengl.GL11;

public class Text extends AnimatedEntity {
    private char[] chars;
    private String string = null;

    public Text(String string) {
        this.setString(string);
    }

    public void setString(String string) {
        this.string = string;
        chars = this.string.toCharArray();
    }

    @Override
    protected String getTextureName() {
        return EntityEnum.FONTS.toString();
    }

    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, position.y, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        for (int i = 0; i < chars.length; i++) {
            drawChar(i);
            GL11.glTranslatef(width + 1, 0, 0);
        }
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite(16, 16, 32, 32);
    }

    private void drawChar(int i) {
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animation[chars[i]].getId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
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
