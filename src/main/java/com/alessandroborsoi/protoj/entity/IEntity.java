package com.alessandroborsoi.protoj.entity;

public interface IEntity {

    float getWidth();

    float getHeight();

    float getPosX();

    float getPosY();

    void spawn();

    void unspawn();

    void update(double timeSlice);

    void render();
}
