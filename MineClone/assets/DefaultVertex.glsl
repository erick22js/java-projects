attribute vec3 a_position;

uniform mat4 povMatrix;

void main(){
	gl_Position = povMatrix*vec4(a_position, 1.0);
}
