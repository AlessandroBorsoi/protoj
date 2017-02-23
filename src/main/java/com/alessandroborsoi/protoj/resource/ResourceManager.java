package com.alessandroborsoi.protoj.resource;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

@Log4j2
public class ResourceManager {
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Shader> shaders = new HashMap<>();

    public static void init() {
        EnumSet.allOf(TextureEnum.class).forEach(textureEnum -> loadTexture(textureEnum.toString()));
        EnumSet.allOf(ShaderEnum.class).forEach(shaderEnum -> loadShader(shaderEnum.toString()));
    }

    public static void loadTexture(String name) {
        textures.put(name, loadTextureFromFile(name));
    }

    public static void loadShader(String name) {
        shaders.put(name, loadShaderFromFile(name));
    }

    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    public static Shader getShader(String name) {
        return shaders.get(name);
    }

    private static Texture loadTextureFromFile(String name) {
        String path = getResourcePath("/texture/" + name + ".png");
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        log.debug("Loading image: {}", path);
        ByteBuffer image = stbi_load(path, w, h, comp, 4);
        if (image == null) {
            log.error("Failed to load the texture file: {}", path);
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + stbi_failure_reason());
        }
        return new Texture(name, w.get(), h.get(), image);
    }

    private static Shader loadShaderFromFile(String name) {
        String vertexPath = getResourcePath("/shader/" + name + ".vert");
        String fragmentPath = getResourcePath("/shader/" + name + ".frag");
        String vertexSource;
        String fragmentSource;
        try {
            vertexSource = new String(Files.readAllBytes(Paths.get(vertexPath)));
        } catch (IOException e) {
            log.error("Failed to load the vertex shader from {}", vertexPath);
            throw new RuntimeException("Failed to load shader from file!");
        }
        try {
            fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentPath)));
        } catch (IOException e) {
            log.error("Failed to load the fragment shader from {}", fragmentPath);
            throw new RuntimeException("Failed to load shader from file!");
        }
        return new Shader(vertexSource, fragmentSource);
    }

    private static String getResourcePath(String resource) {
        URL url = ResourceManager.class.getResource(resource);
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            log.error("Wrong URI: {}", resource);
            throw new RuntimeException("Wrong URI: " + resource);
        }
        return file.toString();
    }
}
