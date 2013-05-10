package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class SubMachineGun extends Weapon{
	
	public SubMachineGun(){
		position = new Vector3(-10,110,1);
		image = weapons.findRegion("SMG");
		readysecs=(float) 0.10;
		damage=5;
		ammo=25;
		maxammo=60;
	}
	
	public void reset(){
		ammo=25;
	}

	@Override
	public AtlasRegion image() {
		return image;
	}

	public int width(){
		return 110;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 90;
	}

}
