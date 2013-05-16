package com.matrunks.shooter.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.matrunks.shooter.Assets;
import com.matrunks.shooter.models.Shader;
import com.matrunks.shooter.models.Weapon;
import com.matrunks.shooter.models.World;

public class GameScreen implements Screen{
	Game game;
	World world;
	SpriteBatch batch;
	public static Vector3 touchPoint;
	public static OrthographicCamera camera;
	private BitmapFont white;
	ShapeRenderer sr;
		
	public GameScreen (Game game){
		this.game=game;
		world = new World();
		touchPoint= new Vector3();
		
		camera = new OrthographicCamera(1280,800); //metros, cuadrados, cogemos toda la pantalla
		camera.position.set(1280/2f, 800/2f, 0); //la camara mirar� a la mitad de la pantalla, z=0
		batch = new SpriteBatch(); //ocupa mucha memoria, debe de tenerse uno m�ximo por pantalla
		sr = new ShapeRenderer();
		sr.setColor(Color.YELLOW);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		GL20 gl = Gdx.graphics.getGL20();
		//gl.glClearColor(1, 0, 0, 1);
		//gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.update(delta);
		
		camera.update();
		
		//System.out.println("[CAMERA] "+ Gdx.graphics.getWidth()+ " " + Gdx.graphics.getHeight());
		
		batch.setProjectionMatrix(camera.combined);
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.fase, 0, 0, 1280, 800);
		batch.end();
		
		//Queco
		batch.enableBlending();
		batch.begin();
		
		//Mostramos los objetos de juego
		for(int i=0; i !=World.gameobjects().size(); i++){
			batch.draw(World.gameobjects().get(i).image(),World.gameobjects().get(i).position.x, World.gameobjects().get(i).position.y,World.gameobjects().get(i).width(),World.gameobjects().get(i).height());
		}
		
		//Mostramos los shaders
		for(int i=0; i !=World.shaders.size(); i++){
			batch.draw(World.shaders().get(i).image(),World.shaders().get(i).position.x, World.shaders().get(i).position.y,World.shaders().get(i).width(),World.shaders().get(i).height());
		}
		
		//Mostramos el resto de objetos
		for(int i=0; i !=World.objects.size(); i++){
			batch.draw(World.objects().get(i).image(),World.objects().get(i).position.x, World.objects().get(i).position.y,World.objects().get(i).width(),World.objects().get(i).height());
		}
		//Mostramos las balas del arma
		
		white.draw(batch, ""+world.level.getLevel(), 180, 688);
		
		batch.end();
		
		
		// Con este c�digo puedo ver la caja de agapito
		/*sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Line);
		sr.rect(world.rag_doll.position.x-30, world.rag_doll.position.y-30, world.rag_doll.width+60, world.rag_doll.height+60);
		sr.end();*/
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		white=new BitmapFont(Gdx.files.internal("data/whitefont.fnt"),false);
		white.scale((float)0.5);
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
