precision mediump float;

uniform float u_Color;

void main() {
   gl_FragColor = vec4(u_Color, u_Color, u_Color, 1);
}