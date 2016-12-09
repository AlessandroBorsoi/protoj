package com.alessandroborsoi.protoj.util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private static Time time;
    private double lastTime;
    private double deltas;
    private double fps;
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
            deltas += getCurrentTimeMillis() - lastTime;
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
        pause = !pause;
    }

    private double getCurrentTimeMillis() {
        return glfwGetTime() * 1000;
    }
}
