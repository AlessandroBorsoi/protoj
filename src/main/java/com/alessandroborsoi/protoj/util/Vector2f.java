package com.alessandroborsoi.protoj.util;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import lombok.Getter;

/**
 * Based on: https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/math/Vector2f.java
 */
public class Vector2f {
    @Getter private float x;
    @Getter private float y;

    public Vector2f() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        float length = length();
        return divide(length);
    }

    public Vector2f add(Vector2f other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        return new Vector2f(x, y);
    }

    public Vector2f negate() {
        return scale(-1f);
    }

    public Vector2f subtract(Vector2f other) {
        return this.add(other.negate());
    }

    public Vector2f scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        return new Vector2f(x, y);
    }

    public Vector2f divide(float scalar) {
        return scale(1f / scalar);
    }

    public float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2f lerp(Vector2f other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    public DoubleBuffer getBuffer() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(2);
        buffer.put(x).put(y);
        buffer.flip();
        return buffer;
    }
}
