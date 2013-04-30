package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public class HUD extends Objects {

	public static TextureAtlas hud = new TextureAtlas("data/HUD.atlas");
	
	public HUD(int type){
		if(type==1){
			image=hud.findRegion("health");
			position= new Vector3(20,660,1);
			width=555;
			height=120;
		}
		if(type==2){
			image=hud.findRegion("selector");
			position= new Vector3(-5,-5,1);
			width=120;
			height=100;
		}
		
	}
	@Override
	public AtlasRegion image() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		hud.dispose();
	}
	
}
