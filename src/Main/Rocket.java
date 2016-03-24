package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Rocket extends Missile {

	private static List<BufferedImage> image = new ArrayList<BufferedImage>();
	private static String[] images = {"gfx/rocket2a.png", "gfx/rocket2b.png", "gfx/rocket2c.png", "gfx/rocket2d.png"};
	private static final double MAX_ANIMATIONTIME = 1d;
	private static double currentAnimationTime = 0;
	
	static {
		try {
			for (String s : images) {
				image.add(ImageIO.read(Rocket.class.getClassLoader().getResourceAsStream(s)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rocket(double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
		setDamagePower(48);
		createBounding(new Rectangle((int)posX, (int)posY, image.get(0).getWidth(), image.get(0).getHeight()));
	}

	@Override
	public void update(double timeSinceLastFrame) {
		currentAnimationTime += timeSinceLastFrame;
		if (currentAnimationTime > MAX_ANIMATIONTIME) {
			currentAnimationTime = 0;
		}
		setPosX(getPosX() + (getSpeedX() * timeSinceLastFrame));
		setPosY(getPosY() + (getSpeedY() * timeSinceLastFrame));
		setBounding((int)getPosX(), (int)getPosY());
	}

	public static BufferedImage getImage() {
		if (image.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < image.size(); i++) {
				if (currentAnimationTime < (double)(MAX_ANIMATIONTIME / image.size() * (i + 1))) {
					return image.get(i);
				}
			}
			return image.get(image.size()-1);
		}
	}
	
	public static int getImageWidth() {
		if (image.size() == 0) {
			return 0;
		} else {
			return image.get(0).getWidth();
		}
	}
	
	public static int getImageHeight() {
		if (image.size() == 0) {
			return 0;
		} else {
			return image.get(0).getHeight();
		}
	}
}