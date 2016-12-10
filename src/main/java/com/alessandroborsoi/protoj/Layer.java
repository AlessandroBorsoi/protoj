package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Entity> entities = new ArrayList<>();

    public void add(Entity e) {
        entities.add(e);
    }

    public void remove(Entity e) {
        entities.remove(e);
    }

    public void render() {
        entities.forEach(Entity::draw);
    }

    public void update() {
        entities.forEach(entity -> {
            entity.updateTick();
            entity.update();
        });
    }
}
