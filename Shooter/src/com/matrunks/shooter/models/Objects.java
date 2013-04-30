package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public abstract class Objects implements Comparable<Objects>{
	public Vector3 position;
	public int width;
	public int height;
	public AtlasRegion image;
	
	public abstract AtlasRegion image();
	
	public abstract int width();
	
	public void setX(int x){
		position.x=x;
	}
	
	public void setY(int y){
		position.y=y;
	}
	
	public abstract int height();
	
	public int compareTo (Objects c)
	{
		return (int)(this.position.z - c.position.z);
	}

	public abstract void dispose();
}