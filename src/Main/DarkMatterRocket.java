package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DarkMatterRocket extends Rocket {
	
	private static BufferedImage image;
	
	static {
		try {
			image = ImageIO.read(DarkMatterRocket.class.getClassLoader().getResourceAsStream("gfx/DarkMatterRocket.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public DarkMatterRocket(double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
		setDamagePower(20);
		createBounding(new Rectangle((int)posX, (int)posY, image.getWidth(), image.getHeight()));
	}
	
	public static BufferedImage getImage() {
		return image;
	}
}