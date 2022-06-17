#version 330

in vec3 position;
in vec2 textureCoord;

out vec3 color;
out vec2 passTextureCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    passTextureCoord = textureCoord;
}