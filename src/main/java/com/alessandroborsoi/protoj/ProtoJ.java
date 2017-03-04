package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Text;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import glm.vec._2.Vec2;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
public class ProtoJ {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static long window;
    private KeyCallback keyCallback;
    private Game protoJ;
    private double fps;

    public static void main(String args[]) {
        new ProtoJ().run();
    }

    private void run() {
        try {
            init();
            loop();
        } finally {
            log.debug("Terminating all...");
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            glfwTerminate();
            GL.destroy();
        }
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            log.error("Unable to initialize GLFW");
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = glfwCreateWindow(WIDTH, HEIGHT, "ProtoJ", NULL, NULL);
        if (window == NULL) {
            log.error("Failed to create the GLFW window");
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwMakeContextCurrent(window);
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
        glfwSwapInterval(0);
        glfwSetKeyCallback(window, keyCallback = new KeyCallback());
        glfwShowWindow(window);
        GL.createCapabilities();
        log.debug("OpenGL: {}", glGetString(GL_VERSION));
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(window, width, height);
        glViewport(0, 0, width.get(0), height.get(0));
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        protoJ = Game.getInstance();
        protoJ.init();
    }

    private void loop() {
        int frames = 0;
        double framesTime = 0;
        Text score = new Text("Score: 0", new Vec2(20.0f, 20.0f));
        Text fpsText = new Text("FPS: 0", new Vec2(700.0f, 20.0f));
        Text entities = new Text("Entities: 0", new Vec2(20.0f, 560.0f));
        score.spawn();
        fpsText.spawn();
        entities.spawn();
        double updateRate = 25.0;
        double dt = 1.0 / updateRate;
        double accumulator = 0.0f;
        double lastTime = glfwGetTime();
        while (!glfwWindowShouldClose(window)) {
            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTime;
            lastTime = currentTime;
            accumulator += deltaTime;
            framesTime += deltaTime;
            if (accumulator > 0.2f)
                accumulator = 0.2f;
            while (accumulator > dt) {
                protoJ.update(dt);
                accumulator -= dt;
                score.setText("Score: " + protoJ.getScore());
                fpsText.setText("FPS: " + ((int) fps));
                entities.setText("Entities: " + Layer.entitiesCount);
            }
            double alpha = accumulator / dt;
            protoJ.render(alpha);
            glfwSwapBuffers(window);
            glfwPollEvents();
            frames++;
            if (frames == 50) {
                fps = frames / framesTime;
                frames = 0;
                framesTime = 0.0;
            }
            if (glGetError() != 0) {
                log.error("glGetError: {}", glGetError());
                throw new RuntimeException("glGetError: " + glGetError());
            }
        }
    }
}
