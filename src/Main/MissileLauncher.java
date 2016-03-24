package Main;

public class MissileLauncher extends Weapon {
	
	public MissileLauncher(WeaponSystem ws) {
		super(ws);
	}

	public void fire() {
		Spaceship host = getWeaponSystem().getHost();
		double speedFactor = 1.0;
		if (host instanceof Player) {
			speedFactor = 1.5;
			host.getShots().add(new Rocket(host.getPosX() + ((host.getImage().getWidth() - Rocket.getImageWidth()) * 0.5d),
					host.getPosY() + ((host.getImage().getHeight() - Rocket.getImageHeight()) * 0.5d),
					host.getSpeedX()* speedFactor, 0));
		} else {
			speedFactor = -3.0;
			host.getShots().add(new DarkMatterRocket(host.getPosX() - DarkMatterRocket.getImage().getWidth(),
					host.getPosY() + ((host.getImage().getHeight() - DarkMatterRocket.getImage().getHeight()) * 0.5d),
					(host.getSpeedX() * speedFactor), 0));
		}
		decrementAmmunition();
	}
}