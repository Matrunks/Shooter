package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class Gun extends Weapon{

	public Gun(){
		position=new Vector3(0,600,0);
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
		return 150;
	}

	@Override
	public int height() {
		return 100;
	}

	@Override
	public void dispose() {
		((Disposable) image).dispose();
	}

}
