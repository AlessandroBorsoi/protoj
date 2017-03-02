package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.Game;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Explosion extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.EXPLOSION_SMALL;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.ANIMATED;
    private int index;
    private double accumulator;

    public Explosion(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        index = 0;
    }

    @Override
    protected float getWidth() {
        return WIDTH;
    }

    @Override
    protected float getHeight() {
        return HEIGHT;
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
    public void update(double timeSlice) {
        accumulator += timeSlice * 60.0;
        if (accumulator > 1.0) {
            accumulator = 0.0;
            ++index;
        }
        if (index == 32)
            Game.getInstance().getBackground().remove(this);
    }

    @Override
    public void render() {
        this.shader.use();
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        Mat4 model = new Mat4().translate(new Vec3(posX, posY, 0.0f));
        Mat4 scale = new Mat4().scale(scaleRatio);
        shader.setInteger("index", index);
        shader.setInteger("rows", textureEnum.getRows());
        shader.setInteger("columns", textureEnum.getColumns());
        shader.setMatrix4("projection", projection);
        shader.setMatrix4("model", model);
        shader.setMatrix4("scale", scale);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
