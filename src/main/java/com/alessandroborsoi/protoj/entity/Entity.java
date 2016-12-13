package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.Layer;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.io.WindowManager;
import com.alessandroborsoi.protoj.texture.Texture;
import com.alessandroborsoi.protoj.util.Time;
import com.alessandroborsoi.protoj.util.Vector2f;

import org.lwjgl.opengl.GL11;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Entity implements IEntity {
    protected int type;
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
    private float ratio = 1.0f;
    @Getter
    private Vector2f position;
    private Vector2f speed;

    @Override
    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(position.getX(), position.getY(), 0);
        GL11.glRotatef(this.rotation, 0f, 0f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getId());
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

    @Override
    public void update() {
        interpolate(position, speed);
        this.rotation += rotationSpeed * tick;
        if (this.position.getX() - this.width > (WindowManager.WIDTH / 2) ||
                this.position.getX() + this.width < -(WindowManager.WIDTH / 2)) {
            unSpawn();
            log.debug(this.getClass().getName() + " died");
            return;
        }
    }

    protected void unSpawn() {
        layer.remove(this);
    }

    protected Vector2f interpolate(Vector2f old_position, Vector2f speed) {
        old_position.setX(old_position.getX() + tick * speed.getX());
        old_position.setY(old_position.getY() + tick * speed.getY());
        return old_position;
    }

    @Override
    public void updateTick() {
        tick = Time.getInstance().getTick();
    }

    public void init() {
        this.texture = ProtoJ.textureLoader.getTexture(this.type);
        this.original_width = this.texture.getWidth();
        this.original_height = this.texture.getHeight();
        this.width = this.original_width * ratio;
        this.height = this.original_height * ratio;
    }

    public void spawn(Vector2f position, Vector2f speed, Layer layer) {
        this.position = position;
        this.speed = speed;
        this.layer = layer;
        this.layer.add(this);
    }

    public void spawn(Vector2f position, Vector2f speed, float rotationSpeed, Layer layer) {
        spawn(position, speed, layer);
        this.rotationSpeed = rotationSpeed;
    }

    protected void setRatio(float newRatio) {
        this.ratio = newRatio;
        this.width = this.original_width * ratio;
        this.height = this.original_height * ratio;
    }
}
