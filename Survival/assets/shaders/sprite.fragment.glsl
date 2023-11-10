precision mediump float;

uniform sampler2D u_tex;
uniform vec4 u_color;

varying vec2 v_uv;

void main(){
	vec4 tex_color = texture2D(u_tex, v_uv);
	gl_FragColor = tex_color*u_color;
}