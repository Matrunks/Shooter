package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class SubMachineGun extends Weapon{
	
	public SubMachineGun(){
		position = new Vector3(200,600,0);
		image = weapons.findRegion("Smg");
		readysecs=(float) 0.10;
		damage=5;
		ammo=30;
	}

	@Override
	public AtlasRegion image() {
		return image;
	}

	public int width(){
		return 150;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 100;
	}
}
