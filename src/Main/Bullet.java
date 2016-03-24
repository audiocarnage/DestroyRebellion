package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet extends Projectile {
	
	private static BufferedImage image;
	
	static {
		try {
			image = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/singleshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bullet(double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
		setDamagePower(6);
		createBounding(new Rectangle((int)posX, (int)posY, image.getWidth(), image.getHeight()));
	}
	
	@Override
	public void update(double timeSinceLastFrame) {
		setPosX(getPosX() + (getSpeedX() * timeSinceLastFrame));
		setPosY(getPosY() + (getSpeedY() * timeSinceLastFrame));
		setBounding((int)getPosX(), (int)getPosY());
	}
	
	public static BufferedImage getImage() {
		return image;
	}
}