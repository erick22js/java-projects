package com.popcap.gaming.Resources;

import java.util.Iterator;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.popcap.gaming.Device;

public class Resources {
	public static Batch batchRender;
	public static ShaderProgram reanimProgram;
	public static void initializeBatch(Batch batch) {
		batchRender = batch;
	}
	public static FileHandle loadAsset(String path) {
		return Gdx.files.internal((!Device.mobile?"assets/":"")+path);
	}
	public static JSONObject JSONreanimAsDataP(JSONObject reanimData) {
		//JSONArray library = reanimData.getJSONArray("library");
		JSONArray timeline = reanimData.getJSONArray("tracks");
		
		float[][][] anims = new float[reanimData.getJSONArray("tracks").length()][][];//(float[][][])reanimData.get("tracks");
		float[][] libs = new float[reanimData.getJSONArray("library").length()][];//new JSONArray(). (String[]) reanimData.get("library");
		
		for(int x=0; x<reanimData.getJSONArray("tracks").length();x++) {
			JSONArray tra = reanimData.getJSONArray("tracks").getJSONArray(x);
			float[][] track = new float[tra.length()][];
			for(int y=0; y<tra.length(); y++) {
				JSONArray fram = reanimData.getJSONArray("tracks").getJSONArray(x).getJSONArray(y);
				float[] frame = new float[fram.length()];
				for(int i=0; i<9; i++)
					frame[i] = fram.getFloat(i);
				track[y] = frame;
			}
			anims[x] = track;
		}
		

		for(int x=0; x<reanimData.getJSONArray("libraries").length();x++) {
			JSONArray lib = reanimData.getJSONArray("libraries").getJSONArray(x);
			float[] libl = new float[] {
					lib.getFloat(0),
					lib.getFloat(1),
					lib.getFloat(2),
					lib.getFloat(3),
			};
			libs[x] = libl;
		}
		
		return new JSONObject().put("libraries", libs).put("tracks", anims);
	}
}
