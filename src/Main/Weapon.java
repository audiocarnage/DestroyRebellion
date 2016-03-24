package Main;

public abstract class Weapon implements Collectable {
	
	private WeaponSystem ws;
	private int rateOfFire = 0;
	private int health;
	private boolean overheated = false;
	private int ammunition = 0;
	
	public Weapon(WeaponSystem ws) {
		this.ws = ws;
	}
	
	public WeaponSystem getWeaponSystem() {
		return ws;
	}
	
	public void addToAmmunition(int ammo) {
		ammunition += ammo;
	}
	
	public void decrementAmmunition() {
		ammunition--;
	}
	
	public int getAmmunitionLeft() {
		return ammunition;
	}
	
	public int getRateOfFire() {
		return rateOfFire;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isOverheated() {
		return overheated;
	}
	
	public abstract void fire();
}