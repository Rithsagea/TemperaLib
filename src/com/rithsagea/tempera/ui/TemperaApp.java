package com.rithsagea.tempera.ui;

import java.awt.Canvas;
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
	
	protected TemperaKeyboard keyboard;
	protected TemperaMouse mouse;
	
	private int frameRate;
	private int tickRate;
	
	private long nanosPerFrame;
	private long nanosPerTick;
	private long lastFrame;
	private long lastTick;
	
	public TemperaApp() {
		keyboard = new TemperaKeyboard();
		mouse = new TemperaMouse();
	}
	
	private void init() {
		setTickRate(0);
		setFrameRate(0);
		
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
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
	
	private void loop() {
		
		long time = System.nanoTime();
		
		if((time - lastTick) > nanosPerTick) {
			input();
			update();
			
			lastTick = time;
		}
		
		if((time - lastFrame) > nanosPerFrame) {
			render();
			
			lastFrame = time;
		}
	}
	
	private void input() {
		keyboard.poll();
		mouse.poll();
	}
	
	private void render() {
		do {
			do {
				Graphics g = null;
				try {
					g = bs.getDrawGraphics();
					g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
					draw(g);
				} finally {
					if(g != null) g.dispose();
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
			loop();
		}
	}
	
	// accessors and mutators
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public int getTickRate() {
		return tickRate;
	}
	
	public int getFrameRate() {
		return frameRate;
	}
	
	public void setBounds(int width, int height) {
		canvas.setSize(width, height);
	}
	
	public void setTickRate(int tickRate) {
		this.tickRate = tickRate;
		nanosPerTick = tickRate == 0 ? 0 : 1000000000 / tickRate;
	}
	
	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
		nanosPerFrame = frameRate == 0 ? 0 : 1000000000 / frameRate;
	}
	
	// external stuff
	
	public abstract void setup();
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public void onClose() {
		System.out.println("Stopping App");
	}
}
