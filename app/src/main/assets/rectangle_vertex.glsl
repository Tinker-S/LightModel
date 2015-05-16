uniform mat4 u_MVPMatrix;
uniform float u_Color;

attribute vec4 a_Position;

varying vec4 v_Color;

void main() {
    v_Color = vec4(u_Color, u_Color, u_Color, 1);
    gl_Position = u_MVPMatrix * a_Position;
}