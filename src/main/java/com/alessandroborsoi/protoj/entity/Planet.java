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
public class Planet {
    protected Texture texture;
    protected Shader shader;
    protected int quadVAO;
    public static final float S = 513.0f;
    public static final float T = 542.0f;
    public static final float W = 511.0f;
    public static final float H = 455.0f;
    public static final float TEXTURE_WIDTH = 1024.0f;
    public static final float TEXTURE_HEIGHT = 1024.0f;

    public Planet() {
        this.texture = ResourceManager.getTexture(TextureEnum.STAGE1_LAYER1.toString());
        this.shader = ResourceManager.getShader(ShaderEnum.SPRITE.toString());

        float vertices[] = {
                // Position     // Texture
                -0.5f, 0.5f,    S/TEXTURE_WIDTH, T/TEXTURE_HEIGHT,          // Top-left
                0.5f, 0.5f,     (S+W)/TEXTURE_WIDTH, T/TEXTURE_HEIGHT,      // Top-right
                0.5f, -0.5f,    (S+W)/TEXTURE_WIDTH, (T+H)/TEXTURE_HEIGHT,  // Bottom-right
                -0.5f, -0.5f,   S/TEXTURE_WIDTH, (T+H)/TEXTURE_HEIGHT,      // Bottom-left
        };

        int elements[] = {
            0, 1, 2,
            2, 3, 0
        };

        this.quadVAO = glGenVertexArrays();
        glBindVertexArray(this.quadVAO);

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

    public void draw() {
        this.shader.use();
        Mat4 model = new Mat4();
        shader.setMatrix4("model", model, false);
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(quadVAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
