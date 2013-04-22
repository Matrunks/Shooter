package com.matrunks.shooter.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import java.util.ArrayList;
import com.matrunks.shooter.models.RagDoll;
import com.matrunks.shooter.Assets;
import com.matrunks.shooter.screens.GameScreen;
import com.badlogic.gdx.math.MathUtils;
import java.lang.Math;

import java.util.Collections;

public class World {
	public static RagDoll rag_doll;
	public static Player pj;
	public static LevelManager level;
	public static Map map;
	public static Collisions collisions;
	public float ShotSecs=0;
	public float RagdollReloadSecs=0;
	public static Cover cover;
	public int indice;
	public static ArrayList<GameObject> objects;
	public static ArrayList<GameObject> initialobjects;
	private MathUtils math;
	private boolean bool=false;
	
	public World(){
		level = new LevelManager();
		rag_doll = new RagDoll();
		objects = new ArrayList<GameObject>();
		initialobjects = new ArrayList<GameObject>();
		map = new Map();
		for(int i=0;i<13;i++){
			cover = new Cover(math.random(-50,map.width()),math.random(0,map.height()));
			initialobjects.add(cover);
		}
		objects = (ArrayList<GameObject>) initialobjects.clone();
		collisions = new Collisions();
		initialize();
	}
	
	public void update (float delta){
		ShotSecs+=delta; //vamos acumulando los segundos
		RagdollReloadSecs+=delta;
		//indice=objects.indexOf(rag_doll);
		//aqui metemos los cambios efectuados en los objetos
		if(Gdx.input.isTouched()){ //si es pulsada la pantalla
			if(pj.checkGun()){//Comprobamos el enfriamiento del arma
				//la c‡mara proyecta la imagen a escala de la pantalla f’sica
				GameScreen.camera.unproject(GameScreen.touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
				//obtengo los metros desde los p’xeles gracias a la c‡mara y lo guardo en touchPoint

				//if(!hitOnCovers(objects)){
				//nueva comprobaci—n, si el mu–eco no est‡ en una cobertura
				if(!rag_doll.isHidden()){
						if(hitOnRagDoll(rag_doll)){
							System.out.println("Hit");
							rag_doll.damage(10); //le hacemos da–o
							rag_doll.Freeze();
							Assets.hit.play();
						}
			    }
				Assets.disparo.play(0.3f);
			}
		}
		
		//actualizamos los datos del mu–eco y personaje
		rag_doll.update(delta);
		pj.update(delta);
		collisions.update(rag_doll,map);
		
		//Si est‡ listo y no est‡ escondido, disparar‡
		if(rag_doll.isReady() && !rag_doll.isHidden()){
			Assets.pium.play();
			Gdx.input.vibrate(500);
			pj.takeDamage(rag_doll.getDamage());
			rag_doll.Shoot();
			rag_doll.NotReady();
		}
		
		//si muere reiniciamos
		if(!rag_doll.isAlive()){
			Assets.dead.play();
			level.setRecord();
			initialize();
		}
		
		if(!pj.isAlive()){
			level.reset();
			objects = (ArrayList<GameObject>) initialobjects.clone();
			initialize();
		}
		
		//Comprobamos donde se encuentra el mu–eco y si est‡ tras cobertura cambiamos su estado
		checkRagdollCover();
		
		//Reordenamos los objetos del juego
		Collections.sort(objects);
	}
	
	public void initialize(){
		level.incrementLevel();
		pj = new Player();
		rag_doll.reset();
		level.update(objects,rag_doll,map);
		Collections.sort(objects);
	}
	
	public void checkRagdollCover(){
		bool=false;
		for(int i=0; i!=objects.size();i++){ //le sumo y resto ya que el arbol por los laterales tiene partes traspasables
			if(rag_doll.position.x > objects.get(i).position.x && rag_doll.position.x+rag_doll.width < objects.get(i).position.x+objects.get(i).width+5 && rag_doll.position.y < objects.get(i).position.y+objects.get(i).height+5 && rag_doll.position.y+5 > objects.get(i).position.y){
				if(!rag_doll.isHidden()){
					rag_doll.hide();
				}	
				bool=true;
			}
		}
		
		if(rag_doll.isHidden() && bool==false){
			rag_doll.notHide();
		}
	}
	
	public boolean hitOnObject(Vector3 touchPoint, float x, float y, int width, int height){
		if(touchPoint.x > x && touchPoint.x < x +width && touchPoint.y > y && touchPoint.y < y+height){
			return true;
		}
		return false;
	}
	
	public boolean hitOnRagDoll(RagDoll ragdoll){
		return (hitOnObject(GameScreen.touchPoint,ragdoll.position.x-30, ragdoll.position.y-30,ragdoll.width()+60, ragdoll.height()+60));
	}
	
	//actualmente no se usa esta funci—n
	public boolean hitOnCovers(ArrayList<GameObject> objects){
		for(int i=0; i!=objects.size();i++){ //le sumo y resto ya que el arbol por los laterales tiene partes traspasables
			if(objects.get(i)!=rag_doll){
				if(hitOnObject(GameScreen.touchPoint,objects.get(i).position.x+30, objects.get(i).position.y, objects.get(i).width-60, objects.get(i).height)){
					//en este if comprobamos si el mu–eco y el arbol est‡n en la misma x, evaluamos la y, si el mu–eco est‡ por debajo del arbol devolvemos false (la covertura no le cubre)
					if(rag_doll.position.x > objects.get(i).position.x && rag_doll.position.x < objects.get(i).position.x + objects.get(i).width && rag_doll.position.y < objects.get(i).position.y){
						return false;
					}
					else{
						System.out.println("Cover");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void dispose(){
		//limpiamos todos los objetos
		for(int i=0; i !=World.objects.size(); i++){
			objects.get(i).dispose();
		}
	}
}


