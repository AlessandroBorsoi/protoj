package com.alessandroborsoi.protoj.resource;

import lombok.Getter;

public enum ShaderEnum {
    REGULAR("regular"),
    IRREGULAR("irregular");

    @Getter private final String name;

    ShaderEnum(String name) {
        this.name = name;
    }
}
