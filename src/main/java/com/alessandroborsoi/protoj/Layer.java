package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    List<Entity> entities = new ArrayList<>();

    public void add(Entity entity) {
        this.entities.add(entity);
    }

    public void remove(Entity entity) {
        this.entities.remove(entity);
    }

    public void update(double timeSlice) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(timeSlice);
        }
    }

    public void render() {
        entities.forEach(Entity::render);
    }
}
