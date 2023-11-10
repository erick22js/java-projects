package com.popcap.gaming.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.popcap.gaming.Levels.LevelAnimation.Animation;

class LevelAnimation{
	public static class Animation{
		public int index_anim = 0;
		public float time_less = 0;
		public float quot_factor = 1;
		public float initial = 0;
		public boolean running = false;
		
		public Object[][] animMotion;
		
		public Animation(Object[][] da) { animMotion = da;}
		
		public boolean endedAnimation() {
			return time_less<=0 && index_anim==animMotion.length-1;
		}
		public float getExactlyDeltaTime() {
			float delta = Gdx.graphics.getDeltaTime();
			if(time_less-delta<=0)
				return time_less;
			return delta;
		}
		public void reset() {
			time_less = index_anim = 0;
			running = false;
		}
		public void initial(Matrix4 levelView) {
			switch(String.valueOf(animMotion[index_anim][0])) {
				case "cameraX":
					initial = levelView.getTranslation(Vector3.Zero).x;
					break;
				case "cameraY":
					initial = levelView.getTranslation(Vector3.Zero).y;
					break;
			}
		}
		public boolean execute(Matrix4 levelView) {
			if(time_less<=0 && endedAnimation())
				return true;
			if(!running) {
				running = true;
				index_anim = 0;
				initial(levelView);
				time_less = ((Float)animMotion[index_anim][3]);
			}
			
			float deltaTime = getExactlyDeltaTime();
			
			Object[] key = animMotion[index_anim];
			float res_value = ((Float)key[1]);//*quot_factor;
			//res_value *= deltaTime;
			quot_factor *= ((Float)key[2]);
			
			float progress = 1-time_less/((Float)animMotion[index_anim][3]);
			
			progress = (float) Math.pow(progress, ((Float)animMotion[index_anim][2]));
			
			switch(key[0].toString()) {
				case "cameraX":
					float posx = levelView.getTranslation(Vector3.Zero).x;
					float posy = levelView.getTranslation(Vector3.Zero).y;
					levelView.setTranslation(initial+(res_value-initial)*progress, posy, 0);
					break;
				case "cameraY":
					//levelView.translate(0, res_value, 0);
					levelView.setTranslation(res_value, 0, 0);
					break;
				case "wait":
					break;
			}
			time_less-=deltaTime;
			if(time_less<=0) {
				//System.out.println("ended at:"+time_less);
				if(endedAnimation())
					return true;
				index_anim++;
				quot_factor = 1;
				time_less = ((float)animMotion[index_anim][3]);
				initial(levelView);
				//System.out.println("init at:"+time_less);
			}
			return false;
		}
	}
	public static Animation AnimationPresenting = new Animation(new Object[][]{
				//Mode,    inc, quot, time(sec)
				{"wait", 0f, 1f, 1.5f},
				{"cameraX", -1.3f, 1.5f, .6f},
				{"cameraX", -1.4f, 1.15f, .15f},
				{"cameraX", -1.55f, .95f, .15f},
				{"cameraX", -1.75f, .4f, .65f},//1.2
				{"wait", 0f, 1f, 2f},
				{"cameraX", -1.375f, 2f, .9f},
				{"cameraX", -1f, .5f, .9f},
			});
}
