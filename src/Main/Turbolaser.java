package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Turbolaser extends Projectile {
	
	private static BufferedImage image;
	
	static {
		try {
			image = ImageIO.read(Turbolaser.class.getClassLoader().getResourceAsStream("gfx/TurboLaserShot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Turbolaser(double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
		setDamagePower(4);
		createBounding(new Rectangle((int)posX, (int)posY, getImage().getWidth(), getImage().getHeight()));
	}

	public void update(double timeSinceLastFrame) {
		setPosX(getPosX() - (getSpeedX() * timeSinceLastFrame));
		setPosY(getPosY() - (getSpeedY() * timeSinceLastFrame));
		setBounding((int)getPosX(), (int)getPosY());
	}
	
	public static BufferedImage getImage() {
		return image;
	}
}