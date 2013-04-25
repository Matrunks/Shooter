package com.matrunks.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

/* clase dedicada a cargar en memoria las imágenes del juego empaquetadas por Demo1Desktop */


public class Assets {
	
	public static TextureAtlas atlas; //representa al objeto que contiene la imagen grande
	public static TextureAtlas shaders;
	public static AtlasRegion background;
	public static AtlasRegion shot;
	public static AtlasRegion fase;
	public static AtlasRegion queco;
	
	public static Sound disparo;
	public static Sound hit;
	public static Sound dead;
	public static Sound pium;
	public static TextField vida;
	public static TextFieldStyle estilotexto;
	public static BitmapFont texto;
	
	
	public static void load(){
		atlas = new TextureAtlas(Gdx.files.internal("data/fondos.atlas")); //le pasamos el archivo
		background = atlas.findRegion("MainMenu"); //busca dentro del atlas el archivo background
		fase = atlas.findRegion("Fase");
		disparo = Gdx.audio.newSound (Gdx.files.internal("data/Disparo_Gunner.ogg"));
		hit = Gdx.audio.newSound (Gdx.files.internal("data/Hit.mp3"));
		dead = Gdx.audio.newSound (Gdx.files.internal("data/Dead.mp3"));
		pium = Gdx.audio.newSound (Gdx.files.internal("data/Pium.mp3"));
	}
	
	public static void dispose(){ //método para borrar de memoria las imágenes
		atlas.dispose();
	}
}
