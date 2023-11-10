package com.survivalgame.game.primitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class ShaderProgram 
{
	public int VERTEX_STRIDE = 0;
	
	private int m_iProgramId;
	private int m_iVertexShaderId;
	private int m_iFragmentShaderId;
	
	private String[] Attributes;
	private HashMap<String, Integer> AttributesId;
	private HashMap<String, Integer> AttributesSizes;
	private HashMap<String, Integer> UniformsId;
	
	public ShaderProgram(String vertexSrc, String fragmentSrc){
		String sVertexShader = vertexSrc;
		String sFragmentShader = fragmentSrc;
		
		m_iVertexShaderId = Gdx.gl20.glCreateShader(Gdx.gl20.GL_VERTEX_SHADER);
		m_iFragmentShaderId = Gdx.gl20.glCreateShader(Gdx.gl20.GL_FRAGMENT_SHADER);
		
		Gdx.gl20.glShaderSource(m_iVertexShaderId, sVertexShader);
		Gdx.gl20.glShaderSource(m_iFragmentShaderId, sFragmentShader);
		
		Gdx.gl20.glCompileShader(m_iVertexShaderId);
		Gdx.gl20.glCompileShader(m_iFragmentShaderId);
		System.err.println(Gdx.gl20.glGetShaderInfoLog(m_iVertexShaderId));
		System.err.println(Gdx.gl20.glGetShaderInfoLog(m_iFragmentShaderId));
		
		m_iProgramId = Gdx.gl20.glCreateProgram();
		Gdx.gl20.glAttachShader(m_iProgramId, m_iVertexShaderId);
		Gdx.gl20.glAttachShader(m_iProgramId, m_iFragmentShaderId);
		Gdx.gl20.glLinkProgram(m_iProgramId);
		System.err.println(Gdx.gl20.glGetProgramInfoLog(m_iProgramId));
		
	}
	
	public ShaderProgram(String vertexSrc, String fragmentSrc, String[] attrs, int[] attrsSize, String[] unifs) {
		this(vertexSrc, fragmentSrc);
		loadAttributes(attrs, attrsSize);
		loadUniforms(unifs);
	}
	
	public void loadAttributes(String[] attrs, int[] sizes) {
		AttributesId = new HashMap<String, Integer>();
		AttributesSizes = new HashMap<String, Integer>();
		Attributes = attrs;
		VERTEX_STRIDE = 0;
		for(int i=0; i<attrs.length; i++) {
			String attr = attrs[i];
			AttributesId.put(attr, Gdx.gl20.glGetAttribLocation(m_iProgramId, attr));
			AttributesSizes.put(attr, sizes[i]);
			VERTEX_STRIDE += sizes[i]*4;
		}
	}
	public void loadUniforms(String[] unifs) {
		UniformsId = new HashMap<String, Integer>();
		for(int i=0; i<unifs.length; i++) {
			String unif = unifs[i];
			UniformsId.put(unif, Gdx.gl20.glGetUniformLocation(m_iProgramId, unif));
		}
	}
	
	public int getAttribute(String attr) {
		return AttributesId.get(attr);
	}
	public int getAttributeSize(String attr) {
		return AttributesSizes.get(attr);
	}
	public String[] getAttributes() {
		return Attributes;
	}
	public int getUniform(String unif) {
		return UniformsId.get(unif);
	}
	public void useShader(){
		Gdx.gl20.glUseProgram(m_iProgramId);
	}
	
}
