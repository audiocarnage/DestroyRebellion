package Main;

import java.awt.Rectangle;

public abstract class GameObject {

	private double posX;
	private double posY;
	private double speedX;
	private double speedY;
	
	public GameObject(double posX, double posY, double speedX, double speedY) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	protected double getPosX() {
		return posX;
	}
	
	protected void setPosX(double posX) {
		this.posX = posX;
	}
	
	protected double getPosY() {
		return posY;
	}
	
	protected void setPosY(double posY) {
		this.posY = posY;
	}
	
	protected double getSpeedX() {
		return speedX;
	}
	
	protected void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
	protected double getSpeedY() {
		return speedY;
	}
	
	protected void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
	public abstract Rectangle getBounding();

	public abstract void setBounding(int x, int y);
	
	protected abstract void update(double timeSinceLastFrame);
}