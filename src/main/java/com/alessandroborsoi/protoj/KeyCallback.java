package com.alessandroborsoi.protoj;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class KeyCallback extends GLFWKeyCallback {
    public static boolean moveForward;
    public static boolean moveBackward;
    public static boolean moveUp;
    public static boolean moveDown;
    public static boolean stopForward;
    public static boolean stopBackward;
    public static boolean stopUp;
    public static boolean stopDown;
    public static boolean forceBlastCharge;
    public static boolean fire;
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
        moveForward = isKeyDown(GLFW_KEY_RIGHT);
        moveBackward = isKeyDown(GLFW_KEY_LEFT);
        moveUp = isKeyDown(GLFW_KEY_UP);
        moveDown = isKeyDown(GLFW_KEY_DOWN);
        forceBlastCharge = isKeyDown(GLFW_KEY_SPACE);
        if (key == GLFW_KEY_RIGHT && action == GLFW_RELEASE) stopForward = true;
        if (key == GLFW_KEY_LEFT && action == GLFW_RELEASE) stopBackward = true;
        if (key == GLFW_KEY_UP && action == GLFW_RELEASE) stopUp = true;
        if (key == GLFW_KEY_DOWN && action == GLFW_RELEASE) stopDown = true;
        if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE) fire = true;

        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true);
    }

    private static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
