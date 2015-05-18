uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;
uniform vec3 uLightLocation;
uniform vec3 uCamera;

attribute vec3 aPosition;
attribute vec3 aNormal;

varying vec3 vPosition;
varying vec4 vSpecular;

void pointLight(in vec3 normal, inout vec4 specular, in vec3 lightLocation, in vec4 lightSpecular) {
    vec3 normalTarget = aPosition + normal;
    vec3 newNormal = (uMMatrix * vec4(normalTarget, 1)).xyz-(uMMatrix * vec4(aPosition, 1)).xyz;
    newNormal = normalize(newNormal);
    //计算从表面点到摄像机的向量
    vec3 eye = normalize(uCamera - (uMMatrix * vec4(aPosition, 1)).xyz);
    //计算从表面点到光源位置的向量vp
    vec3 vp = normalize(lightLocation - (uMMatrix * vec4(aPosition, 1)).xyz);
    vp = normalize(vp);//格式化vp
    vec3 halfVector = normalize(vp + eye); //求视线与光线的半向量
    float shininess = 50.0; //粗糙度，越小越光滑
    float nDotViewHalfVector = dot(newNormal, halfVector); //法线与半向量的点积
    float powerFactor = max(0.0, pow(nDotViewHalfVector, shininess)); //镜面反射光强度因子
    specular = lightSpecular * powerFactor; //最终的镜面光强度
}

void main()  {
    gl_Position = uMVPMatrix * vec4(aPosition, 1); //根据总变换矩阵计算此次绘制此顶点的位置
    vec4 specularTemp=vec4(0.0, 0.0, 0.0, 0.0);
    pointLight(normalize(aNormal), specularTemp, uLightLocation, vec4(0.7, 0.7, 0.7, 1.0));//计算镜面光
    vSpecular = specularTemp;	//将最终镜面光强度传给片元着色器
    vPosition = aPosition; 		//将顶点的位置传给片元着色器
} 
