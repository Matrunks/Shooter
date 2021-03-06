package com.matrunks.shooter.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.matrunks.shooter.Assets;
import com.matrunks.shooter.screens.GameScreen;

public class World {
	public static RagDoll rag_doll;
	public static Player pj;
	public static LevelManager level;
	public static Map map;
	public static Collisions collisions;
	public static Gun gun;
	public static SubMachineGun smg;
	public float ShotSecs=0;
	public float RagdollReloadSecs=0;
	public static Cover cover;
	public int indice;
	public static ArrayList<GameObject> gameobjects;
	public static ArrayList<GameObject> initialobjects;
	public static ArrayList<Shader> shaders;
	public static ArrayList<Objects> objects;
	private BoundingBox gunBox;
	private BoundingBox smgBox;
	private BoundingBox quitBox;
	public Shader shot;
	public HUD healthHud;
	public HUD selectorHud;
	public Lifebar lifeBar;
	public Bar bar;
	private MathUtils math;
	private boolean turn=false;
	private boolean bool=false;
	
	public World(){
		//Objects
		level = new LevelManager();
		pj = new Player();
		healthHud = new HUD(1);
		selectorHud = new HUD(2);
		
		//Creating others objects
		map = new Map();
		bar = new Bar();
		lifeBar= new Lifebar();
		rag_doll = new RagDoll(map);
		
		//ArrayLists
		gameobjects = new ArrayList<GameObject>();
		initialobjects = new ArrayList<GameObject>();
		shaders = new ArrayList<Shader>();
		objects = new ArrayList<Objects>();
		
		//Bounding Box
		gunBox = new BoundingBox(new Vector3(0,0,0), new Vector3(100,95,0));
		smgBox = new BoundingBox(new Vector3(0,95,0), new Vector3(100,220,0));
		
		//Weapons
		gun = new Gun();
		smg = new SubMachineGun();
		
		//Inserciones al arraylist
		objects.add(gun);
		objects.add(smg);
		objects.add(healthHud);
		objects.add(lifeBar);
		objects.add(bar);
		objects.add(selectorHud);
		Collections.sort(objects);
		
		pj.weapon(gun);
		for(int i=0;i<13;i++){
			cover = new Cover(math.random(-50,map.width()),math.random(0,map.height()));
			initialobjects.add(cover);
		}
		gameobjects = (ArrayList<GameObject>) initialobjects.clone();
		collisions = new Collisions();
		initialize();
	}
	
