package com.alessandroborsoi.protoj;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public class LayerManager {
    public static final String BACKGROUND = "background";
    public static final String PLAYER = "player";
    public static final String FX = "fx";
    public static final String POWER_UP = "powerUp";
    public static final String BULLETS = "bullets";
    public static final String ENEMIES = "enemies";
    public static final String FOREGROUND = "foreground";
    public static final String TEXT = "text";
    private static LayerManager layerManager;
    @Getter private Map<String, Layer> layers;

    private LayerManager() {
        layers = new LinkedHashMap<>();
        layers.put(BACKGROUND, new Layer());
        layers.put(PLAYER, new Layer());
        layers.put(FX, new Layer());
        layers.put(POWER_UP, new Layer());
        layers.put(BULLETS, new Layer());
        layers.put(ENEMIES, new Layer());
        layers.put(FOREGROUND, new Layer());
        layers.put(TEXT, new Layer());
    }

    public static LayerManager getInstance() {
        if (layerManager == null)
            layerManager = new LayerManager();
        return layerManager;
    }

    public void update(float dt) {
        layers.forEach((s, layer) -> layer.update(dt));
    }

    public void render(float alpha) {
        layers.forEach((s, layer) -> layer.render(alpha));
    }
}
