package com.testProject;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class MeshObject {
	private int ArraysObj;
	private int vertexBufferObj;
	private int colorArrayObj;
	
	private int vertexCount;
	
	public MeshObject() {
		
	}
	
	public void createMesh(float[] vertices, int dimensions) {
		ArraysObj = GL30.glGenVertexArrays();
		vertexBufferObj = storeBuffer(vertices, 0, dimensions);
		vertexCount = vertices.length/dimensions;
		
	}
	
	public int storeBuffer(float[] bufferData, int index, int dimensionsSize) {
		GL30.glBindVertexArray(ArraysObj);
		FloatBuffer bf = MemoryUtil.memAllocFloat(bufferData.length);
		bf.put(bufferData).flip();
		int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bf, GL15.GL_STATIC_DRAW);
		MemoryUtil.memFree(bf);
		GL20.glVertexAttribPointer(index, dimensionsSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		return buffer;
		//vertexCount = vertices.length/dimensions;
	}
	
	public void destroyObject() {
		GL15.glDeleteBuffers(vertexBufferObj);
		GL30.glDeleteVertexArrays(ArraysObj);
	}
	
	public void draw(int type) {
		GL30.glBindVertexArray(ArraysObj);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glDrawArrays(type, 0, vertexCount);
		
		
		GL30.glBindVertexArray(0);
	}
	
}
