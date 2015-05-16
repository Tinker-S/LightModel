uniform mat4 u_MVPMatrix;

attribute vec4 a_Position;

uniform float u_Size;

void main() {
    gl_Position = u_MVPMatrix * a_Position;
    gl_PointSize = u_Size;
}