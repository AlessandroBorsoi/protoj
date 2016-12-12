package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.input.KeyboardHandler;
import com.alessandroborsoi.protoj.texture.TextureLoader;
import com.alessandroborsoi.protoj.texture.TextureLoaderImpl;
import com.alessandroborsoi.protoj.util.Time;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static com.alessandroborsoi.protoj.util.Time.getInstance;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
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
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
public class ProtoJ {
    public static TextureLoader textureLoader;
    public long window;
    private Time time;
    private GLFWKeyCallback keyCallback;
    private static Layer bullets = new Layer();
    private static Layer enemies = new Layer();
    private static Layer fx = new Layer();
    private static Layer bonus = new Layer();
    @Getter private static Layer background = new Layer();
    private static Layer foreground = new Layer();
    private static Layer text = new Layer();


    public static void main(String args[]) {
        new ProtoJ().run();
    }

    private ProtoJ() {
        init();
        initGL();
    }

    private void init() {
        log.debug("init() starts");
        time = getInstance();
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        int width = 800;
        int height = 600;
        window = glfwCreateWindow(width, height, "ProtoJ", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        log.debug("init() ends");
    }

    private void initGL() {
        log.debug("initGL() starts");
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        textureLoader = new TextureLoaderImpl();
        textureLoader.init();
        log.debug("initGL() ends");
    }

    private void run() {
        try {
            log.info("The loop begins...");
            loop();
            log.info("...and the loop ends");
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            time.heartBeat();
            glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
            if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE))
                log.debug("Space Key Pressed");
            update();
            checkCollisions();
            render();
            time.update();
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        bullets.render();
        enemies.render();
        fx.render();
        background.render();
        bonus.render();
        foreground.render();
        text.render();
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void update() {
        bullets.update();
        enemies.update();
        fx.update();
        background.update();
        bonus.update();
        foreground.update();
        text.update();
    }

    private void checkCollisions() {

    }
}
