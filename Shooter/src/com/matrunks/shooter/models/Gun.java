package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class Gun extends Weapon{

	public Gun(){
		position=new Vector3(0,0,1);
		ammo=-1;
		image = weapons.findRegion("Pistola");
		readysecs=(float) 0.60;
		damage=10;
	}
	
	@Override
	public AtlasRegion image() {
		return image;
	}

	@Override
	public int width() {
		return (int)194/2;
	}

	@Override
	public int height() {
		return (int)157/2;
	}

}
