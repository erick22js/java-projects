attribute float a_pos;
attribute float a_uvs;
attribute float a_uvs2;
attribute float a_col;

const float grid = 1/64.0;

uniform vec3 base_color = vec3(1.0, 1.0, 1.0);
uniform mat4 global_matrix;

varying vec2 v_uvs;
varying vec2 v_uvs2;
varying vec3 v_col;

void main(){
	uint i_uvs = uint(a_uvs);
	v_uvs = vec2(
		(mod(floor(float(i_uvs)/64.0), 64.0)*grid),
		(mod(floor(float(i_uvs))     , 64.0)*grid));
	v_uvs2 = vec2(
		(mod(floor(float(i_uvs)/262144.0), 64.0)*grid),
		(mod(floor(float(i_uvs)/4096.0)  , 64.0)*grid));
	uint i_col = uint(a_col);
	v_col = vec3(
		mod(float(i_col)/65536.0, 256.0)/128.0, 
		mod(float(i_col)/256.0,   256.0)/128.0, 
		mod(float(i_col),         256.0)/128.0)*base_color;
	uint i_pos = uint(a_pos);
	gl_Position = vec4(
		mod(float(i_pos)/1024.0, 1024.0)/1.0, 
		mod(float(i_pos),        1024.0)/1.0, 
		0.0, 1.0)*global_matrix;
}
