package com.alessandroborsoi.protoj.texture;

import com.alessandroborsoi.protoj.entity.Entity;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import lombok.extern.log4j.Log4j2;

import static com.alessandroborsoi.protoj.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

@Log4j2
public class TextureLoaderImpl implements TextureLoader {
    private static Texture[] textures = new Texture[1024];
    private static Texture[][] animations = new Texture[1024][1024];

    @Override
    public void init() {
        textures[Entity.PLANET] = loadTexture("/sprite/Stage1Layer1.png", 510, 510, 512, 512);
        textures[Entity.SPACE_TRASH_1] = loadTexture("/sprite/Stage1Layer1.png", 256, 438, 256, 256);
        textures[Entity.SPACE_TRASH_2] = loadTexture("/sprite/Stage1Layer1.png", 257, 700, 256, 256);
        textures[Entity.SPACE_TRASH_3] = loadTexture("/sprite/Stage1Layer1.png", 373, 956, 64, 64);
        textures[Entity.SPACE_TRASH_4] = loadTexture("/sprite/Stage1Layer1.png", 492, 102, 256, 128);
        textures[Entity.STAR_1] = loadTexture("/sprite/Stage1Layer1.png", 612, 1011, 8, 8);
        textures[Entity.STAR_2] = loadTexture("/sprite/Stage1Layer1.png", 649, 990, 16, 32);
        textures[Entity.STAR_3] = loadTexture("/sprite/Stage1Layer1.png", 674, 994, 16, 16);
        textures[Entity.STAR_4] = loadTexture("/sprite/Stage1Layer1.png", 716, 1004, 16, 16);
        textures[Entity.STAR_5] = loadTexture("/sprite/Stage1Layer1.png", 738, 990, 32, 32);
        textures[Entity.STAR_6] = loadTexture("/sprite/Stage1Layer1.png", 780, 994, 16, 16);
        textures[Entity.BULLET] = loadTexture("/sprite/BulletSet1.png", 32, 00, 32, 32);
        textures[Entity.ORB_BEAM] = loadTexture("/sprite/GlowBullets.png", 32, 0, 32, 32);
        textures[Entity.ENEMY_BULLET] = loadTexture("/sprite/BulletSet1.png", 0, 0, 32, 32);
        textures[Entity.ENEMY_PIECE_1] = loadTexture("/sprite/SpaceTrash.png", 00, 00, 32, 32);
        textures[Entity.ENEMY_PIECE_2] = loadTexture("/sprite/SpaceTrash.png", 32, 00, 32, 32);
        textures[Entity.ENEMY_PIECE_3] = loadTexture("/sprite/SpaceTrash.png", 64, 00, 32, 32);
        textures[Entity.ENEMY_PIECE_4] = loadTexture("/sprite/SpaceTrash.png", 96, 00, 32, 32);
        textures[Entity.ENEMY_PIECE_5] = loadTexture("/sprite/SpaceTrash.png", 00, 32, 32, 32);
        textures[Entity.ENEMY_PIECE_6] = loadTexture("/sprite/SpaceTrash.png", 32, 32, 32, 32);
        textures[Entity.ENEMY_PIECE_7] = loadTexture("/sprite/SpaceTrash.png", 64, 32, 32, 32);
        textures[Entity.ENEMY_PIECE_8] = loadTexture("/sprite/SpaceTrash.png", 96, 32, 32, 32);
        textures[Entity.BULLET_RAPID_FIRE] = loadTexture("/sprite/BulletSet1.png", 00, 64, 32, 32);
        textures[Entity.BULLET_MULTI_SHOOT] = loadTexture("/sprite/BulletSet1.png", 0, 32, 32, 32);
        textures[Entity.BULLET_BASE] = loadTexture("/sprite/BulletSet1.png", 32, 32, 32, 32);
        textures[Entity.SPACE_TRASH_5] = loadTexture("/sprite/Stage1Layer1.png", 148, 721, 64, 64);
        textures[Entity.FORCEBLAST] = loadTexture("/sprite/ForceBlast.png", 0, 0, 128, 64);
        textures[Entity.SCANLINE] = loadTexture("/sprite/Scanlines.png", 0, 0, 32, 32);
        animations[Entity.PLAYER_SHIP] = loadAnimation("/sprite/PlayerShip.png", 4, 4, 128, 128);
        animations[Entity.GREEN_ORB] = loadAnimation("/sprite/Orb1.png", 8, 2, 64, 64);
        animations[Entity.BLUE_ORB] = loadAnimation("/sprite/Orb1.png", 8, 2, 64, 64, 0, 128);
        animations[Entity.PINK_ORB] = loadAnimation("/sprite/Orb1.png", 8, 2, 64, 64, 0, 256);
        animations[Entity.RED_ORB] = loadAnimation("/sprite/Orb1.png", 8, 2, 64, 64, 0, 384);
        animations[Entity.FORCE_CHARGE] = loadAnimation("/sprite/ForceCharge.png", 8, 4, 64, 64);
        animations[Entity.PLAYER_SPEED] = loadAnimation("/sprite/PlayerSpeed.png", 8, 4, 64, 64);
        animations[Entity.EXPLOSION1] = loadAnimation("/sprite/Explosion2.png", 8, 4, 64, 64);
        animations[Entity.EXPLOSION2] = loadAnimation("/sprite/ExplosionSmall.png", 8, 4, 128, 128);
        animations[Entity.LADYBIRD] = loadAnimation("/sprite/Ladybird.png", 4, 4, 64, 64);
        animations[Entity.FIRE_BALL] = loadAnimation("/sprite/Fireball.png", 4, 4, 128, 128);
        animations[Entity.FONT] = loadAnimation("/sprite/Fonts.png", 16, 16, 32, 32);
        animations[Entity.BULLET_HIT_YELLOW] = loadAnimation("/sprite/BulletHit.png", 4, 2, 32, 32);
        animations[Entity.BULLET_HIT_GREEN] = loadAnimation("/sprite/BulletHit2.png", 4, 2, 32, 32);
        animations[Entity.BULLET_HIT_BLUE] = loadAnimation("/sprite/BulletHit3.png", 4, 2, 32, 32);
        animations[Entity.BIT_UPGRADE] = loadAnimation("/sprite/BitUpgrade.png", 4, 2, 32, 32);
        animations[Entity.BONUS_MULTI_SHOOT_ORB] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 00);
        animations[Entity.BONUS_LIGHTNING_ORB] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 64);
        animations[Entity.BONUS_RAPID_SHOOT_ORB] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 128);
        animations[Entity.BONUS_GRAVITY_ORB] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 192);
        animations[Entity.BONUS_SPEED] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 256);
        animations[Entity.BONUS_CRYSTAL_ORB] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 320);
        animations[Entity.BONUS_BOOSTER] = loadAnimation("/sprite/PowerUp1.png", 8, 1, 64, 64, 00, 384);
        animations[Entity.IMPLOSION] = loadAnimation("/sprite/Implosion.png", 8, 4, 64, 64);
        animations[Entity.MISSILE] = loadAnimation("/sprite/HomingMissile.png", 4, 4, 64, 64);
        animations[Entity.SMOKE] = loadAnimation("/sprite/SmokePuff.png", 4, 4, 32, 32);
    }

    @Override
    public Texture getTexture(int textureId) {
        return textures[textureId];
    }

    @Override
    public Texture[] getAnimation(int animationId) {
        return animations[animationId];
    }

    private Texture loadTexture(String path, int xOffSet, int yOffSet, int textureWidth, int textureHeight) {
        ByteBuffer imageBuffer;
        try {
            log.debug("Loading image: {0}", path);
            imageBuffer = ioResourceToByteBuffer(path, textureWidth * textureHeight);
        } catch (IOException e) {
            log.error("Cannot load image: {0}", path);
            throw new RuntimeException(e);
        }
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
        if (image == null) {
            log.error("Failed to load image: {0}", stbi_failure_reason());
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA8,
                textureWidth,
                textureHeight,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                image);
        return new Texture(textureId, textureHeight, textureWidth);
    }

    private Texture[] loadAnimation(String path, int cols, int rows, int textWidth, int textHeight) {
        return loadAnimation(path, cols, rows, textWidth, textHeight, 0, 0);
    }

    private Texture[] loadAnimation(String path, int cols, int rows, int textWidth, int textHeight, int xOffSet, int yOffSet) {
        Texture[] textures = new Texture[cols * rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                textures[i * cols + j] = loadTexture(
                        path,
                        j * textWidth + xOffSet,
                        i * textHeight + yOffSet,
                        textWidth,
                        textHeight);
            }
        return textures;
    }
}
