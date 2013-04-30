package com.matrunks.shooter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ShooterDesktop{
	
	public static void main(String[] args){
		// TODO Auto-generated method stub

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Shooter";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 800;
		cfg.fullscreen = false;
		
		new LwjglApplication(new ShooterGame(), cfg);
	}
}
