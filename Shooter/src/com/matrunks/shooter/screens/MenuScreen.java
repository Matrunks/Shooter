package com.matrunks.shooter.screens;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Json;
import com.matrunks.shooter.Assets;
import com.matrunks.shooter.ShooterGame;

class Aplayer
{
	public String name, score;
}

public class MenuScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	Game game;
	private BoundingBox salirBox;
	private BoundingBox nuevaBox;
	
	private Vector3 touchPoint;
	
	public MenuScreen(Game game){
		this.game=game;
		
		camera = new OrthographicCamera(1280, 800);
		camera.position.set(1280/2, 800/2, 0);
		salirBox = new BoundingBox(new Vector3(500,125,0), new Vector3(780,275,0));
		nuevaBox = new BoundingBox(new Vector3(250,390,0), new Vector3(1100,425,0));
		batch = new SpriteBatch();
		
		touchPoint = new Vector3();
	}

	@Override
	public void dispose() {
		batch.dispose();
		Assets.dispose();
	}

	@Override
	public void render (float delta) {	
		System.out.println("[CAMERA] "+ Gdx.graphics.getWidth()+ " " + Gdx.graphics.getHeight());
		if(Gdx.input.justTouched()){ //si se ha pulsado la pantalla
			//la c�mara proyecta la imagen a escala de la pantalla f�sica
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
			//obtengo los metros desde los p�xeles gracias a la c�mara y lo guardo en touchPoint
			
			if(salirBox.contains(touchPoint)){//si el rect�ngulo pulsado contiene las
				//coordenadas del touchPoint, es que he tocado el bot�n start
				System.out.println("Salir");
				Assets.dispose();//para que dejemos de dibujar y no siga ejecutandose �sta pantalla
				Gdx.app.exit();
			}	
			
			if(nuevaBox.contains(touchPoint)){
				game.setScreen(((ShooterGame)game).gameScreen);
			}
		}
		
		GL20 gl = Gdx.graphics.getGL20();
		//gl.glClearColor(0, 0, 0, 1);
		//gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.background,0,0, 1280, 800);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
