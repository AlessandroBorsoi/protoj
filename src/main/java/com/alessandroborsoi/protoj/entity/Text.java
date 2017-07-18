package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;
import lombok.Setter;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Text extends Entity {
    private static final float WIDTH = 32.0f;
    private static final float HEIGHT = 32.0f;
    private static final String LAYER = LayerManager.TEXT;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.FONTS;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final float SCALE_RATIO = 0.5f;
    @Setter private String text;

    public Text(String text, Vec2 position) {
        super(position);
        this.text = text;
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
        return LAYER;
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
    public void update(float dt) {

    }

    @Override
    public void render(float alpha) {
        this.shader.use();
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        float x = position.x;
        Mat4 scale = new Mat4().scale(scaleRatio);
        shader.setInteger("rows", textureEnum.getRows());
        shader.setInteger("columns", textureEnum.getColumns());
        shader.setMatrix4("projection", projection);
        shader.setMatrix4("scale", scale);
        shader.setMatrix4("rotation", new Mat4());
        for (int i = 0; i < text.length(); i++) {
            Mat4 model = new Mat4().translate(new Vec3(x, position.y, 0.0f));
            shader.setInteger("index", ((int) text.charAt(i)));
            shader.setMatrix4("model", model);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            x += 10.0f;
        }
        glBindVertexArray(0);
    }
}
