package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

@Log4j2
public class Planet extends Entity {
    private static final float S = 513.0f;
    private static final float T = 542.0f;
    private static final float WIDTH = 511.0f;
    private static final float HEIGHT = 455.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.STAGE1_LAYER1;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.IRREGULAR;
    private static final float SPEED = 5.0f;
    private static final float SCALE_RATIO = 1.3f;

    public Planet() {
        this.posX = ProtoJ.WIDTH;
        this.posY = ProtoJ.HEIGHT / 2;
        this.scaleRatio = SCALE_RATIO;
    }

    @Override
    public float getSpriteWidth() {
        return WIDTH;
    }

    @Override
    public float getSpriteHeight() {
        return HEIGHT;
    }

    @Override
    protected String getLayer() {
        return LayerManager.BACKGROUND;
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
    protected float[] getVertices() {
        float vertices[] = {
                // Position     // Texture
                0.0f, 0.0f,     S / this.textureEnum.getWidth(), T / this.textureEnum.getHeight(),                      // Top-left
                WIDTH, 0.0f,    (S + WIDTH) / this.textureEnum.getWidth(), T / this.textureEnum.getHeight(),            // Top-right
                WIDTH, HEIGHT,  (S + WIDTH) / this.textureEnum.getWidth(), (T + HEIGHT) / this.textureEnum.getHeight(), // Bottom-right
                0.0f, HEIGHT,   S / this.textureEnum.getWidth(), (T + HEIGHT) / this.textureEnum.getHeight(),           // Bottom-left
        };
        return vertices;
    }

    @Override
    public void update(double dt) {
        posX -= SPEED * dt;
    }

    @Override
    public void render(double alpha) {
        this.shader.use();
        Mat4 model = new Mat4()
                .translate(new Vec3(-getWidth() / 2.0f, -getHeight() / 2.0f, 0.0f))
                .translate(new Vec3(posX, posY, 0.0f));
        Mat4 scale = new Mat4().scale(scaleRatio);
        shader.setMatrix4("projection", projection);
        shader.setMatrix4("model", model);
        shader.setMatrix4("scale", scale);
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
