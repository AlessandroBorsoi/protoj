package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.Layer;
import com.alessandroborsoi.protoj.io.WindowManager;
import com.alessandroborsoi.protoj.texture.Coordinates;
import com.alessandroborsoi.protoj.texture.Sprite;
import com.alessandroborsoi.protoj.texture.Texture;
import com.alessandroborsoi.protoj.texture.TextureLoader;
import com.alessandroborsoi.protoj.util.Time;
import com.alessandroborsoi.protoj.util.Vector2f;

import org.lwjgl.opengl.GL11;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Entity implements IEntity {
    protected Layer layer = null;
    protected float rotation = 0;
    protected float textureUp = 1;
    protected float textureDown = 0;
    protected float textureLeft = 0;
    protected float textureRight = 1;
    protected int original_width;
    protected int original_height;
    protected float rotationSpeed;
    protected Texture texture;
    protected float width;
    protected float height;
    protected float tick;
    protected Vector2f position;
    protected Vector2f speed;
    protected Sprite sprite;
    protected int textureId;
    protected String textureName;

    public Entity() {
        this.sprite = getSprite();
        this.textureName = getTextureName();
        this.textureId = TextureLoader.getInstance().getTextureId(this.textureName);
    }

    protected abstract Sprite getSprite();

    protected abstract String getTextureName();

    @Override
    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.x, position.y, 0);
        GL11.glRotatef(this.rotation, 0f, 0f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(textureRight, textureUp); // Upper right
            GL11.glVertex2f(width, -height);

            GL11.glTexCoord2f(textureLeft, textureUp); // Upper left
            GL11.glVertex2f(-width, -height);

            GL11.glTexCoord2f(textureLeft, textureDown); // Lower left
            GL11.glVertex2f(-width, height);

            GL11.glTexCoord2f(textureRight, textureDown); // Lower right
            GL11.glVertex2f(width, height);
        }
        GL11.glEnd();
    }

    public void update() {
        interpolate(position, speed);
        this.rotation += rotationSpeed * tick;
        if (this.position.x - this.width > (WindowManager.WIDTH / 2) ||
                this.position.x + this.width < -(WindowManager.WIDTH / 2)) {
            unSpawn();
            log.debug(this.getClass().getName() + " died");
            return;
        }
    }

    protected void unSpawn() {
        layer.remove(this);
    }

    protected Vector2f interpolate(Vector2f old_position, Vector2f speed) {
        old_position.x = old_position.x + tick * speed.x;
        old_position.y = old_position.y + tick * speed.y;
        return old_position;
    }

    public void updateTick() {
        tick = Time.getInstance().getTick();
    }

    public void spawn(Vector2f position, Vector2f speed, Layer layer) {
        this.position = position;
        this.speed = speed;
        this.layer = layer;
        this.layer.add(this);
    }

    Coordinates[] getCoordinates(Sprite sprite) {
        int rows = sprite.getRows();
        int cols = sprite.getCols();
        int x = sprite.getX();
        int w = sprite.getW();
        int y = sprite.getY();
        int h = sprite.getH();
        Coordinates[] sprites = new Coordinates[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coordinates coordinates = new Coordinates();
                // Upper left
                coordinates.getCoordinates()[0].x = x + w * j;
                coordinates.getCoordinates()[0].y = y + h * i;
                // Lower left
                coordinates.getCoordinates()[1].x = x + w * j;
                coordinates.getCoordinates()[1].y = y + h * (i + 1);
                // Lower right
                coordinates.getCoordinates()[2].x = x + w * (j + 1);
                coordinates.getCoordinates()[2].y = y + h * (i + 1);
                // Upper right
                coordinates.getCoordinates()[3].x = x + w * (j + 1);
                coordinates.getCoordinates()[3].y = y + h * i;
                sprites[i * cols + j] = coordinates;
            }
        }
        return sprites;
    }
}
