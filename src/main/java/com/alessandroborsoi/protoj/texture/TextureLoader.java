package com.alessandroborsoi.protoj.texture;

import com.alessandroborsoi.protoj.entity.EntityEnum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class TextureLoader {
    private static TextureLoader textureLoader;
    @Getter
    private Map<String, Texture> textureMap;

    private TextureLoader() {
        textureMap = new HashMap<>();
    }

    public static TextureLoader getInstance() {
        if (textureLoader == null) {
            textureLoader = new TextureLoader();
            textureLoader.load();
        }
        return textureLoader;
    }

    private void load() {
        EnumSet.allOf(EntityEnum.class).forEach(name -> {
            Texture texture = Texture.loadTexture(name.toString());
            this.textureMap.put(texture.getName(), texture);
        });
    }

    public int getTextureId(String name) {
        return this.textureMap.get(name).getId();
    }
}
