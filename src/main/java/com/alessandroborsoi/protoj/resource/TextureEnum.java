package com.alessandroborsoi.protoj.resource;

import lombok.Getter;

public enum TextureEnum {
    BULLET_SET1("BulletSet1", 64.0f, 128.0f, 4, 2),
    EXPLOSION_SMALL("ExplosionSmall", 1024.0f, 512.0f, 4, 8),
    FONTS("Fonts", 512.0f, 512.0f, 16, 16),
    FORCE_BLAST("ForceBlast", 128.0f, 64.0f, 1, 1),
    FORCE_CHARGE("ForceCharge", 512.0f, 256.0f, 4, 8),
    IMPLOSION("Implosion", 512.0f, 256.0f, 4, 8),
    LADYBIRD("Ladybird", 256.0f, 256.0f, 4, 4),
    PLAYER_SHIP("PlayerShip", 512.0f, 512.0f, 4, 4),
    PLAYER_SPEED("PlayerSpeed", 512.0f, 256.0f, 4, 8),
    POWER_UP("PowerUp1", 512.0f, 512.0f, 8, 8),
    STAGE1_LAYER1("Stage1Layer1", 1024.0f, 1024.0f, 0, 0);

    @Getter private final String name;
    @Getter private final float width;
    @Getter private final float height;
    @Getter private final int rows;
    @Getter private final int columns;

    TextureEnum(String name, float width, float height, int rows, int columns) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
    }
}
