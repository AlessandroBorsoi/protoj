package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.io.KeyboardHandler;
import com.alessandroborsoi.protoj.io.WindowManager;
import com.alessandroborsoi.protoj.texture.TextureLoader;
import com.alessandroborsoi.protoj.texture.TextureLoaderImpl;
import com.alessandroborsoi.protoj.util.Time;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static com.alessandroborsoi.protoj.io.WindowManager.running;
import static com.alessandroborsoi.protoj.util.Time.getInstance;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

@Log4j2
public class ProtoJ {
    public static TextureLoader textureLoader;
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
        WindowManager.init();
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
            new Intro(this).play();
            loop();
        } finally {
            log.debug("Closing the window");
            WindowManager.terminate();
        }
    }

    private void loop() {
        log.debug("The loop begins...");
        while (running()) {
            time.heartBeat();
            glfwSetKeyCallback(WindowManager.getWindow(), keyCallback = new KeyboardHandler());
            if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE))
                log.debug("Space Key Pressed");
            update();
            checkCollisions();
            render();
            time.update();
        }
        log.debug("...and the loop ends");
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
        WindowManager.render();
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