	public void update (float delta){
		ShotSecs+=delta; //vamos acumulando los segundos
		RagdollReloadSecs+=delta;
		
		//aqui metemos los cambios efectuados en los objetos
		if(Gdx.input.isTouched()){ //si es pulsada la pantalla
			//la c�mara proyecta la imagen a escala de la pantalla f�sica
			GameScreen.camera.unproject(GameScreen.touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
			//obtengo los metros desde los p�xeles gracias a la c�mara y lo guardo en touchPoint
			
			if(checkWeaponsBox(GameScreen.touchPoint)){
				//reproducir alg�n sonido o algo de cambio de arma
			}
			else if(pj.checkGun()){//Comprobamos el enfriamiento del arma		

				//si el mu�eco no est� en una cobertura
				if(!rag_doll.isHidden()){
						if(hitOnRagDoll(rag_doll)){
							System.out.println("Hit");
							rag_doll.damage(pj.weapon().damage()); //le hacemos da�o tanto como el arma equipada
							rag_doll.Freeze();
							Assets.hit.play();
							turn=true;
						}
			    }
				if(turn == false){
					Cover c=hitOnCovers(gameobjects);
					if(c!=null){
						c.damage(pj.weapon().damage());
						if(c.health()<=0){
							//Quitamos los impactos de bala que estén ahí
							removeShaders(c.position(), c.width(), c.height());
							gameobjects.remove(c);
							//TODO add sound
							Collections.sort(gameobjects);
						}
					}
				}
				
				//si no hemos dado al queco, disparo con shader
				if(turn==false && GameScreen.touchPoint.y < map.height()){ //compruebo no dejar marca en el aire
						shot = new Shader(0, (int)GameScreen.touchPoint.x, (int)GameScreen.touchPoint.y);
						shaders.add(shot);
				}
				Assets.disparo.play(0.3f); //sonido de disparo
				pj.weapon().shot(); //descontamos la bala del cargador
				bar.setWidth(smg.ammo());
				turn=false;
			}
		}
		
		//actualizamos los datos del mu�eco y personaje
		rag_doll.update(delta);
		pj.update(delta);
		collisions.update(rag_doll,map);
		
		//Si est� listo y no est� escondido, disparar�
		if(rag_doll.isReady() && !rag_doll.isHidden()){
			Assets.pium.play();
			Gdx.input.vibrate(500);
			pj.takeDamage(rag_doll.getDamage());
			rag_doll.Shoot();
			rag_doll.NotReady();
			lifeBar.setWidth(pj.health());
		}
		
		//si muere el queco aumentamos el nivel
		if(!rag_doll.isAlive()){
			Assets.dead.play();
			level.setRecord();
			pj.initialize(); //inicializamos la vida del jugador
			initialize();
		}
		
		//si muere el jugador, pedimos puntuaci�n y reiniciamos el nivel
		if(!pj.isAlive()){
			TextInput listener = new TextInput (level.getRecord ());
			Gdx.input.getTextInput(listener, "Introduce tu nombre", "");
			
			smg.reset();
			level.reset();
			pj.reset(); //reseteamos al jugador, le damos otra vez municion
			gameobjects = (ArrayList<GameObject>) initialobjects.clone(); //necesitamos los objetos como al principio
			initialize();
		}
		
		//Comprobamos donde se encuentra el mu�eco y si est� tras cobertura cambiamos su estado
		checkRagdollCover();
		
		//Reordenamos los objetos del juego
		Collections.sort(gameobjects);
	}
	
	public void initialize(){
		lifeBar.setWidth(100);
		shaders.clear();
		level.incrementLevel(); //se incrementa el nivel ya que level empieza en 0
		rag_doll.reset();
		level.update(gameobjects,rag_doll,map,smg); //hacemos un update de los �rboles mu�eco y mapa
		bar.setWidth(smg.ammo());
		Collections.sort(gameobjects); //ordenamos los objetos
	}
	
	public void checkRagdollCover(){
		bool=false;
		for(int i=0; i!=gameobjects.size();i++){ //le sumo y resto ya que el arbol por los laterales tiene partes traspasables
			if(rag_doll.position.x > gameobjects.get(i).position.x && rag_doll.position.x+rag_doll.width < gameobjects.get(i).position.x+gameobjects.get(i).width+5 && rag_doll.position.y < gameobjects.get(i).position.y+gameobjects.get(i).height+5 && rag_doll.position.y+5 > gameobjects.get(i).position.y){
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
	
	public Cover hitOnCovers(ArrayList<GameObject> objects){
		for(int i=0; i!=objects.size();i++){ 
			if(objects.get(i)!=rag_doll){ //le sumo y resto ya que el arbol por los laterales tiene partes traspasables
				if(hitOnObject(GameScreen.touchPoint,objects.get(i).position.x+30, objects.get(i).position.y, objects.get(i).width-60, objects.get(i).height)){
					//en este if comprobamos si el mu–eco y el arbol est‡n en la misma x, evaluamos la y, si el mu–eco est‡ por debajo del arbol devolvemos false (la covertura no le cubre)
					if(rag_doll.position.x > objects.get(i).position.x && rag_doll.position.x < objects.get(i).position.x + objects.get(i).width && rag_doll.position.y < objects.get(i).position.y){
						return null;
					}
					else{
						System.out.println("Cover");
						return (Cover) objects.get(i);
					}
				}
			}
		}
		return null;
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
	
	public void dispose(){
		//limpiamos todos los objetos
		for(int i=0; i !=gameobjects.size(); i++){
			gameobjects.get(i).dispose();
		}
		for(int i=0; i !=initialobjects.size();i++){
			initialobjects.get(i).dispose();
		}
		for(int i=0; i !=shaders.size(); i++){
			shaders.get(i).dispose();
		}
	}
	
	public static ArrayList<GameObject> gameobjects(){
		return gameobjects;
	}
	
	public static ArrayList<Shader> shaders(){
		return shaders;
	}
	
	public static ArrayList<Objects> objects(){
		return objects;
	}
	
	public boolean checkWeaponsBox(Vector3 touchPoint){
		if(gunBox.contains(touchPoint)){ //si hemos tocado la caja de la pistola
			pj.weapon(gun);
			selectorHud.setY(-5);
			return true;
		}
		else if(smgBox.contains(touchPoint)){ //si hemos tocado la caja del smg
			pj.weapon(smg);
			selectorHud.setY(100);
			return true;
		}
		else if(!(pj.weapon()==gun) && pj.weapon().ammo()==0){ //Si tenemos un arma diferente a la pistola, y no tenemos balas de otras cambiamos directamente a pistola
			pj.weapon(gun); //hacemos el cambio
			selectorHud.setY(-5);
		}
		return false;
	}

	public void removeShaders(Vector3 position, int width, int height){
		
		for(int i=0; i < shaders.size(); i++){
			Shader s= shaders.get(i);
			if(s.position().x <= position.x+width && s.position().x >= position.x && s.position().y <= position.y + height && s.position().y >= position.y){
				shaders.remove(i);
				//Collections.sort(shaders);
				i=0;
			}
		}
	}

}

