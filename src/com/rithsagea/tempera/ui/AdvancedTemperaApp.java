package com.rithsagea.tempera.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public abstract class AdvancedTemperaApp extends TemperaApp {

	private Graphics2D g;
	
	private Color strokeColor;
	private Color fillColor;
	
	private double strokeWeight = 1.0d;
	private int strokeStyle = BasicStroke.CAP_SQUARE;
	private int strokeJoin = BasicStroke.JOIN_MITER;
	
	@Override
	public final void draw(Graphics g) {
		this.g = (Graphics2D) g;
		draw();
	}
	
	public abstract void draw();
	
	private void drawShape(Shape shape) {
		if(g == null) return;
		g.setPaint(fillColor);
		g.fill(shape);
		g.setPaint(strokeColor);
		g.draw(shape);
	}
	
	public final void ellipse(double x, double y, double w, double h) {
		drawShape(new Ellipse2D.Double(x - w / 2, y - h / 2, w, h));
	}
	
	public final void circle(double x, double y, double d) {
		ellipse(x, y, d, d);
	}
	
	public final void rect(double x, double y, double w, double h) {
		drawShape(new Rectangle2D.Double(x, y, w, h));
	}
	
	public final void fill(Color color) {
		fillColor = color;
	}
	
	public final void stroke(Color color) {
		strokeColor = color;
	}
	
	private void updateStroke() {
		if(g == null) return;
		g.setStroke(new BasicStroke((float) strokeWeight, strokeStyle, strokeJoin));
	}
	
	public final void strokeWeight(double weight) {
		strokeWeight = weight;
		updateStroke();
	}
	
	//See BasicStroke static fields
	public final void strokeStyle(int style) {
		strokeStyle = style;
		updateStroke();
	}
	
	public final void strokeJoin(int join) {
		strokeJoin = join;
		updateStroke();
	}
	
	public final void background(Color color) {
		canvas.setBackground(color);
	}
	
	public final void size(int width, int height) {
		canvas.setSize(width, height);
	}
}
