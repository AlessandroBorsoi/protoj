package com.alessandroborsoi.protoj.entity;

import com.alessandroborsoi.protoj.KeyCallback;
import com.alessandroborsoi.protoj.LayerManager;
import com.alessandroborsoi.protoj.ProtoJ;
import com.alessandroborsoi.protoj.resource.ShaderEnum;
import com.alessandroborsoi.protoj.resource.TextureEnum;

import glm.vec._2.Vec2;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlayerShip extends Entity {
    private static final float WIDTH = 128.0f;
    private static final float HEIGHT = 128.0f;
    private static final String LAYER = LayerManager.PLAYER;
    private static final TextureEnum TEXTURE_ENUM = TextureEnum.PLAYER_SHIP;
    private static final ShaderEnum SHADER_ENUM = ShaderEnum.REGULAR;
    private static final int BULLET = 0;
    private static final int FORCE_BLAST_1 = 1;
    private static final int FORCE_BLAST_2 = 2;
    private static final int FORCE_BLAST_3 = 3;
    private static final float SCALE_RATIO = 0.4f;
    private static final float MAX_SPEED = 600.0f;
    private static final float ACCELERATION = 500.0f;
    private static final float DECELERATION = 1000.0f;
    private static PlayerShip playerShip;
    private boolean speedOn;
    private boolean forceChargeOn;
    private float accumulator;
    private float forceBlastChargeTime;
    private int fireType;
    @Setter private int powerUpType;

    public static PlayerShip getInstance() {
        if (playerShip == null) {
            playerShip = new PlayerShip();
        }
        return playerShip;
    }

    private PlayerShip() {
        position.x = ProtoJ.WIDTH / 4;
        position.y = ProtoJ.HEIGHT / 2;
        oldPosition = position;
        scaleRatio = SCALE_RATIO;
        index = 8;
        powerUpType = -1;
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
        oldPosition = new Vec2(position);
        if (KeyCallback.moveForward) {
            if (!speedOn) {
                speedOn = true;
                new PlayerSpeed().spawn();
            }
            velocity.x = velocity.x < MAX_SPEED ? velocity.x += ACCELERATION * dt : velocity.x;
        }
        if (KeyCallback.stopForward) {
            velocity.x -= DECELERATION * dt;
            if (velocity.x < 0.0f) {
                velocity.x = 0.0f;
                KeyCallback.stopForward = false;
                speedOn = false;
            }
        }
        if (KeyCallback.moveBackward) {
            velocity.x = velocity.x > -MAX_SPEED ? velocity.x -= ACCELERATION * dt : velocity.x;
        }
        if (KeyCallback.stopBackward) {
            velocity.x += DECELERATION * dt;
            if (velocity.x > 0.0f) {
                velocity.x = 0.0f;
                KeyCallback.stopBackward = false;
            }
        }
        if (KeyCallback.moveUp) {
            velocity.y = velocity.y > -MAX_SPEED ? velocity.y -= ACCELERATION * dt : velocity.y;
            accumulator += dt * 6.0f;
            if (accumulator > 1.0 && index < 15) {
                ++index;
                accumulator = 0.0f;
            }
        }
        if (KeyCallback.stopUp) {
            if (index > 9) --index;
            velocity.y += DECELERATION * dt;
            if (velocity.y > 0.0f) {
                velocity.y = 0.0f;
                KeyCallback.stopUp = false;
                index = 8;
            }
        }
        if (KeyCallback.moveDown) {
            velocity.y = velocity.y < MAX_SPEED ? velocity.y += ACCELERATION * dt : velocity.y;
            accumulator += dt * 6.0f;
            if (accumulator > 1.0 && index > 0) {
                --index;
                accumulator = 0.0f;
            }
        }
        if (KeyCallback.stopDown) {
            if (index < 8) ++index;
            velocity.y -= DECELERATION * dt;
            if (velocity.y < 0.0f) {
                velocity.y = 0.0f;
                KeyCallback.stopDown = false;
                index = 8;
            }
        }
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
        if (position.x > ProtoJ.WIDTH) position.x = ProtoJ.WIDTH;
        if (position.x < 0.0f) position.x = 0.0f;
        if (position.y > ProtoJ.HEIGHT) position.y = ProtoJ.HEIGHT;
        if (position.y < 0.0f) position.y = 0.0f;

        if (KeyCallback.forceBlastCharge) {
            forceBlastChargeTime += dt;
            if (forceBlastChargeTime < 0.5f) {
                fireType = BULLET;
            } else if (forceBlastChargeTime < 1.0f) {
                if (!forceChargeOn) {
                    forceChargeOn = true;
                    new ForceCharge().spawn();
                }
                fireType = FORCE_BLAST_1;
            } else if (forceBlastChargeTime < 2.0f) {
                fireType = FORCE_BLAST_2;
            } else {
                fireType = FORCE_BLAST_3;
            }
        }

        if (KeyCallback.fire) {
            if (fireType == BULLET) {
                new Bullet(new Vec2(position.x, position.y)).spawn();
            } else {
                new ForceBlast(new Vec2(position.x, position.y), fireType).spawn();
            }
            KeyCallback.fire = false;
            forceBlastChargeTime = 0.0f;
            forceChargeOn = false;
        }
    }
}
