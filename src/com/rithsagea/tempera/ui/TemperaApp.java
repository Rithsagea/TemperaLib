package com.rithsagea.tempera.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public abstract class TemperaApp implements Runnable {
	
	private volatile boolean running;
	private Thread appThread;
	
	protected JFrame frame;
	protected Canvas canvas;
	private BufferStrategy bs;
	
	protected Keyboard keyboard;
	protected Mouse mouse;
	
	public TemperaApp() {
		keyboard = new Keyboard();
		mouse = new Mouse();
	}
	
	private void init() {
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		//TODO remove these
		setup();
		
		frame = new JFrame();
		frame.getContentPane().add(canvas);
		frame.setTitle("Game");
		frame.setIgnoreRepaint(true);
		frame.pack();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onWindowClose();
			}
		});
		
		canvas.addKeyListener(keyboard);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		frame.setVisible(true);
		
		appThread = new Thread(this);
		appThread.start();
	}
	
	private void appLoop() {
		processInput();
		update();
		renderFrame();
	}
	
	private void processInput() {
		keyboard.poll();
		mouse.poll();
	}
	
	private void renderFrame() {
		do {
			do {
				Graphics g = null;
				try {
					//gets the current graphics object
					g = bs.getDrawGraphics();
					//clears the graphics (canvas)
					g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
					//draws stuff to the graphics
					render(g);
				} finally {
					if(g != null) {
						g.dispose();
					}
				}
			} while(bs.contentsRestored());
			bs.show();
		} while(bs.contentsLost());
	}

	private void onWindowClose() {
		try {
			onClose();
			running = false;
			appThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public final void start() {
		SwingUtilities.invokeLater(() -> {
			init();
		});
	}
	
	@Override
	public final void run() {
		running = true;
		while(running) {
			appLoop();
		}
	}
	
	// accessors and mutators
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	// external stuff
	
	public abstract void setup();
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public void onClose() {
		System.out.println("Stopping App");
	}
}
