package com.alessandroborsoi.protoj.entity;

import glm.vec._2.Vec2;

public interface IEntity {

    float getWidth();

    float getHeight();

    Vec2 getPosition();

    void spawn();

    void unspawn();

    void update(double dt);

    void render(double alpha);
}
