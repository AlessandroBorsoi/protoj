package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.Shader;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.Texture;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;
import lombok.Getter;

import static com.alessandroborsoi.protoj.resource.ResourceManager.getShader;
import static com.alessandroborsoi.protoj.resource.ResourceManager.getTexture;
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

public abstract class Entity implements IEntity {
    protected float width;
    protected float height;
    protected String layer;
    protected TextureEnum textureEnum;
    protected ShaderEnum shaderEnum;
    protected Texture texture;
    protected Shader shader;
    protected float vertices[];
    protected Mat4 projection;
    protected int vao;
    @Getter Vec2 position;
    @Getter Vec2 oldPosition;
    protected int index;
    protected float scaleRatio;

    protected Entity() {
        this.width = getSpriteWidth();
        this.height = getSpriteHeight();
        this.layer = getLayer();
        this.textureEnum = getTextureEnum();
        this.shaderEnum = getShaderEnum();
        this.texture = getTexture(this.textureEnum.getName());
        this.shader = getShader(this.shaderEnum.getName());
        this.vertices = getVertices();
        this.projection = new Mat4();
        this.projection = projection.ortho(0.0f, ProtoJ.WIDTH, ProtoJ.HEIGHT, 0.0f, -1.0f, 1.0f);
        this.index = 0;
        this.scaleRatio = 1.0f;
        this.position = new Vec2();
        this.oldPosition = new Vec2();

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
    public float getWidth() {
        return width * scaleRatio;
    }

    @Override
    public float getHeight() {
        return height * scaleRatio;
    }

    protected abstract String getLayer();

    protected abstract float getSpriteWidth();

    protected abstract float getSpriteHeight();

    protected abstract TextureEnum getTextureEnum();

    protected abstract ShaderEnum getShaderEnum();

    protected float[] getVertices() {
        float vertices[] = {
                // Position     // Texture
                0.0f, 0.0f,     0.0f, 0.0f,                                                         // Top-left
                width, 0.0f,    width / textureEnum.getWidth(), 0.0f,                               // Top-right
                width, height,  width / textureEnum.getWidth(), height / textureEnum.getHeight(),   // Bottom-right
                0.0f, height,   0.0f, height / textureEnum.getHeight(),                             // Bottom-left
        };
        return vertices;
    }

    @Override
    public void spawn() {
        LayerManager.getInstance().getLayers().get(this.layer).add(this);
    }

    @Override
    public void unspawn() {
        LayerManager.getInstance().getLayers().get(this.layer).remove(this);
    }

    @Override
    public Vec2 interpolate(double alpha) {
        position.x = oldPosition.x * ((float) alpha) + position.x * ((float) (1.0 - alpha));
        position.y = oldPosition.y * ((float) alpha) + position.y * ((float) (1.0 - alpha));
        oldPosition = new Vec2(position.x, position.y);
        return position;
    }

    @Override
    public void render(double alpha) {
        interpolate(alpha);
        this.shader.use();
        glActiveTexture(GL_TEXTURE0);
        this.texture.bind();
        glBindVertexArray(vao);
        Mat4 model = new Mat4()
                .translate(new Vec3(-getWidth() / 2.0f, -getHeight() / 2.0f, 0.0f))
                .translate(new Vec3(position.x, position.y, 0.0f));
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
