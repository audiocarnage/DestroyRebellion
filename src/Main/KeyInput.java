package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyInput implements KeyListener {

	private static Map<Integer, Boolean> keys = new HashMap<Integer,Boolean>();
	
	public static boolean isKeyDown(int keyCode) {
		if (keys.containsKey(keyCode)) {
			return keys.get(keyCode);
		} else {
			return false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys.put(keyCode, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys.put(keyCode, false);
	}

	// wird nicht benötigt
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}