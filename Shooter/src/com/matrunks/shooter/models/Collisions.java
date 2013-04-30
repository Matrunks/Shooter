package com.matrunks.shooter.models;

public class Collisions {

	
	
	public Collisions (){
		
	}
	
	//comprueba las colisiones del momento y modifica los par‡metros
	public void update(RagDoll rag_doll, Map map){
		//comprobamos las colisiones
				if(rag_doll.getX() > map.width() - rag_doll.width){
					rag_doll.setX(map.width()-rag_doll.width);
					rag_doll.changeDirectionX();
				}
				if(rag_doll.getX() < map.minX()){
					rag_doll.setX(map.minX());
					rag_doll.changeDirectionX();
				}
				if(rag_doll.getY() > map.height()){
					rag_doll.setY(map.height());
					rag_doll.changeDirectionY();
				}
				if(rag_doll.getY() < 0){
					rag_doll.setY(0);
					rag_doll.changeDirectionY();
				}
	}
}
