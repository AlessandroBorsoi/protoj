package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

@Log4j2
public class Text extends Entity {
    private static final float WIDTH = 32.0f;
    private static final float HEIGHT = 32.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.FONTS;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.ANIMATED;
    @Setter private String text;

    public Text(String text, float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.text = text;
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

    }

    @Override
    public void render() {
        this.shader.use();
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        float x = posX;
        for (int i = 0; i < text.length(); i++) {
            Mat4 model = new Mat4();
            model = model.translate(new Vec3(x, posY, 0.0f));
            shader.setMatrix4("model", model);
            shader.setInteger("index", ((int) text.charAt(i)));
            shader.setInteger("rows", 16);
            shader.setInteger("columns", 16);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            x += 0.05f;
        }
        glBindVertexArray(0);
    }
}
