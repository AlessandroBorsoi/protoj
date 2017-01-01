package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.impl.Planet;
import com.alessandroborsoi.protoj.entity.impl.PlayerShip;
import com.alessandroborsoi.protoj.io.KeyboardHandler;
import com.alessandroborsoi.protoj.io.WindowManager;
import com.alessandroborsoi.protoj.texture.TextureLoader;
import com.alessandroborsoi.protoj.util.Time;
import com.alessandroborsoi.protoj.util.Vector2f;

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
import static org.lwjgl.opengl.GL11.GL_VERSION;
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
    @Getter
    private static Layer background = new Layer();
    private static Layer foreground = new Layer();
    private static Layer text = new Layer();
    private static PlayerShip player = null;

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
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        log.debug("OpenGL: {}", GL11.glGetString(GL_VERSION));
//        GL11.glClearDepth(1.0f);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDepthMask(false);
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        GL11.glOrtho(0, WindowManager.WIDTH, 0, WindowManager.HEIGHT, 1, -1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        textureLoader = TextureLoader.getInstance();
        log.debug("initGL() ends");
    }

    private void run() {
        try {
//            new Intro(this).play();
//            addBasicEntries();
            loop();
        } finally {
            log.debug("Terminating all...");
            WindowManager.terminate();
            GL.destroy();
        }
    }

    private void addBasicEntries() {
        Planet planet = new Planet();
        planet.spawn(new Vector2f(-150f, -100f), new Vector2f(), bullets);
//        player = new PlayerShip();
//        player.spawn(new Vector2f(-150f, -100f), new Vector2f(), bullets);
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
            time.update();
            render();
        }
        log.debug("...and the loop ends");
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

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        bullets.render();
        enemies.render();
        fx.render();
        background.render();
        bonus.render();
        foreground.render();
        text.render();

        // set the color of the quad (R,G,B,A)
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(0.0f, 0.0f);
            GL11.glVertex2f(1.0f, 0.0f);
            GL11.glVertex2f(1.0f, 1.0f);
            GL11.glVertex2f(0.0f, 1.0f);
        GL11.glEnd();
        WindowManager.render();
    }

    private void checkCollisions() {

    }
}
