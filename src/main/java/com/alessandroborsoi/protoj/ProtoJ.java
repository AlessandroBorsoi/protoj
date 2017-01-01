package com.alessandroborsoi.protoj;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
public class ProtoJ {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private long window;

    public static void main(String[] args) {
        new ProtoJ().run();
    }

    private void run() {
        try {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            GLFWErrorCallback.createPrint(System.err).set();

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            if (!glfwInit())
                throw new IllegalStateException("Unable to initialize GLFW");

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

            // Create the window
            window = glfwCreateWindow(WIDTH, HEIGHT, "ProtoJ", NULL, NULL);
            if (window == NULL)
                throw new RuntimeException("Failed to create the GLFW window");

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center our window
            glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

            // Make the OpenGL context current
            glfwMakeContextCurrent(window);

            // Make the window visible
            glfwShowWindow(window);

            while (!glfwWindowShouldClose(window)) {
                GL.createCapabilities();
                // Render
                // Clear the colorbuffer
                glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
                glClear(GL_COLOR_BUFFER_BIT);

                glColor3f(1, 1, 1);
                glBegin(GL_LINES);
                    glVertex2f(0.0f, 0.0f);
                    glVertex2f(1.0f, 1.0f);
                glEnd();
                glBegin(GL_QUADS);
                    glVertex2f(0.0f, 0.0f);
                    glVertex2f(-1.0f, 0.0f);
                    glVertex2f(-1.0f, -1.0f);
                    glVertex2f(0.0f, -1.0f);
                glEnd();

                // Swap the screen buffers
                glfwSwapBuffers(window);
                // Check if any events have been activiated (key pressed, mouse moved etc.) and call corresponding response functions
                glfwPollEvents();
            }

        } finally {
            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            glfwTerminate();
        }
    }
}
