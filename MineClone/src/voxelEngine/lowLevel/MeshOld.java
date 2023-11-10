package voxelEngine.lowLevel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MeshOld {
	private FloatBuffer m_vertexBuffer;
	private ShortBuffer m_indiceBuffer;
	
	private float[] vertices;
	private short[] indices;
	
	public MeshOld(){}
	public MeshOld(float[] vertices, short[] indices) {
		setVertices(vertices);
		setIndices(indices);
	}
	public void setVertices(float[] vertices) {
		this.vertices = vertices;
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		
		m_vertexBuffer = bb.asFloatBuffer();
		m_vertexBuffer.put(vertices);
		m_vertexBuffer.position(0);
	}
	public void setIndices(short[] indices) {
		this.indices = indices;
		ByteBuffer bb = ByteBuffer.allocateDirect(indices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		
		m_indiceBuffer = bb.asShortBuffer();
		m_indiceBuffer.put(indices);
		m_indiceBuffer.position(0);
	}
	public void draw(ShaderProgram shader){
		String[] attrs = shader.getAttributes();
		int offset = 0;
		for(int i=0; i<attrs.length; i++) {
			String attr = attrs[i];
			int size = shader.getAttributeSize(attr);
			m_vertexBuffer.position(offset);
			int attrId = shader.getAttribute(attr);
			Gdx.gl20.glEnableVertexAttribArray(attrId);
			Gdx.gl20.glVertexAttribPointer(attrId, size, Gdx.gl20.GL_FLOAT, false, 
					                     shader.VERTEX_STRIDE, m_vertexBuffer);
			offset += size;
		}
		Gdx.gl20.glDrawElements(Gdx.gl20.GL_TRIANGLES, indices.length, GL20.GL_UNSIGNED_SHORT, m_indiceBuffer);
		for(int i=0; i<attrs.length; i++) {
			String attr = attrs[i];
			int attrId = shader.getAttribute(attr);
			Gdx.gl20.glDisableVertexAttribArray(attrId);
		}
	}
}
