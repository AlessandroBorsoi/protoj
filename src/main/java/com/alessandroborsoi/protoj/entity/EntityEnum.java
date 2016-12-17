package com.alessandroborsoi.protoj.entity;

public enum EntityEnum {
    FONTS("Fonts"),
    PLAYER_SHIP("PlayerShip"),
    STAGE1_LAYER1("Stage1Layer1");

    private String name;

    EntityEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}
