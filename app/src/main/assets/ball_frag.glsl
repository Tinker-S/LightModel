precision mediump float;

uniform float u_Color;

void main() {
    // 环境光
    vec4 ambientColor = vec4(0.5, 0.5, 0.5, 1);

    gl_FragColor = vec4(u_Color, u_Color, u_Color, 1) * ambientColor;
}