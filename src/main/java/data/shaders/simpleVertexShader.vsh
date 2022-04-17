#version 330

in vec3 position;

out vec3 color;

void main(void) {
    gl_Position = vec4(position, 1.0);
    color = vec3(position.x + .4, 1.0, position.y + .5);
}