package com.rithsagea.tempera.test;

import java.awt.Color;

import com.rithsagea.tempera.ui.AdvancedTemperaApp;

public class AppTest extends AdvancedTemperaApp {

	private int x;
	
	public static void main(String[] args) {
		AppTest app = new AppTest();
		app.start();
	}
	
	public void setup() {
		background(Color.GRAY);
		size(1080, 720);
	}
	
	public void update() {
		x++;
		if(x > 1000) x = 100;
	}

	public void draw() {
		fill(Color.BLUE);
		ellipse(getWidth() / 2, getHeight() / 2, x * 2, x);
		fill(Color.RED);
		circle(getWidth() / 2, getHeight() / 2, x);
		fill(Color.GREEN);
		rect(getWidth() / 2 - x / 4, getHeight() / 2 - x / 4, x / 2, x / 2);
	}
	
	public void close() {
		System.out.println("Hello. My name is Inigo Montoya. You killed my father. Prepare to die.");
	}
}
