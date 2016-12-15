package com.alessandroborsoi.protoj.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

@Data
@Log4j2
public class Texture {
    private int id;
    @Getter private int width;
    @Getter private int height;
    @Getter private String name;

    private Texture(String name, int width, int height, ByteBuffer data) {
        log.debug("Loading texture {} on memory", name);
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        this.name = name;
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    public static Texture loadTexture(String name) {
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
        URL url = Texture.class.getResource(path);
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
