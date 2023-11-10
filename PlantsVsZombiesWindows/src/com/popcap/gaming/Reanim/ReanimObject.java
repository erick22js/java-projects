package com.popcap.gaming.Reanim;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.popcap.gaming.ActorObject;
import com.popcap.gaming.Resources.DataReanims;
import com.popcap.gaming.Resources.Resources;

public class ReanimObject extends ActorObject{
	
	//ReanimPart[] parts;
	
	public float key_frame;
	int[][] track;
	float length;
	public float color;
	Mesh meshBody;
	
	ReanimControl animation;
	String actualPlaying;
	
	int[] pos;
	
	//ShaderProgram program;
	Texture textureSrc;
	
	//public Matrix4 transformation;
	
	float[] vertices;
	short[] indices;
	
	public ReanimObject(ReanimData reanimdata) {
		track = reanimdata.frames;
		textureSrc = reanimdata.texture;
		animation = reanimdata.animation;
		actualPlaying = animation.animations.keySet().iterator().next();
		length = reanimdata.length;
		x = 0;
		y = 0;
		scaleX = 1;
		scaleY = 1;
		pivotX = 0;
		pivotY = 0;
		angle = 0;
		meshBody = new Mesh(true, 20, 6,
				new VertexAttribute(Usage.Position, 2, "a_position"),
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0")
				);
		vertices = new float[20];
		indices = new short[6];
		
	}
	public boolean endedAnimation() {
		return key_frame>=animation.animations.get(actualPlaying)[1];
	}
	public boolean endedAnimationAndReset() {
		boolean ended = endedAnimation();
		if(ended) {
			resetAnimation();
		}
		return ended;
	}
	public void resetAnimation() {
		key_frame = animation.animations.get(actualPlaying)[0];
	}
	public void play(String name) {
		actualPlaying = name;
		key_frame = animation.animations.get(actualPlaying)[0];
	}
	public void execute(float delta) {
		key_frame += delta;
	}
	public String getPlaying() {
		return actualPlaying;
	}
	public void render() {
		endedAnimationAndReset();
		pos = track[(int) key_frame];
		//float tX = position.getFloat("pX");
		//float tY = -(position.getFloat("pY")+position.getFloat("srcH"))+position.getFloat("height");
		Resources.batchRender.draw(textureSrc,
				x-pivotX, y-pivotY,
				pivotX, pivotY,//60,40,//10, 85,
				pos[2],pos[3],//50,50,//165, 197,
				scaleX, scaleY,
				angle,
				pos[0], pos[1],
				pos[2], pos[3],
				false, false
		);
	}
}
