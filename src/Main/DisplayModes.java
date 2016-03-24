package Main;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class DisplayModes {

	private static GraphicsEnvironment environment;
	private static GraphicsDevice device;
	private static DisplayMode[] dm;
	
	public static void main(String[] args) {
		getDisplayModes();
	}
	
	public static DisplayMode[] getDisplayModes() {
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		dm = device.getDisplayModes();
		//for (int i = 0; i < dm.length; i++) {
		//	System.out.println(dm[i].getWidth() + " " +  dm[i].getHeight() + " " + dm[i].getBitDepth() + " " + dm[i].getRefreshRate());
		//}
		return dm;
	}
}