package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.resource.ResourceManager;
import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.Texture;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

@Log4j2
public class Planet implements Entity {
    private static final float S = 513.0f;
    private static final float T = 542.0f;
    private static final float W = 511.0f;
    private static final float H = 455.0f;
    private static final float TEXTURE_WIDTH = 1024.0f;
    private static final float TEXTURE_HEIGHT = 1024.0f;
    private Texture texture;
    private Shader shader;
    private int vao;

    public Planet() {
        this.texture = ResourceManager.getTexture(TextureEnum.STAGE1_LAYER1.toString());
        this.shader = ResourceManager.getShader(ShaderEnum.SPRITE.toString());

        float vertices[] = {
                // Position     // Texture
                0.0f, 0.0f,    S / TEXTURE_WIDTH, T / TEXTURE_HEIGHT,              // Top-left
                511.0f, 0.0f,     (S + W) / TEXTURE_WIDTH, T / TEXTURE_HEIGHT,        // Top-right
                511.0f, 455.0f,    (S + W) / TEXTURE_WIDTH, (T + H) / TEXTURE_HEIGHT,  // Bottom-right
                0.0f, 455.0f,   S / TEXTURE_WIDTH, (T + H) / TEXTURE_HEIGHT,        // Bottom-left
        };

        int elements[] = {
                0, 1, 2,
                2, 3, 0
        };

        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * 4, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void update(double timeSlice) {

    }

    @Override
    public void render() {
        this.shader.use();
        Mat4 model = new Mat4();
        Mat4 projection = new Mat4();
        projection = projection.ortho(0.0f, 800.0f, 600.0f, 0.0f, -1.0f, 1.0f);
        shader.setMatrix4("projection", projection, false);
        shader.setMatrix4("model", model, false);
        shader.setMatrix4("scale", new Mat4(), false);
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
