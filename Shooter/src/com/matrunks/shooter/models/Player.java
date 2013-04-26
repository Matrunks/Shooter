package com.matrunks.shooter.models;

public class Player {

	private int hp=100;
	private float ShotSecs=0;
	private Weapon weapon;
	
	public Player(){
		initialize();
	}
	
	public void initialize(){
		hp=100;
	}
	
	public void takeDamage(int damage){
		hp-=damage;
	}
	
	public void update(float delta){
		ShotSecs+=delta;
	}
	
	public boolean isAlive(){
		return hp>0;
	}
	
	public int health(){
		return hp;
	}
	
	public boolean checkGun(){
		if(ShotSecs>weapon.readysecs() && (weapon.ammo() >0 || weapon.ammo()<0)){ //si es menor que cero es inifinita
			ShotSecs=0;
			return true;
		}
		return false;
	}
	
	public void weapon(Weapon weapon){
		this.weapon=weapon;
	}
	
	public Weapon weapon(){
		return weapon;
	}
	
	public void reset(){
		initialize();
	}
}
