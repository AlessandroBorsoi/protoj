package com.alessandroborsoi.protoj.resource;

import glm.mat._4.Mat4;
import lombok.extern.log4j.Log4j2;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

@Log4j2
public class Shader {
    private final int id;

    public void use() {
        glUseProgram(this.id);
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

    public void setInteger(String name, int value) {
        glUniform1i(glGetUniformLocation(this.id, name), value);
    }

    public void setMatrix4(String name, Mat4 matrix) {
        glUniformMatrix4fv(glGetUniformLocation(this.id, name), false, matrix.toFa_());
    }
}
