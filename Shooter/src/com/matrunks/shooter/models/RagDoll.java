package com.matrunks.shooter.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import java.lang.Math;

public class RagDoll extends GameObject{

	public Vector3 speed;
	public static TextureAtlas atlas;
	public float HitSecs=0;
	public float ReloadSecs=0;
	public float ShootingSecs=0;
	private static AtlasRegion posture1;
	private static AtlasRegion posture2;
	private boolean frozen=false;
	private boolean ready=false;
	private int hp;
	private int inicialx=300;
	private int inicialy=100;
	private int max_x;
	private int max_y;
	private int damage;
	private int directionx;
	private int directiony;
	private boolean hidden=false;
	private boolean shooting=false;
	private MathUtils math;
	private Map map;
	
	public RagDoll(Map map){
		this.map=map;
		width=100;
		height=200;
		atlas = new TextureAtlas(Gdx.files.internal("data/ragdoll.atlas"));
		posture1 = atlas.findRegion("posture1");
		posture2 = atlas.findRegion("posture2");
		directionx=1;
		directiony=1;
		
		position = new Vector3(200,0,0);
		speed = new Vector3(inicialx,inicialy,0);
		hp=100;
	}
	
	public void reset(){
		position = new Vector3(math.random(map.minX(),map.width()),math.random(0,map.height()),0);
		hp=100;
		speed = new Vector3(inicialx,inicialy,0);
		directionx*=-1;
		directiony*=-1;
	}
	
	public void dispose(){
		atlas.dispose();
	}
	
	public void update(float delta){
		this.checkReload(delta);
	
		//vamos moviendo al ragdoll
		if(this.checkFrozen(delta) && this.checkShooting(delta)){
			position.add((int)(speed.x*directionx*delta-(position.y/70)), (int)(speed.y*directiony*delta),0);
			//modificamos su altura y anchura al alejarse
			height=200-(int)(position.y/3.5);
			width=100-(int)(position.y/7);
		}
		
		if(speed.x<max_x){
			speed.x+=delta*100;
		}
		
		if(speed.y<max_y){
			speed.y+=delta*100;
		}
	}
	
	public boolean checkShooting(float delta){
		if(this.isShooting() && ShootingSecs < 0.40){
			ShootingSecs+=delta;
			return false;
		}
		else if(this.isShooting() && ShootingSecs > 0.40){
			ShootingSecs=0;
			shooting=false;
			return true;
		}
		return true;
	}
	
	public boolean checkFrozen (float delta){
		if(this.isFrozen() && HitSecs < 0.20){
			HitSecs+=delta;
			return false;
		}
		else if(this.isFrozen() && HitSecs > 0.20){
			HitSecs=0;
			frozen=false;
			return true;
		}
		return true;
	}
	
	public boolean checkReload(float delta){
		if(ReloadSecs>2){//si est‡ listo para disparar
			ReloadSecs=0;
			this.Ready();
			System.out.println("Ready");
			return true;
		}
		else if(!this.isReady()){
			ReloadSecs+=delta;
			return false;
		}
		return false;
	}
	
	public void damage(int d){
		hp-=d;
	}
	
	public AtlasRegion image(){
		if(this.isFrozen()){
			return posture2;
		}
		else
			return posture1;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public void changeDirectionX(){
		/*if ( Math.abs(direction.x) < max_x){
			direction.x *=-math.random(1f,1.5f);
		}
		else
			direction.x *= -math.random(0.7f,1f);*/
		
		directionx*=-1;
	}
	
	public void changeDirectionY(){
		/*if (Math.abs(direction.y) < max_y){
			direction.y *=-math.random(1f,1.5f);
		}
		else
			direction.y *= -math.random(0.7f,1f);*/
		
		directiony*=-1;
	}
	
	public boolean isAlive (){
		return hp>0;
	}
	
	public void hide(){
		System.out.println("A cubierto");
		hidden=true;
	}
	
	public void notHide(){
		System.out.println("Expuesto");
		hidden=false;
	}
	
	public boolean isHidden(){
		return hidden;
	}

	public boolean isFrozen(){
		return frozen;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void Ready(){
		ready=true;
	}
	
	public void NotReady(){
		ready=false;
	}
	
	public void Freeze(){
		ReloadSecs=0; //lo pongo a 0 por que si recibe un disparo no debe de atacar tan pronto
		
		if(speed.x>inicialx || speed.y>inicialy){
			speed.set(speed.x/1.2f,speed.y/1.3f,0);
		}
		
		frozen=true;
	}
	
	public void Shoot(){
		shooting=true;
	}
	
	public boolean isShooting(){
		return shooting;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	
	public void setX(float x){
		position.x=x;
	}
	
	public void setY(float y){
		position.y=y;
	}
	
	public int health(){
		return hp;
	}
	
	public void setDamage(int damage){
		this.damage=damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setMaxX(int x){
		max_x=x;
	}
	
	public void setInicialX(int x){
		inicialx=x;
	}
	
	public void setInicialY(int y){
		inicialy=y;
	}
	
	public int getMaxX(){
		return max_x;
	}
	
	public void setMaxY(int y){
		max_y=y;
	}
	
	public int getMaxY(){
		return max_y;
	}
}
