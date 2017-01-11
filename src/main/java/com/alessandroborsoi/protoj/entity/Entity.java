package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.Texture;

import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;

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

    public void draw(Vec2 position) {
        this.shader.use();
        Mat4 model = new Mat4();
        model = model.translate(position.x, position.y, 0.0f);
//        model = model.rotateZ(45.0f);
        model = model.scale(new Vec3(2.0f, 2.0f, 2.0f));
        shader.setMatrix4("model", model, false);
        shader.setVector3f("spriteColor", new Vec3(1.0f, 1.0f, 1.0f), false);
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(quadVAO);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }

    public abstract void setTexture();

    public abstract void setShader();
}
