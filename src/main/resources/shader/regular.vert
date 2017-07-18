#version 330 core
layout (location = 0) in vec4 vertex; // <vec2 position, vec2 texCoords>

out vec2 TexCoords;

uniform int index;
uniform int rows;
uniform int columns;
uniform mat4 projection;
uniform mat4 model;
uniform mat4 scale;
uniform mat4 rotation;

void main()
{
    TexCoords = vec2(vertex.z + (index % columns) / float(columns), vertex.w + (index / columns) / float(rows));
    gl_Position = projection * model * scale * rotation * vec4(vertex.xy, 0.0, 1.0);
}