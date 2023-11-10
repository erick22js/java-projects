package com.idcorporation.redoom64.renderization;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.idcorporation.redoom64.gameplay.map.GameMap;
import com.idcorporation.redoom64.gameplay.map.Linedef;
import com.idcorporation.redoom64.gameplay.map.Node;
import com.idcorporation.redoom64.gameplay.map.Sector;
import com.idcorporation.redoom64.gameplay.map.Segment;
import com.idcorporation.redoom64.gameplay.map.SubSector;
import com.idcorporation.redoom64.gameplay.map.Vertex;
import com.idcorporation.redoom64.utils.MathDoom;

public class BSPNodes {
	
	GameMap map;
	Projection proj;
	
	Vector3 vert = new Vector3(0, 0, 0);
	
	ArrayList<Linedef> linesForDraw;
	
	public BSPNodes(GameMap map, Projection proj) {
		this.map = map;
		this.proj = proj;
		this.linesForDraw = new ArrayList<Linedef>();
	}
	public int LocationBSPNode(int nodei) {
		Node node = this.map.NODES[nodei];
		//System.out.println(nodei);
		
		float lineAngle = MathDoom.angleVector(node.x, node.y, node.x+node.dx, node.y+node.dy);
		float viewAngle = MathDoom.angleVector(node.x, node.y, proj.x, -proj.z);
		float relativeAngle = (viewAngle-lineAngle);
		//normalize angle
		relativeAngle += relativeAngle>MathDoom.PI?-MathDoom.PI*2:relativeAngle<-MathDoom.PI?MathDoom.PI*2:0;
		
		if(relativeAngle<0)//Right
			return node.rightSSector!= null? node.rightSSector.index: LocationBSPNode(node.right);
		else//Left
			return node.leftSSector!= null? node.leftSSector.index: LocationBSPNode(node.left);
		
	}
	public void getComponents(Sector sector, int deptht, float anglemin, float anglemag) {
		int depth = deptht;
		if(depth<=0)
			return;
		depth--;
		for(int i=0; i<sector.linedefs.length; i++) {
			if(!sector.linedefs[i].forDraw) {
				float[] xs = linedefInFielOfView(sector.linedefs[i], anglemin, anglemag);
				if(xs!=null) {
					linesForDraw.add(sector.linedefs[i]);
					float minfov = xs[0]<=xs[1]?xs[0]:xs[1];
					float maxfov = xs[0]>xs[1]?xs[0]:xs[1];
					sector.linedefs[i].forDraw = true;
					if(sector.linedefs[i].front!=null)
						if(sector.linedefs[i].front.sector.index!=sector.index) {
							getComponents(sector.linedefs[i].front.sector, depth, minfov, maxfov);
						}
					//sector.linedefs[i].front.
					if(sector.linedefs[i].back!=null)
						if(sector.linedefs[i].back.sector.index!=sector.index) {
							getComponents(sector.linedefs[i].back.sector, depth, minfov, maxfov);
						}
					/*if(sector.linedefs[i].back!=null&&sector.linedefs[i].front!=null) {
						if(sector.linedefs[i].front.sector.index!=sector.index) {
							getComponents(sector.linedefs[i].front.sector, depth);
						}else {
							getComponents(sector.linedefs[i].back.sector, depth);
						}
					}*/
				}else {
					//System.out.println("ocluded!");
				}
			}
		}
		//return linedefs;
	}
	public void drawLinedefs(Mesh tmesh, ShaderProgram program) {
		for(int i=0; i<linesForDraw.size(); i++) {
			Linedef line = linesForDraw.get(i);
			line.forDraw = false;
			
			Sector sec = line.front.sector;
			
			float x1 = MathDoom.angleVector(proj.x, -proj.z, line.v1.x, line.v1.y)+proj.direction;
			x1 = x1/MathDoom.PI*45;
			float x2 = MathDoom.angleVector(proj.x, -proj.z, line.v2.x, line.v2.y)+proj.direction;
			x2 = x2/MathDoom.PI*45;
			
			//System.out.println(x1);
			//System.out.println(x2);
			
			tmesh.setVertices(new float[] {
					line.v1.x,  sec.ceilY,  line.v1.y,   x1,  .8f,     1f, 0f, 0f, 1f,     0f, 0f,
					line.v1.x, sec.floorY,  line.v1.y,   x2,  .8f,     0f, 1f, 0f, 1f,     0f, 1f,
					line.v2.x, sec.floorY,  line.v2.y,   x2, -.8f,     0f, 0f, 1f, 1f,     1f, 1f,
					line.v2.x,  sec.ceilY,  line.v2.y,   x1, -.8f,     1f, 1f, 0f, 1f,     1f, 0f,
			});
			if(line.front==null||line.back==null)
				tmesh.render(program, Gdx.gl.GL_TRIANGLE_FAN);
			//System.out.println("drawed!");
		}
	}
	public void RenderBSPNode(Mesh tmesh, ShaderProgram program) {
		
		int ssind = LocationBSPNode(this.map.NODES.length-1);
		SubSector ssector = this.map.SSECTORS[ssind];
		Segment seg = ssector.SEGS[0];
		
		Sector sector;
		
		if(seg.isFront)
			sector = seg.line.front.sector;
		else
			sector = seg.line.back.sector;
		
		linesForDraw.clear();
		
		getComponents(sector, 10, -1, 1);
		drawLinedefs(tmesh, program);
		
		//sector.linedefs[0].back.
		//sector.linedefs[0].
		//Node projection_begin = this.map.NODES[];
	}
	
	public float[] linedefInFielOfView(Linedef line, float minfov, float maxfov) {
		float[] xs = proj.xsFromLinedef(line);
		if(((xs[0]>=minfov&&xs[0]<=maxfov)||(xs[1]>=minfov&&xs[1]<=maxfov))
				||((xs[0]<=minfov&&xs[1]>=maxfov)||(xs[1]<=minfov&&xs[0]>=maxfov))) {
			System.out.println(xs[0]+"___"+xs[1]);
			return xs;
		}
		return null;
				//(xs[0]>=-1&&xs[0]<=1)&&(xs[1]>=-1&&xs[1]<=1);
	}
	
}
