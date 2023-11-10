attribute vec3 a_position;
attribute vec3 a_color;
attribute vec2 a_uv;

uniform mat4 gMatrix;
uniform vec2 u_uvf;

varying vec3 v_color;
varying vec2 v_uv;

void main(){
	v_color = a_color;
	v_uv = a_uv/u_uvf;
	gl_Position = gMatrix*vec4(a_position, 1.0);
}