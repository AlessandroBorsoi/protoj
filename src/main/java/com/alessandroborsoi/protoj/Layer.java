package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<AbstractEntity> entities = new ArrayList<>();

    public void add(AbstractEntity e) {
        entities.add(e);
    }

    public void remove(AbstractEntity e) {
        entities.remove(e);
    }

    public void render() {
    }

    public void update() {
    }
}
