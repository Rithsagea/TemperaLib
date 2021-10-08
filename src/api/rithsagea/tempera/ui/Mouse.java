package api.rithsagea.tempera.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Reads whenever mouse buttons or the scroll wheel is changed.
 * Whenever a mouse button is pushed down, the button's code
 * is incremented through each call of the poll() function.
 * The mouse wheel's notches are also updated each time poll()
 * is called
 * 
 * @author rithsagea
 *
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int BUTTON_COUNT = 3;
	
	private Point mousePos;
	private Point currentPos;
	private boolean[] buttons;
	private int[] duration;
	private int notches;
	private int polledNotches;
	
	public Mouse() {
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		buttons = new boolean[BUTTON_COUNT];
		duration = new int[BUTTON_COUNT];
	}
	
	protected synchronized void poll() {
		mousePos = new Point(currentPos);
		
		polledNotches = notches;
		notches = 0;
		
		for(int button = 0; button < buttons.length; button++) {
			if(buttons[button]) {
				duration[button]++;
			} else {
				duration[button] = 0;
			}
		}
	}
	
	/**
	 * Gets the current position of the mouse
	 * @return	where the mouse is on the window
	 */
	public Point getPosition() {
		return mousePos;
	}
	
	/**
	 * Gets which notch the mouse scroll wheel is currently on
	 * @return	which notch the scroll wheel is on
	 */
	public int getNotches() {
		return polledNotches;
	}
	
	/**
	 * Checks if a button is being held down
	 * @param button	the code of the button to check
	 * @return			whether the button is being held down
	 */
	public boolean buttonDown(int button) {
		return duration[button - 1] > 0;
	}
	
	/**
	 * Checks if this is the first frame a button is being held down
	 * @param button	the code of the button to check
	 * @return			if this button was pressed this frame
	 */
	public boolean buttonDownOnce(int button) {
		return duration[button - 1] == 1;
	}
	
	/**
	 * Gets how many ticks this button has been held down for
	 * @param button	the code of the button to check
	 * @return			how many ticks the button has been held for
	 */
	public int buttonDownFrames(int button) {
		return duration[button - 1];
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton() - 1;
		if(button >= 0 && button < buttons.length) {
			buttons[button] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int button = e.getButton() - 1;
		if(button >= 0 && button < buttons.length) {
			buttons[button] = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	//TODO investigate if this actually does anything important
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		notches += e.getWheelRotation();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// this method is kind of useless,
		// as it's a combination of mousepressed
		// and mousereleased
	}

}
