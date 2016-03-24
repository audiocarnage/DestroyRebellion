package Main;

import java.util.Random;

public class UpgradeGenerator extends Generator {

	private Game game;
	private Random rand;
	private static final int SPEEDX = 40;

	public UpgradeGenerator(Game game) {
		this.game = game;
		rand = new Random();
	}
	
	public void generateIonCannon() {
		addToGame(new Upgrade(new IonCannon(game.getPlayer().getWeaponSystem()),
				Game.getScreenWidth(),
				getRandomScreenYPosition(),
				SPEEDX,
				0));
	}
	
	public void generateTurbolaserCannon() {
		addToGame(new Upgrade(new TurbolaserCannon(game.getPlayer().getWeaponSystem()),
				Game.getScreenWidth(),
				getRandomScreenYPosition(),
				SPEEDX,
				0));
	}
	
	protected int getRandomScreenYPosition() {
		return rand.nextInt(Game.getScreenHeight()-100);
	}
	
	private void addToGame(Upgrade upgrade) {
		game.getUpgrades().add(upgrade);
	}
}