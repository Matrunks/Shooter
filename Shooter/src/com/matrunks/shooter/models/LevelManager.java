package com.matrunks.shooter.models;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;

public class LevelManager {

	private int level;
	private int record=0;
	private MathUtils math;
	private Cover cover;
	private int minx=300;
	private int miny=100;
	
	public LevelManager(){
		initialize();
	}
	
	public void update(ArrayList<GameObject> objects,RagDoll rag_doll, Map map, Weapon smg){
		rag_doll.setDamage(10);
		
		rag_doll.setMaxX(level*75+minx);
		rag_doll.setMaxY(level*25+miny);
		
		rag_doll.setInicialX(minx+level*3);
		rag_doll.setInicialY(miny+level*3);
		
		if(level!=1){
			for(int i=0;i<1;i++){
				//los ‡rboles salen en posiciones aleatorias
				cover = new Cover(math.random(-50,map.width()),math.random(0,map.height()));
				objects.add(cover);
			}
		}
		
		//A–adimos recompensas de munici—n
		if(level<10){
			smg.addAmmo(5);
		}
		else {
			smg.addAmmo(10);
		}
			
		objects.add(rag_doll);	
	}
	
	public void reset(){
		this.initialize();
	}
	
	public void setRecord(){
		if(record < level){
			record=level;
		}
	}
	
	public int getRecord(){
		return record;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void incrementLevel(){
		level++;
	}
	
	private void initialize(){
		level=0;
	}
	
}
