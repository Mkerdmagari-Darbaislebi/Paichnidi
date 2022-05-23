#version 330

in vec3 position;
in vec2 textureCoord;

out vec3 color;
out vec2 passTextureCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
    gl_Position = projectionMatrix * transformationMatrix * vec4(position, 1.0);
//    color = vec3(position.x + .4, 1.0, position.y + .5);
    passTextureCoord = textureCoord;
}