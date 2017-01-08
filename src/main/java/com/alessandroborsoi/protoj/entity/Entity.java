package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.Texture;
import com.alessandroborsoi.protoj.util.Vector2f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public abstract class Entity {
    protected Texture texture;
    protected Shader shader;
    protected int quadVAO;

    protected Entity() {
        setTexture();
        setShader();
    }

    public void draw(Vector2f position) {
        this.shader.use();

        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(quadVAO);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }

    public abstract void setTexture();

    public abstract void setShader();

    public abstract void setQuadVAO(int quadVAO);
}
