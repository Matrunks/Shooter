package com.matrunks.shooter.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.matrunks.shooter.Assets;
import com.matrunks.shooter.models.Shader;
import com.matrunks.shooter.models.World;

public class GameScreen implements Screen{
	Game game;
	World world;
	SpriteBatch batch;
	public static Vector3 touchPoint;
	public static OrthographicCamera camera;
	private BitmapFont white;
		
	public GameScreen (Game game){
		this.game=game;
		world = new World();
		touchPoint= new Vector3();
		
		camera = new OrthographicCamera(1280,800); //metros, cuadrados, cogemos toda la pantalla
		camera.position.set(1280/2f, 800/2f, 0); //la camara mirar‡ a la mitad de la pantalla, z=0
		batch = new SpriteBatch(); //ocupa mucha memoria, debe de tenerse uno m‡ximo por pantalla

	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		GL20 gl = Gdx.graphics.getGL20();
		//gl.glClearColor(1, 0, 0, 1);
		//gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.update(delta);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.fase, 0, 0, 1280, 800);
		batch.end();
		
		//Queco
		batch.enableBlending();
		batch.begin();
		
		for(int i=0; i !=World.objects.size(); i++){
			batch.draw(World.objects().get(i).image(),World.objects().get(i).position.x, World.objects().get(i).position.y,World.objects().get(i).width,World.objects().get(i).height);
		}
		
		for(int i=0; i !=World.shaders.size(); i++){
			batch.draw(World.shaders().get(i).image(),World.shaders().get(i).position.x, World.shaders().get(i).position.y,World.shaders().get(i).width,World.shaders().get(i).height);
		}
		
		white.draw(batch, "Jugador: "+world.pj.health(), 1000, 750);
		white.draw(batch, "Agapito: "+world.rag_doll.health(), 1000, 700);
		white.draw(batch, "Nivel: "+world.level.getLevel(), 1000, 650);
		white.draw(batch, "Record: "+world.level.getRecord(), 1000,600);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		white=new BitmapFont(Gdx.files.internal("data/whitefont.fnt"),false);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		white.dispose();
		world.dispose();
	}

}
