#version 330 core

in vec2 fragTextureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {
    fragColor = texture(textureSampler, fragTextureCoords);
}