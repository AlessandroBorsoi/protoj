package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.Game;
import com.alessandroborsoi.protoj.KeyCallback;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class PlayerShip extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.PLAYER_SHIP;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.ANIMATED;
    private static final float SCALE_RATIO = 0.5f;
    private static final float SPEED = 600.0f;
    private static PlayerShip playerShip;

    public static PlayerShip getInstance() {
        if (playerShip == null) {
            playerShip = new PlayerShip();
        }
        return playerShip;
    }

    private PlayerShip() {
        posX = 0.0f;
        posY = 0.0f;
        scaleRatio = SCALE_RATIO;
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
        if (KeyCallback.isKeyDown(GLFW_KEY_RIGHT)) {
            posX = posX < 800.0f ? posX += (SPEED * timeSlice) : posX;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_LEFT)) {
            posX = posX > 0.0f ? posX -= (SPEED * timeSlice) : posX;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_UP)) {
            posY = posY > 0.0f ? posY -= (SPEED * timeSlice) : posY;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_DOWN)) {
            posY = posY < 600.0f ? posY += (SPEED * timeSlice) : posY;
        }
        if (KeyCallback.isKeyDown(GLFW_KEY_SPACE)) {
            Game.getInstance().getPlayer().add(new Bullet(this.posX, this.posY));
        }
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
