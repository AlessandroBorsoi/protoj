package com.alessandroborsoi.protoj.resource;

import lombok.Getter;

public enum ShaderEnum {
    SPRITE("sprite", "sprite.vert", "sprite.frag");

    @Getter private String name;
    @Getter private String vertex;
    @Getter private String fragment;

    ShaderEnum(String name, String vertex, String fragment) {
        this.name = name;
        this.vertex = vertex;
        this.fragment = fragment;
    }
}
