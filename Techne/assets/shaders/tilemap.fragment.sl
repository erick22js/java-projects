precision mediump float;

uniform sampler2D uTex;

varying vec3 vColor;
varying vec2 vUv1;
varying vec2 vUv2;

void main(){
	vec4 texColor1 = texture2D(uTex, vUv1)*vec4(vColor, 1.0);
	vec4 texColor2 = texture2D(uTex, vUv2);
	gl_FragColor = texColor1*(1.0-texColor2.a)+texColor2*(texColor2.a);
}