package com.alessandroborsoi.protoj;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public class LayerManager {
    public static final String BACKGROUND = "background";
    public static final String PLAYER = "player";
    public static final String ENEMIES = "enemies";
    public static final String FOREGROUND = "foreground";
    private static LayerManager layerManager;
    @Getter private Map<String, Layer> layers;

    private LayerManager() {
        layers = new LinkedHashMap<>();
        layers.put(BACKGROUND, new Layer());
        layers.put(PLAYER, new Layer());
        layers.put(ENEMIES, new Layer());
        layers.put(FOREGROUND, new Layer());
    }

    public static LayerManager getInstance() {
        if (layerManager == null)
            layerManager = new LayerManager();
        return layerManager;
    }

    public void update(double timeSlice) {
        layers.forEach((s, layer) -> layer.update(timeSlice));
    }

    public void render() {
        layers.forEach((s, layer) -> layer.render());
    }
}
