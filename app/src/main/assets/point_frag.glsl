precision mediump float;

uniform float u_Color;

void main() {
    gl_FragColor = vec4(vec3(u_Color), 1);
}