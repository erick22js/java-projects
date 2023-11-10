package com.circuitsimulator.woltex.renderer;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.circuitsimulator.woltex.utils.FastMath;

public class ComponentShape {
	
	public static final int LINE_MODE = 0x10;
	public static final int CURVE_MODE = 0x11;
	public static final int RECT_MODE = 0x12;
	public static final int ARC_MODE = 0x13;
	public static final int POINT_MODE = 0x14;
	public static final int COLOR_MODE = 0x20;
	public static final int LINESIZE_MODE = 0x21;
	public static final int FLUSH_MODE = 0x30;
	public static final int SWAP_TLINE = 0x35;
	public static final int SWAP_TFILL = 0x36;
	
	private float[][] shape;
	private float lineSize;
	
	public ComponentShape(float[][] shape, boolean filled) {
		this.shape = shape;
	}
	
	public ComponentShape(JSONObject shapejson) {
		JSONArray arr = shapejson.getJSONArray("shape");
		this.shape = new float[arr.length()][];
		for(int i=0; i<arr.length(); i++) {
			JSONArray elem = arr.getJSONArray(i);
			this.shape[i] = new float[elem.length()];
			String key = elem.getString(0);
			this.shape[i][0] = key.contentEquals("LINE_MODE")? LINE_MODE:
					key.contentEquals("CURVE_MODE")? CURVE_MODE:
					key.contentEquals("ARC_MODE")? ARC_MODE:
					key.contentEquals("POINT_MODE")? POINT_MODE:
					key.contentEquals("COLOR_MODE")? COLOR_MODE:
					key.contentEquals("LINESIZE_MODE")? LINESIZE_MODE:
					key.contentEquals("FLUSH_MODE")? FLUSH_MODE:
					key.contentEquals("SWAP_TLINE")? SWAP_TLINE:
					key.contentEquals("SWAP_TFILL")? SWAP_TFILL:
					0;
			for(int a=1; a<elem.length(); a++) {
				this.shape[i][a] = elem.getFloat(a);
			}
		}
		lineSize = shapejson.getFloat("lineSize");
		System.out.println(shapejson.toString(2));
	}
	
	public void render(ShapeRenderer renderer, float r, float g, float b, float a, float penSize, Matrix4 matrix) {
		renderer.setProjectionMatrix(matrix);
		renderer.begin(ShapeType.Line);
		renderer.setColor(r, g, b, a);
		renderer.updateMatrices();
		Gdx.gl.glLineWidth(lineSize);
		int begin_fill_mode = -1;
		for(int s=0; s<shape.length; s++) {
			switch((int)shape[s][0]) {
				case(LINE_MODE): {
						renderer.line(shape[s][1], shape[s][2], shape[s][3], shape[s][4]);
					}
					break;
				case(CURVE_MODE): {
						renderer.curve(shape[s][1], shape[s][2], shape[s][3], shape[s][4], shape[s][5], shape[s][6], shape[s][7], shape[s][8], (int)shape[s][9]);
					}
					break;
				case(RECT_MODE): {
						renderer.rect(shape[s][1], shape[s][2], shape[s][3], shape[s][4]);
					}
					break;
				case(ARC_MODE): {
						renderer.arc(shape[s][1], shape[s][2], shape[s][3], shape[s][4], shape[s][5]);
					}
					break;
				case(POINT_MODE): {
						renderer.point(shape[s][1], shape[s][2], shape[s][3]);
					}
					break;
				case(COLOR_MODE): {
						renderer.setColor(shape[s][1], shape[s][2], shape[s][3], shape[s][4]);
					}
					break;
				case(LINESIZE_MODE): {
						Gdx.gl.glLineWidth(shape[s][1]);
					}
					break;
				case(FLUSH_MODE): {
						renderer.flush();
					}
					break;
				case(SWAP_TLINE):{
						renderer.end();
						renderer.begin(ShapeType.Line);
					}
					break;
				case(SWAP_TFILL):{
						renderer.end();
						renderer.begin(ShapeType.Filled);
					}
					break;
			}
		}
		renderer.end();
	}
}
