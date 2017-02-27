package com.alessandroborsoi.protoj.resource;

public enum ShaderEnum {
    ANIMATED("animated"),
    SPRITE("sprite");

    private String name;

    ShaderEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
