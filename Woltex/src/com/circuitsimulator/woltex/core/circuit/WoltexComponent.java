package com.circuitsimulator.woltex.core.circuit;

import org.jsonJava.JSONArray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.circuitsimulator.woltex.renderer.ComponentShape;
import com.circuitsimulator.woltex.utils.FastMath;

public class WoltexComponent {
	
	private static String baseShapePath = "assets/ShapesCircuits/";
	
	public ComponentShape[] shapes;
	public WoltexPin[] pins;
	
	public Matrix4 local;
	public Vector2 position;
	public float rotation;
		//0 is 0 degress
		//1 is 90 degress
		//2 is 180 degress
		//3 is -90 degress
	
	//Measures and values
	public float energy = 0;
	public float temperature = 0;
	
	public WoltexComponent(String shapeFilePath) {
		JSONArray parts = new JSONArray(Gdx.files.internal(baseShapePath+shapeFilePath).readString());
		shapes = new ComponentShape[parts.length()];
		for(int c=0; c<shapes.length; c++) {
			shapes[c] = new ComponentShape(parts.getJSONObject(c));
		}
		local = new Matrix4();
		position = new Vector2(0, 0);
		rotation = 0;
	}
	
	public void render(ShapeRenderer renderer, float r, float g, float b, float a, float penSize, Matrix4 matrix) {
		for(int c=0; c<shapes.length; c++) {
			shapes[c].render(renderer, r, g, b, a, penSize, matrix);
		}
		for(int p=0; p<pins.length; p++) {
			pins[p].render(renderer, matrix);
		}
	}
	public void render(ShapeRenderer renderer, Matrix4 globalMatrix) {
		local = globalMatrix.cpy();
		local.translate(position.x, position.y, 0);
		local.rotate(0, 0, 1, rotation);
		render(renderer, 0, 0, 0, 0, 4, local);
	}
	
	//Prototyping funcion for circuit execution
	public void execute() {}
	
}
