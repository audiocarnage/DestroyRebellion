package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Background {	
	
	private BufferedImage image;
	private double posX;
	private double speed;
	
	public Background(double speed, String imagePath) {
		this.speed = speed;
		setBackground(imagePath);
	}
	
	public void update(double timeSinceLastFrame) {
		posX -= speed * timeSinceLastFrame;
		if (posX < -image.getWidth()) {
			posX += image.getWidth();
		}
	}
	
	public int getPositionX() {
		return (int)posX;
	}
	
	public BufferedImage getBackground() {
		return image;
	}
	
	
	private void setBackground(String imagePath) {
		try {
			image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}