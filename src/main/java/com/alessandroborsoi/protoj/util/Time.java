package com.alessandroborsoi.protoj.util;

import lombok.Getter;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private static Time time;
    private float lastTime;
    @Getter private float tick;
    private float deltas;
    private float fps;
    private int frames;
    private boolean pause;

    public static Time getInstance() {
        if (time == null) {
            time = new Time();
        }
        return time;
    }

    private Time() {
        lastTime = getCurrentTimeMillis();
    }

    public void heartBeat() {
        if (!pause) {
            tick = getCurrentTimeMillis() - lastTime;
            deltas += tick;
            lastTime = getCurrentTimeMillis();
        }
    }

    public void update() {
        if (!pause) {
            frames++;
            if (frames == 50) {
                fps = frames / deltas;
                frames = 0;
                deltas = 0;
            }
        }
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        pause = false;
    }

    private float getCurrentTimeMillis() {
        return (float) (glfwGetTime() * 1000);
    }
}
