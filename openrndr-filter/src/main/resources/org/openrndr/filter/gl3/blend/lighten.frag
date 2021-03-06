#version 330

in vec2 v_texCoord0;
uniform sampler2D tex0;
uniform sampler2D tex1;

out vec4 o_color;
void main() {
    vec4 a = texture(tex0, v_texCoord0);
    vec4 b = texture(tex1, v_texCoord0);

    vec4 c = vec4(
        1.0 - (1.0-a.r) * (1.0-b.r),
        1.0 - (1.0-a.g) * (1.0-b.g),
        1.0 - (1.0-a.b) * (1.0-b.b),
        1.0
        );

    o_color = c;
    o_color.a = 1.0;
}