package com.alessandroborsoi.protoj.resource;

import lombok.Getter;

public enum TextureEnum {
    BULLET_SET1("BulletSet1", 64.0f, 128.0f, 4, 2),
    EXPLOSION_SMALL("ExplosionSmall", 1024.0f, 512.0f, 4, 8),
    FONTS("Fonts", 512.0f, 512.0f, 16, 16),
    LADYBIRD("Ladybird", 256.0f, 256.0f, 4, 4),
    PLAYER_SHIP("PlayerShip", 512.0f, 512.0f, 4, 4),
    PLAYER_SPEED("PlayerSpeed", 512.0f, 256.0f, 4, 8),
    STAGE1_LAYER1("Stage1Layer1", 1024.0f, 1024.0f, 0, 0);

    @Getter private String name;
    @Getter private float width;
    @Getter private float height;
    @Getter private int rows;
    @Getter private int columns;

    TextureEnum(String name, float width, float height, int rows, int columns) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
    }
}
