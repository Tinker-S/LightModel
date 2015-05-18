uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;
uniform vec3 uLightLocation;

attribute vec3 aPosition;
attribute vec3 aNormal;
varying vec3 vPosition;
varying vec4 vDiffuse;

void pointLight (in vec3 normal, inout vec4 diffuse, in vec3 lightLocation, in vec4 lightDiffuse) {
    vec3 normalTarget = aPosition + normal;
    vec3 newNormal = (uMMatrix * vec4(normalTarget,1)).xyz - (uMMatrix*vec4(aPosition,1)).xyz;
    newNormal = normalize(newNormal);
    vec3 vp = normalize(lightLocation - (uMMatrix * vec4(aPosition, 1)).xyz);
    vp = normalize(vp);
    float nDotViewPosition = max(0.0, dot(newNormal, vp));
    diffuse = lightDiffuse * nDotViewPosition;
}

void main() {
    gl_Position = uMVPMatrix * vec4(aPosition,1);
    vec4 diffuseTemp = vec4(0.0, 0.0, 0.0, 0.0);

    vec4 diffuseStrenth = vec4(1, 1, 1, 1);
    pointLight(normalize(aNormal), diffuseTemp, uLightLocation, diffuseStrenth);
    vDiffuse = diffuseTemp;
    vPosition = aPosition;
}