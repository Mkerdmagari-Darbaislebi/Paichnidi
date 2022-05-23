#version 460 core

//in vec3 color;
in vec2 passTextureCoord;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main() {
    out_Color = texture(textureSampler, passTextureCoord);
}