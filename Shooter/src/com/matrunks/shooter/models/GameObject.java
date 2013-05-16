package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject implements Comparable<GameObject>{
	public Vector3 position;
	public int width;
	public int height;
	public AtlasRegion image;
	
	public Vector3 position(){
		return position;
	}
	
	public abstract AtlasRegion image();
	
	public abstract int width();
	
	public abstract int height();
	
	public int compareTo (GameObject c)
	{
		return (int)(c.position.y - this.position.y);
	}

	public abstract void dispose();
}
