package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public class Bar extends Objects {
	public TextureAtlas hud= new TextureAtlas("data/HUD.atlas");
	
	public Bar(){
		position= new Vector3(10,110,1);
		image=hud.findRegion("redpixel");
		height=5;
	}

	@Override
	public AtlasRegion image() {
		// TODO Auto-generated method stub
		return image;
	}
	
	public void setWidth(int width){
		this.width=width;
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
	
	public void setX(int x){
		position.x=x;
	}
	
	public void setY(int y){
		position.y=y;
	}
}
