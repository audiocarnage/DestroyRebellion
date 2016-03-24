package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Upgrade extends GameObject implements Collectable {

	private static BufferedImage image;
	private Collectable content;
	private Rectangle bounding;

	static {
		try {
			image = ImageIO.read(Upgrade.class.getClassLoader().getResourceAsStream("gfx/metalcrate.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Upgrade(Collectable content, double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
		this.content = content;
		bounding = new Rectangle((int)posX, (int)posY, image.getWidth(), image.getHeight());
	}

	@Override
	public Rectangle getBounding() {
		return bounding;
	}

	@Override
	public void setBounding(int x, int y) {
		bounding.x = x;
		bounding.y = y;
	}

	@Override
	protected void update(double timeSinceLastFrame) {
		setPosX(getPosX() - (getSpeedX() * timeSinceLastFrame));
		setPosY(getPosY() - (getSpeedY() * timeSinceLastFrame));
		setBounding((int)getPosX(), (int)getPosY());
	}
	
	public Collectable getContent() {
		return content;
	}
	
	
	public static BufferedImage getImage() {
		return image;
	}
}