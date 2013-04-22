package com.matrunks.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.matrunks.shooter.screens.GameScreen;
import com.matrunks.shooter.screens.MenuScreen;

public class ShooterGame extends Game {
	public Screen menuScreen;
	public Screen gameScreen;
	
		@Override
		public void create() {
			Assets.load();
			
			//inicializamos las pantallas y les pasamos Game con this
			menuScreen = new MenuScreen(this); // creamos el menœ del juego, con this enviamos la instancia de 'Game'
			gameScreen = new GameScreen(this);
			
			//fijamos para empezar el videojuego la pantalla de menu principal
			setScreen(menuScreen); //fijamos la screen con mainMenu
		}
}
