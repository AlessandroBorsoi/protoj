package com.alessandroborsoi.protoj.resource;

public enum TextureEnum {
    PLAYER_SHIP("PlayerShip"),
    STAGE1_LAYER1("Stage1Layer1");

    private String name;

    TextureEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}
