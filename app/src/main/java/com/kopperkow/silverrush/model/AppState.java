package com.kopperkow.silverrush.model;

import java.util.ArrayList;

import android.app.Application;

public class AppState extends Application {
	
	private ArrayList<Info> myEvents;
	
	public void load(){
		
	}
	
	public Info getInfo(int num){
		return myEvents.get(num);
	}
	
}
