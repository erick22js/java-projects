precision mediump float;

uniform sampler2D tex;

varying vec3 v_color;
varying vec2 v_uv;

void main(){
	vec4 color = texture2D(tex, v_uv);
	gl_FragColor = color;
}