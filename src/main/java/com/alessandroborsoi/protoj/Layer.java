package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    public static int entitiesCount = 0;
    List<IEntity> entities = new ArrayList<>();

    public void add(IEntity entity) {
        this.entities.add(entity);
        ++entitiesCount;
    }

    public void remove(IEntity entity) {
        this.entities.remove(entity);
        --entitiesCount;
    }

    public void update(double timeSlice) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(timeSlice);
        }
    }

    public void render() {
        entities.forEach(IEntity::render);
    }
}
