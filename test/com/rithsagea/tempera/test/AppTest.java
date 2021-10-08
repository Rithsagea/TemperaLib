package com.rithsagea.tempera.test;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import api.rithsagea.tempera.ui.TemperaApp;

public class AppTest extends TemperaApp {

	private int x;
	
	public static void main(String[] args) {
		AppTest app = new AppTest();
		app.start();
	}
	
	@Override
	public void update() {
		x++;
		if(x > 200) x = 100;
	}

	@Override
	public void render(Graphics2D g) {
		g.fill(new Rectangle(x, x, x, x));
	}
	
	@Override
	public void onClose() {
		System.out.println("Hello. My name is Inigo Montoya. You killed my father. Prepare to die.");
	}

}
