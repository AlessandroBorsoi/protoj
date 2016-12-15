package com.alessandroborsoi.protoj.texture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureLoader {
    private static TextureLoader textureLoader;
    private final List<String> textures;
    private Map<String, Texture> textureMap;

    private TextureLoader() {
        textureMap = new HashMap<>();
        String textureArray[] = {
                "PlayerShip",
                "Stage1Layer1"
        };
        this.textures = Arrays.asList(textureArray);
    }

    public static TextureLoader getInstance() {
        if (textureLoader == null) {
            textureLoader = new TextureLoader();
            textureLoader.load();
        }
        return textureLoader;
    }

    private void load() {
        this.textures.forEach(name -> {
            Texture texture = Texture.loadTexture(name);
            this.textureMap.put(texture.getName(), texture);
        });
    }

}
