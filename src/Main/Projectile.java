package Main;

import java.awt.Rectangle;

public abstract class Projectile extends GameObject {

	private int damage = 0;
	private Rectangle bounding;

	public Projectile(double posX, double posY, double speedX, double speedY) {
		super(posX, posY, speedX, speedY);
	}
	
	protected void createBounding(Rectangle bounding) {
		this.bounding = bounding;
	}
	
	public Rectangle getBounding() {
		return bounding;
	}
	
	public void setBounding(int x, int y) {
		bounding.x = x;
		bounding.y = y;
	}
	
	public int getDamagePower() {
		return damage;
	}
	
	public void setDamagePower(int damage) {
		this.damage = damage;
	}
	
	public abstract void update(double timeSinceLastFrame);
}