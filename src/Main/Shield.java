package Main;

public class Shield implements Collectable {

	private int maxEnergy;
	private int energy;
	
	public Shield(int energy) {
		maxEnergy = energy;
		this.energy = maxEnergy;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getMaxEnergy() {
		return maxEnergy;
	}
	
	public int absorb(int damage) {
		// return absorbed damage
		if (energy > damage) {
			energy -= damage;
			return damage;
		} else {
			energy -= damage;
			return energy + damage;
		}
	}
}