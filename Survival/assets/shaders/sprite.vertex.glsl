attribute vec3 a_position;
attribute vec2 a_uv;

uniform mat4 gMatrix;
uniform mat4 oMatrix;

varying vec2 v_uv;

void main(){
	v_uv = a_uv;
	gl_Position = oMatrix*gMatrix*vec4(a_position, 1.0);
}