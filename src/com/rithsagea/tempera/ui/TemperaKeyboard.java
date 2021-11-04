package com.rithsagea.tempera.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Reads whenever a key is pressed down or released from
 * implementing KeyListener. Whenever a key is pressed, it's
 * key code is logged as being held down. Each tick poll() is
 * called, incrementing the duration of all pressed keys by
 * 1 and resetting the durations of all released keys. If the
 * duration of a key is 1, it was just pressed that tick.
 * 
 * @author rithsagea
 */
public class TemperaKeyboard implements KeyListener {
	
	private boolean[] keys;
	private int[] duration;
	
	public TemperaKeyboard() {
		keys = new boolean[255];
		duration = new int[256];
	}
	
	/**
	 * Checks if the key is currently being held down
	 * @param keyCode	the key code of the key to check
	 * @return			whether the key is being held down
	 */
	public boolean keyDown(int keyCode) {
		return duration[keyCode] > 0;
	}
	
	/**
	 * Checks if this is the first tick a key is being held down
	 * @param keyCode	the key code of the key to check
	 * @return			whether this is the first tick of a key being held down
	 */
	public boolean keyDownOnce(int keyCode) {
		return duration[keyCode] == 1;
	}
	
	/**
	 * Gets how many ticks this key has been held down for
	 * @param keyCode	the key code of the key to check
	 * @return			how many ticks the key has been held down for
	 */
	public int keyDownTicks(int keyCode) {
		return duration[keyCode];
	}
	
	/**
	 * Increments the duration each key has been held for
	 * when this function is run.<br>Run this once every tick. 
	 */
	protected synchronized void poll() {
		for(int key = 0; key < keys.length; key++) {
			if(keys[key]) {
				duration[key]++;
			} else {
				duration[key] = 0;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >= 0 && keyCode <= keys.length) {
			keys[keyCode] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >= 0 && keyCode <= keys.length) {
			keys[keyCode] = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// this method is kind of a combination of
		// keyPressed and keyReleased.
		// not actually needed for anything
	}
}
