package com.matrunks.shooter.models;

import com.badlogic.gdx.math.Vector3;

public class Lifebar extends Bar {
	
	public Lifebar(){
		position = new Vector3(137,751,0);
		height=25;
		width=425;
	}
	
	public void setWidth(int hp){
		width=(int)(hp*4.25);
	}

}
