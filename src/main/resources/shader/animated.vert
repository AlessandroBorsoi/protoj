#version 330 core
layout (location = 0) in vec4 vertex; // <vec2 position, vec2 texCoords>

out vec2 TexCoords;

uniform mat4 model;
uniform int index;
uniform int rows;
uniform int columns;

void main()
{
    TexCoords = vec2(vertex.z + (index % columns) / float(columns), vertex.w + (index / columns) / float(rows));
    gl_Position = model * vec4(vertex.xy, 0.0, 1.0);
}