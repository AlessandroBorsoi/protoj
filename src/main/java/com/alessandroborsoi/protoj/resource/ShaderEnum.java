package com.alessandroborsoi.protoj.resource;

import lombok.Getter;

public enum ShaderEnum {
    ANIMATED("animated"),
    SPRITE("sprite");

    @Getter private String name;

    ShaderEnum(String name) {
        this.name = name;
    }
}
