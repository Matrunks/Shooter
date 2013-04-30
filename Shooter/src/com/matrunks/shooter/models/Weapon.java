package com.matrunks.shooter.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public abstract class Weapon extends Objects {
	public int ammo;
	public int damage;
	public int maxammo;
	public float readysecs;
	public float reloadsecs;
	public static TextureAtlas weapons = new TextureAtlas ("data/Weapons.atlas");
	
	public int ammo(){
		return ammo;
	}
	
	public int damage(){
		return damage;
	}
	
	public int maxammo(){
		return maxammo;
	}
	
	public void addAmmo(int ammo){
		if(this.ammo+ammo>maxammo){
			this.ammo=maxammo;
		}else{
			this.ammo+=ammo;
		}
	}
	
	public float readysecs(){
		return readysecs;
	}
	
	public void shot(){
		if(ammo>0)
			ammo--;
	}
	
	public float reloadsecs(){
		return reloadsecs;
	}
	public void dispose(){
		weapons.dispose();
	}
}
