package com.alessandroborsoi.protoj.resource;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

@Log4j2
public class ResourceManager {
    private static Map<String, Texture> textures = new HashMap<>();

    public static void init() {
        EnumSet.allOf(TextureEnum.class).forEach(textureEnum -> loadTexture(textureEnum.toString()));
    }

    public static void loadTexture(String name) {
        textures.put(name, loadTextureFromFile(name));
    }

    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    private static Texture loadTextureFromFile(String name) {
        String path = getTexturePath(name);
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        log.debug("Loading image: {}", path);
        ByteBuffer image = stbi_load(path, w, h, comp, 4);
        if (image == null) {
            log.error("Failed to load a texture file: {}", path);
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + stbi_failure_reason());
        }
        int width = w.get();
        int height = h.get();
        return new Texture(name, width, height, image);
    }

    private static String getTexturePath(String name) {
        String path = "/texture/" + name + ".png";
        URL url = ResourceManager.class.getResource(path);
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            log.error("Wrong URL: {}", path);
            throw new RuntimeException("Wrong URL: " + path);
        }
        return file.toString();
    }
}
