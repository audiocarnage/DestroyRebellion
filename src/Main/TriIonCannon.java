package Main;

public class TriIonCannon extends Weapon {

	public TriIonCannon(WeaponSystem ws) {
		super(ws);
	}

	public void fire() {
		Spaceship host = getWeaponSystem().getHost();
		double speedXFactor = 2.0;
		double speedY1Factor = 0.75;
		double speedY2Factor = -0.75;
		if (host instanceof Enemy) {
			speedXFactor = -3.0;
			speedY1Factor = 1;
			speedY2Factor = -1;
		}
		host.getShots().add(new Bullet(host.getPosX() + ((host.getImage().getWidth() - Bullet.getImage().getWidth()) * 0.5d),
				host.getPosY() + ((getWeaponSystem().getHost().getImage().getHeight() - Bullet.getImage().getHeight()) * 0.5d),
				host.getSpeedX() * speedXFactor, 0));
		host.getShots().add(new Bullet(getWeaponSystem().getHost().getPosX() + ((host.getImage().getWidth() - Bullet.getImage().getWidth()) * 0.5d),
				host.getPosY() + ((host.getImage().getHeight() - Bullet.getImage().getHeight()) * 0.5d),
				host.getSpeedX() * speedXFactor, host.getSpeedX() * speedY2Factor));
		host.getShots().add(new Bullet(host.getPosX() + ((host.getImage().getWidth() - Bullet.getImage().getWidth()) * 0.5d),
				host.getPosY() - ((host.getImage().getHeight() - Bullet.getImage().getHeight()) * -0.5d),
				host.getSpeedX() * speedXFactor, host.getSpeedX() * speedY1Factor));
	}
}