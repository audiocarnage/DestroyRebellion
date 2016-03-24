package Main;

public class WeaponSystem {

	private Spaceship host;
	private Weapon guns;
	private Weapon missiles;
	private double primaryWeaponShotIntervall = 0.12d;
	private double secondaryWeaponShotIntervall = 2d;
	private double timeSinceLastShotPrimary = 0;
	private double timeSinceLastShotSecondary = 0;

	public WeaponSystem(Spaceship host) {
		this.host = host;
	}

	public Spaceship getHost() {
		return host;
	}
	
	public void setHost(Spaceship host) {
		this.host = host;
	}

	public Weapon getPrimaryWeapon() {
		return guns;
	}

	public void setPrimaryWeapon(Weapon weapon) {
		guns = weapon;
	}

	public Weapon getSecondaryWeapon() {
		return missiles;
	}

	public void setSecondaryWeapon(Weapon weapon) {
		missiles = weapon;
	}

	public void firePrimary() {
		if (getTimeSinceLastShotPrimary() > primaryWeaponShotIntervall) {
			setTimeSinceLastShotPrimary(0);
			guns.fire();
		}
	}

	public void fireSecondary() {
		if (getTimeSinceLastShotSecondary() > secondaryWeaponShotIntervall) {
			setTimeSinceLastShotSecondary(0);
			missiles.fire();
		}
	}

	public double getTimeSinceLastShotPrimary() {
		return timeSinceLastShotPrimary;
	}

	public void setTimeSinceLastShotPrimary(double time) {
		timeSinceLastShotPrimary = time;
	}	

	public double getTimeSinceLastShotSecondary() {
		return timeSinceLastShotSecondary;
	}

	public void setTimeSinceLastShotSecondary(double time) {
		timeSinceLastShotSecondary = time;
	}

	public double getPrimaryWeaponShotIntervall() {
		return primaryWeaponShotIntervall;
	}
	
	public void setPrimaryWeaponShotIntervall(double intervall) {
		primaryWeaponShotIntervall = intervall;
	}
	
	public double getSecondaryWeaponShotIntervall() {
		return secondaryWeaponShotIntervall;
	}

	public void setSecondaryWeaponShotIntervall(double intervall) {
		secondaryWeaponShotIntervall = intervall;
	}

	public void update (double timeSinceLastFrame) {
		setTimeSinceLastShotPrimary(getTimeSinceLastShotPrimary() + timeSinceLastFrame);
		setTimeSinceLastShotSecondary(getTimeSinceLastShotSecondary() + timeSinceLastFrame);
	}
}