package com.testProject;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderObject {
	
	private int vertexShader;
	private int fragmentShader;
	
	private int programShader;
	
	public ShaderObject() {
		
	}
	public void create(String vertexSrc, String fragmentSrc) {
		vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader, vertexSrc);
		GL20.glCompileShader(vertexShader);
		int sucess = GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS);
		if(sucess == GL11.GL_FALSE) {
			System.out.print("vertex");
			System.err.println(GL20.glGetShaderInfoLog(vertexShader));
		}
		
		fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader, fragmentSrc);
		GL20.glCompileShader(fragmentShader);
		sucess = GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS);
		if(sucess == GL11.GL_FALSE) {
			System.out.print("fragment");
			System.err.println(GL20.glGetShaderInfoLog(fragmentShader));
		}
		
		programShader = GL20.glCreateProgram();
		GL20.glAttachShader(programShader, vertexShader);
		GL20.glAttachShader(programShader, fragmentShader);
		
		GL20.glLinkProgram(programShader);
		sucess = GL20.glGetProgrami(programShader, GL20.GL_LINK_STATUS);
		if(sucess == GL11.GL_FALSE) {
			//System.out.println("Linking");
			System.err.println(GL20.glGetProgramInfoLog(programShader));
		}
		
		GL20.glValidateProgram(programShader);
		sucess = GL20.glGetProgrami(programShader, GL20.GL_VALIDATE_STATUS);
		if(sucess == GL11.GL_FALSE) {
			//System.out.println("Validing");
			System.err.println(GL20.glGetProgramInfoLog(programShader));
		}
	}
	private int createShader(String src) {
		int shader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);
		
		int sucess = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		
		if(sucess == GL11.GL_FALSE) {
			System.out.print(src);
			System.err.println(GL20.glGetShaderInfoLog(shader));
		}
		return shader;
	}
	public void setUniformFloat(String name, float value) {
		int uniformS = GL20.glGetUniformLocation(programShader, name);
		/*if(uniformS<0) {
			System.err.println("Uniform of name '"+name+"' can't be founded!");
			return;
		}*/
		//GL20.glGet
		GL20.glUniform1f(uniformS, value);
	}
	public void setUniformInt(String name, int value) {
		int uniformS = GL20.glGetUniformLocation(programShader, name);
		/*if(uniformS<0) {
			System.err.println("Uniform of name '"+name+"' can't be founded!");
			return;
		}*/
		//GL20.glGet
		GL20.glUniform1i(uniformS, value);
	}
	public void destroy() {
		GL20.glDetachShader(programShader, vertexShader);
		GL20.glDetachShader(programShader, fragmentShader);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteProgram(programShader);
	}
	
	public void useShader() {
		GL20.glUseProgram(programShader);
	}
	
}
