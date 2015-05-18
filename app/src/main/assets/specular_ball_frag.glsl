precision mediump float;

varying vec3 vPosition;
varying vec4 vSpecular;

void main() {
    vec4 ambientColor = vec4(1.0);

    vec3 color = vec3(0.5, 0.5, 0.5);
    gl_FragColor = vec4(color, 1) * vSpecular + vec4(color, 1) * ambientColor;
}