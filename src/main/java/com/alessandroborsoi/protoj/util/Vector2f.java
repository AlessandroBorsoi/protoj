package com.alessandroborsoi.protoj.util;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

/**
 * Based on: https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/math/Vector2f.java
 */
public class Vector2f {
    private double x;
    private double y;

    public Vector2f() {
        this.x = 0.0d;
        this.y = 0.0d;
    }

    public Vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        double length = length();
        return divide(length);
    }

    public Vector2f add(Vector2f other) {
        double x = this.x + other.x;
        double y = this.y + other.y;
        return new Vector2f(x, y);
    }

    public Vector2f negate() {
        return scale(-1d);
    }

    public Vector2f subtract(Vector2f other) {
        return this.add(other.negate());
    }

    public Vector2f scale(double scalar) {
        double x = this.x * scalar;
        double y = this.y * scalar;
        return new Vector2f(x, y);
    }

    public Vector2f divide(double scalar) {
        return scale(1d / scalar);
    }

    public double dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2f lerp(Vector2f other, double alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    public DoubleBuffer getBuffer() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(2);
        buffer.put(x).put(y);
        buffer.flip();
        return buffer;
    }
}
