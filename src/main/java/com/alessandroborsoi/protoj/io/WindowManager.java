package com.alessandroborsoi.protoj.io;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
public class WindowManager {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    @Getter private static long window;

    private WindowManager() {
    }

    public static long init() {
        if (window == NULL) {
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit()) {
                log.error("Unable to initialize GLFW");
                throw new IllegalStateException("Unable to initialize GLFW");
            }
            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
            window = glfwCreateWindow(WIDTH, HEIGHT, "ProtoJ", NULL, NULL);
            if (window == NULL) {
                log.error("Failed to create the GLFW window");
                throw new RuntimeException("Failed to create the GLFW window");
            }
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            glfwShowWindow(window);
        }
        return window;
    }

    public static boolean running() {
        return !glfwWindowShouldClose(window);
    }

    public static void terminate() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void render() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
}
