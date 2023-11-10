/**
 * 
 */
package com.testProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;


public class OpenGlProject {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Not could initialized 'GLFW' library.");
		}
		//System.out.println("Initialized!");

		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		
		long window = GLFW.glfwCreateWindow(800, 480, "Meu jogo java em opengl", 0, 0);
		
		if(window==0) {
			throw new IllegalStateException("Window can't be initialized!");
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwShowWindow(window);
		
		MeshObject obj = new MeshObject();
		obj.createMesh(new float[] {
			-.5f, -.5f, 0,
			-.5f, .5f, 0,
			.5f, .5f, 0,
			.5f, -.5f, 0
		}, 3);
		obj.storeBuffer(new float[]{
				1, 0, 0,
				0, 1, 0,
				0, 0, 1,
				1, 1, 0
		}, 1, 3);
		obj.storeBuffer(new float[]{
				0, 1,
				0, 0,
				1, 0,
				1, 1
		}, 2, 2);
		ShaderObject sh = new ShaderObject();
		
		sh.create(Loader.readTextFile("assets/shaders/vertex.glsh"), Loader.readTextFile("assets/shaders/fragment.glsh"));
		
		TextureObject texturet = new TextureObject("assets/TEXTURA.PNG");
		
		float mp = -1.0f;
		while(!GLFW.glfwWindowShouldClose(window)) {
			GLFW.glfwPollEvents();
			GL11.glClearColor(0, .7f, .3f, 1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			sh.useShader();
			sh.setUniformFloat("valor", mp);
			sh.setUniformInt("textureSampler", 0);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturet.getTexture());
			obj.draw(GL11.GL_TRIANGLE_FAN);
			mp+=.0005f;
			GLFW.glfwSwapBuffers(window);
		}
		sh.destroy();
		obj.destroyObject();
		GLFW.glfwTerminate();
		
	}
	
}
