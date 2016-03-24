package Main;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public abstract class Spaceship extends GameObject implements Destroyable {

	private List<BufferedImage> image = new ArrayList<BufferedImage>();
	private static List<BufferedImage> imageExplode = new ArrayList<BufferedImage>();
	private static final String[] imagesExplosion = {"gfx/explosionAnim01.png", "gfx/explosionAnim02.png", "gfx/explosionAnim03.png",
		"gfx/explosionAnim04.png", "gfx/explosionAnim05.png", "gfx/explosionAnim06.png",
		"gfx/explosionAnim07.png", "gfx/explosionAnim08.png", "gfx/explosionAnim09.png",
		"gfx/explosionAnim10.png", "gfx/explosionAnim11.png", "gfx/explosionAnim12.png"};
	private Rectangle bounding;
	private static BufferedImage hullHitImage;
	private int healthPoints;
	private boolean hullHit = false; 
	private static final double MAX_ANIMATIONTIME = 1d;
	private double currentAnimationTime = 0;
	private static final double MAX_HULLHIT_ANIMATIONTIME = 1d;
	private double explosionAnimationTime = 0;
	private static final double MAX_EXPLOSION_ANIMATIONTIME = 1d;
	private double hullHitAnimationTime = 0;
	private boolean alive = true;
	private boolean disposable = false;

	static {
		try {
			hullHitImage = ImageIO.read(Spaceship.class.getClassLoader().getResourceAsStream("gfx/HullHit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Spaceship(int x, int y, double speedX, double speedY, String[] images, int healthPoints) {
		super(x, y, speedX, speedY);
		this.healthPoints = healthPoints;
		swapImage(images);
		bounding = new Rectangle(x, y, image.get(0).getWidth(), image.get(0).getHeight());
	}
	
	public Rectangle getBounding() {
		return bounding;
	}
	public void setBounding(int x, int y) {
		bounding.x = x;
		bounding.y = y;
	}

	protected boolean isAlive() {
		return alive;
	}

	public boolean isDisposable() {
		return disposable;
	}

	public int getHealth() {
		return healthPoints;
	}

	public void setHealth(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public void kill() {
		swapImageExplode(imagesExplosion);
		alive = false;
	}

	public void revive() {
		alive = true;
	}

	protected void swapImage(String[] images) {
		image.clear();
		for (String s : images) {
			try {
				image.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(s)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		bounding = new Rectangle((int)getPosX(), (int)getPosY(), image.get(0).getWidth(), image.get(0).getHeight());
	}

	private void swapImageExplode(String[] images) {
		imageExplode.clear();
		for (String s : images) {
			try {
				imageExplode.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(s)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected BufferedImage getImage() {
		if (image.size() == 0 && isDisposable()) {
			return null;
		} else if (!isAlive()) { 
			for (int i = 0; i < imageExplode.size(); i++) {
				if (explosionAnimationTime < (double)(MAX_EXPLOSION_ANIMATIONTIME / imageExplode.size() * (i + 1))) {
					return imageExplode.get(i);
				}
			}
			return imageExplode.get(imageExplode.size()-1);
		} else {
			for (int i = 0; i < image.size(); i++) {
				if (currentAnimationTime < (double)(MAX_ANIMATIONTIME / image.size() * (i + 1))) {
					return image.get(i);
				}
			}
			return image.get(image.size()-1);
		}
	}

	public static BufferedImage getImageHullHit() {
		return hullHitImage;
	}

	@Override
	protected void update(double timeSinceLastFrame) {
		currentAnimationTime += timeSinceLastFrame;
		hullHitAnimationTime += timeSinceLastFrame;
		if (!isAlive()) {
			explosionAnimationTime += timeSinceLastFrame;
			if (explosionAnimationTime > MAX_EXPLOSION_ANIMATIONTIME) {
				explosionAnimationTime = 0;
				disposable = true;
			}
		}
		if (currentAnimationTime > MAX_ANIMATIONTIME) {
			currentAnimationTime = 0;
		}
		if (hullHitAnimationTime > MAX_HULLHIT_ANIMATIONTIME) {
			hullHitAnimationTime = 0;
			hullHit = false;
		}
	}

	protected void setHullHit() {
		hullHit = true;
	}

	protected boolean getHullHit() {
		return hullHit;
	}
	
	public abstract List<Projectile> getShots();
	
	protected abstract void collisionDetection();
	
	public abstract void firePrimary();
	
	public abstract void fireSecondary();
}