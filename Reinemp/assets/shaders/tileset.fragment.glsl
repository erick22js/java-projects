precision mediump float;
	
varying vec2 v_uvs;
varying vec2 v_uvs2;
varying vec3 v_col;

uniform sampler2D tex;

void main(){
	vec4 c_ground = texture2D(tex, v_uvs);
	vec4 c_tile = texture2D(tex, v_uvs2);
	gl_FragColor = (c_ground*(1.0-c_tile.a)+c_tile*c_tile.a)*vec4(v_col, 1.0);
}