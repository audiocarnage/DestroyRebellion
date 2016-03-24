package Main;

public class TurbolaserCannon extends Weapon {

	public TurbolaserCannon(WeaponSystem ws) {
		super(ws);
	}

	public void fire() {
		Spaceship host = getWeaponSystem().getHost();
		double speedFactor = 1.0;
		if (host instanceof Player) {
			speedFactor = 2.0;
			host.getShots().add(new Turbolaser(host.getPosX() + ((host.getImage().getWidth() - Turbolaser.getImage().getWidth()) * 0.5d),
					host.getPosY() + ((host.getImage().getHeight() - Turbolaser.getImage().getHeight()) * 0.5d),
					host.getSpeedX() * speedFactor, 0));
		} else {
			speedFactor = 3.0;
			host.getShots().add(new Turbolaser(host.getPosX() - ((host.getImage().getWidth() - Turbolaser.getImage().getWidth()) * 0.5d),
					host.getPosY() + ((host.getImage().getHeight() - Turbolaser.getImage().getHeight()) * 0.5d),
					host.getSpeedX() * speedFactor, 0));
		}
	}
}