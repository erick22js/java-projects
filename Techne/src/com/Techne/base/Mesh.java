package com.Techne.base;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Mesh {
	private Buffer m_vertexBuffer;
	private int vertexBufferFormat;
	private ShortBuffer m_indiceBuffer;
	
	//private float[] vertices;
	private short[] indices;
	
	public Mesh(){}
	public Mesh(float[] vertices, short[] indices) {
		setVertices(vertices);
		setIndices(indices);
	}
	public void setVertices(float[] vertices) {
		//m_vertexBuffer.
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		//bb.
		bb.order(ByteOrder.nativeOrder());
		vertexBufferFormat = Gdx.gl20.GL_FLOAT;
		
		m_vertexBuffer = bb.asFloatBuffer();
		((FloatBuffer) m_vertexBuffer).put(vertices);
		m_vertexBuffer.position(0);
	}
	/*public void setVertices(FloatBuffer vertices) {
		vertexBufferFormat = Gdx.gl20.GL_FLOAT;
		m_vertexBuffer = vertices;
		m_vertexBuffer.position(0);
	}*/
	public Buffer getVerticesBuffer() {
		return m_vertexBuffer;
	}
	public void setIndices(short[] indices) {
		this.indices = indices;
		ByteBuffer bb = ByteBuffer.allocateDirect(indices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		
		m_indiceBuffer = bb.asShortBuffer();
		m_indiceBuffer.put(indices);
		m_indiceBuffer.position(0);
	}
	/*public void setIndices(ShortBuffer indices) {
		m_indiceBuffer = indices;
		m_indiceBuffer.position(0);
	}*/
	public ShortBuffer getIndicesBuffer() {
		return m_indiceBuffer;
	}
	public void draw(ShaderProgram shader, int mode){
		String[] attrs = shader.getAttributes();
		int offset = 0;
		for(int i=0; i<attrs.length; i++) {
			String attr = attrs[i];
			int size = shader.getAttributeSize(attr);
			m_vertexBuffer.position(offset);
			int attrId = shader.getAttribute(attr);
			Gdx.gl20.glEnableVertexAttribArray(attrId);
			Gdx.gl20.glVertexAttribPointer(attrId, size, vertexBufferFormat, false, 
					                     shader.VERTEX_STRIDE, m_vertexBuffer);
			offset += size;
		}
		//Gdx.gl20.glDr
		Gdx.gl20.glDrawElements(mode, indices.length, GL20.GL_UNSIGNED_SHORT, m_indiceBuffer);
		for(int i=0; i<attrs.length; i++) {
			String attr = attrs[i];
			int attrId = shader.getAttribute(attr);
			Gdx.gl20.glDisableVertexAttribArray(attrId);
		}
	}
}
