package com.alessandroborsoi.protoj.resource;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

@Log4j2
public class Shader {
    private int id;

    public Shader use() {
        glUseProgram(this.id);
        return this;
    }

    public Shader(String vertexSource, String fragmentSource) {
        // Vertex shader
        int vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);
        checkShaderErrors(vertexId);
        // Fragment shader
        int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);
        checkShaderErrors(fragmentId);
        // Shader program
        this.id = glCreateProgram();
        glAttachShader(this.id, vertexId);
        glAttachShader(this.id, fragmentId);
        glLinkProgram(this.id);
        checkProgramErrors();
        glDeleteShader(vertexId);
        glDeleteShader(fragmentId);
    }

    private void checkShaderErrors(int shaderId) {
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            log.error(glGetShaderInfoLog(shaderId));
            log.error("Shader id {} compilation error", shaderId);
            throw new RuntimeException("Shader compilation error");
        }
        log.debug("Shader id {} compile success", shaderId);
    }

    private void checkProgramErrors() {
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            log.error(glGetProgramInfoLog(this.id));
            log.error("Program id {} link error", this.id);
            throw new RuntimeException("Program link error");
        }
        log.debug("Program id {} link success", this.id);
    }
}
