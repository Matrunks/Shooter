package com.matrunks.shooter.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public class Cover extends GameObject{
	
	public AtlasRegion cover;
	public static TextureAtlas covers = new TextureAtlas(Gdx.files.internal("data/coberturas.atlas"));
	public int hp=70;
	
	public Cover(int x, int y){
		this.position=new Vector3 (x,y,0);
		
		//si reduzco sus ancho y alto es por que el juego est� en perspectiva y a m�s y m�s lejos est� el objeto
		height=340-(int)(y/1.75);
		width=160-(int)(y/3.5);
		
		cover = covers.findRegion("Cobertura"); //en un futuro puede haber m�s tipos de coberuras
	}
	
	public int width(){
		return width;
	}
	
	public int health(){
		return hp;
	}
	
	public void damage(int d){
		hp-=d;
	}
	
	public int height(){
		return height;
	}
	
	public AtlasRegion image(){
		return cover;
	}
	
	public void dispose(){
		covers.dispose();
	}
}
