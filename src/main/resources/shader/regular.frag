#version 330 core
in vec2 TexCoords;
out vec4 color;

uniform sampler2D image;
uniform int blackFilter;

void main()
{
    color = texture(image, TexCoords);
    if (blackFilter == 1 && color.r == color.g && color.r == color.b && color.r < 0.3)
        color = vec4(0.0, 0.0, 0.0, 0.0);
}