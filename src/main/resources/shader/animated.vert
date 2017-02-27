#version 330 core
layout (location = 0) in vec4 vertex; // <vec2 position, vec2 texCoords>

out vec2 TexCoords;

uniform mat4 model;
uniform int index;
uniform int offset;

void main()
{
    TexCoords = vec2(vertex.z + (index / offset) / float(offset), vertex.w + (index % offset) / float(offset));
    gl_Position = model * vec4(vertex.xy, 0.0, 1.0);
}