attribute vec2 aPosition;
attribute vec3 aColor;
attribute vec2 aUv1;
attribute vec2 aUv2;

uniform mat4 uMatrix;

varying vec3 vColor;
varying vec2 vUv1;
varying vec2 vUv2;

void main(){
	vColor = aColor;
	vUv1 = aUv1/10.0;
	vUv2 = aUv2/10.0;
	gl_Position = vec4(aPosition, 0.0, 1.0)*uMatrix;
}