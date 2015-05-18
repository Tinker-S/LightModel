precision mediump float;

varying vec3 vPosition;//接收从顶点着色器过来的顶点位置
varying vec4 vDiffuse;//接收从顶点着色器过来的散射光分量

void main() {
    vec4 color= vec4(0.5, 0.5, 0.5, 1);
    gl_FragColor = color * vDiffuse;
}