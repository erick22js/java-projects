package com.popcap.gaming.Levels;

import org.jsonJava.JSONArray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.popcap.gaming.ActorObject;
import com.popcap.gaming.Device;
import com.popcap.gaming.Input;
import com.popcap.gaming.Util;
import com.popcap.gaming.Zombie;
import com.popcap.gaming.Plants.Peashooter;
import com.popcap.gaming.Plants.Plant;
import com.popcap.gaming.Plants.seedPacket.SeedPackageSlot;
import com.popcap.gaming.Resources.Resources;

public class GameLevel extends ActorObject{
	Plant[][][] plants_grid;
	Zombie[] zombies;
	
	Matrix4 levelView;
	
	LevelModule module;
	
	boolean allowPlaying = false;
	String gamingState = "";
	
	SeedPackageSlot actual;
	SeedPackageSlot pck1;
	
	public GameLevel(LevelModule module) {
		this.module = module;
		levelView = Device.DefaultView.cpy();
		gamingState = "introduction";
		
		pck1 = new SeedPackageSlot("peashooter");
		pck1.scaleX = pck1.scaleY = 1.2f;
		
		actual = null;
		plants_grid = new Plant[module.locally.rows][9][4];
		//plants_grid[0][0][0] = new Peashooter();
		plantPlant(0, 0);
	}
	
	public void execute(float delta) {
		if(LevelAnimation.AnimationPresenting.execute(levelView)&&gamingState=="introduction")
			gamingState="gaming";
		Resources.batchRender.setProjectionMatrix(levelView);
		Resources.batchRender.getShader().setUniformf("gray", 0);
		prepareForDraw();
		Resources.batchRender.draw(module.locally.background, 0, 0);
		Resources.batchRender.flush();
		
		renderRows();
		
		pck1.render();
		
		
		if(Input.released) {
			int[] grid = coordToGrid(Input.eventX(), Input.eventY());
			if(actual!=null&&!pck1.holdOnRect()) {
				if(validSlot(grid[0], grid[1]))
					plantPlant(grid[0], grid[1]);
				actual = null;
				pck1.color[0] = pck1.color[1] = pck1.color[2] = 1;
			}
		}
		

		if(pck1.picked()) {
			//System.out.println("tap");
			pck1.color[0] = pck1.color[1] = pck1.color[2] = .4f;
			actual = pck1;
		}
			//System.out.println("para coordenadas: "+new JSONArray().putAll(coordToGrid(Input.eventX(), Input.eventY())).toString());//"tocado:\nx:"+Gdx.input.getX()+"\ny:"+Gdx.input.getX());
		
	}
	
	public void renderRows() {
		for(int r=0; r<plants_grid.length; r++) {
			for(int c=0; c<9; c++) {
				for(int p=0; p<4; p++) {
					if(plants_grid[r][c][p]!=null)
						plants_grid[r][c][p].execute();
				}
			}
		}
	}
	
	public void plantPlant(int collum, int row) {
		Plant plant = new Peashooter();//prot.instantiate();
		float[] coord = gridToCoord(collum+.5f, row-.8f);
		plant.x = coord[0]+Util.random(-12, 15);
		plant.y = coord[1]+Util.random(-6, 7);
		plants_grid[row][collum][0] = plant;
		//int[] coords = coordGrids(x, y);
	}
	public boolean validSlot(int x, int y) {
		return (x>=0&&x<7)&&(y>=0&&y<module.locally.rows);
	}
	public int[] coordToGrid(float x, float y) {
		return new int[] {
				(int)((x-158)/82)-1,
				(int)((y+68)/98)-1
		};
	}
	public float[] gridToCoord(float x, float y) {
		return new float[] {
				(x+1)*82+158,
				(y+1)*98+68
		};
	}
}
