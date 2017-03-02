package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.Game;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Ladybird extends Entity {
    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.LADYBIRD;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.ANIMATED;
    private static final float SPEED = 100.0f;
    @Getter
    private int index;
    private double accumulator;

    public Ladybird() {
        posX = ProtoJ.WIDTH;
        posY = ((float) Math.random()) * ProtoJ.HEIGHT;
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
        accumulator += timeSlice * 6.0;
        if (accumulator > 1.0) {
            index = ++index % 16;
            accumulator = 0.0;
        }
        posX -= SPEED * timeSlice;
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

    public void destroy() {
        Game protoJ = Game.getInstance();
        protoJ.getBackground().add(new Explosion(this.posX, this.posY));
        protoJ.getEnemies().remove(this);
    }
}
