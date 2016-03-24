package Levels;

import java.util.ArrayList;
import java.util.List;
import Main.*;

public class Level02 extends Level {

	private static final int LEVELNUMBER = 2;
	private List<Upgrade> upgrades = new ArrayList<Upgrade>();
	private Game game;
	private static final int UPGRADE_SPEEDX = 45;
	private static final int ENEMIES_TO_GENERATE = 360;
	
	public Level02(Game game) {
		super(game, ENEMIES_TO_GENERATE);
		this.game = game;
	}
	
	public void load() {
		WeaponSystem ws = game.getPlayer().getWeaponSystem();
		ws.setPrimaryWeapon(new IonCannon(ws));
		
		upgrades.add(new Upgrade(new Shield(50),
				Game.getScreenWidth(),
				getRandomScreenYPosition(),
				UPGRADE_SPEEDX,
				0));
		upgrades.add(new Upgrade(new TriIonCannon(game.getPlayer().getWeaponSystem()),
				Game.getScreenWidth(),
				getRandomScreenYPosition(),
				UPGRADE_SPEEDX,
				0));
	}
	
	@Override
	public int getNumber() {
		return LEVELNUMBER;
	}

	@Override
	public boolean isLevelFinished() {
		if (getGeneratedEnemies() > getEnemyNumber()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void update(double timeSinceLastFrame) {
		int generatedEnemies = 0;
		if (!isLevelFinished()) {
			generatedEnemies = getEnemyGenerator().update(timeSinceLastFrame);
			incrementGeneratedEnnemies(generatedEnemies);
			if (getGeneratedEnemies() > 100) {
				for (Enemy enemy : game.getEnemies()) {
					enemy.getWeaponSystem().setPrimaryWeapon(new TriIonCannon(enemy.getWeaponSystem()));
				}
			}
		}
		if (!isLevelFinished() && game.getScore() > 0 && game.getUpgrades().size() < 1) {
			if (((getGeneratedEnemies() > 80 && getGeneratedEnemies() < 90)
					|| (getGeneratedEnemies() > 180 && getGeneratedEnemies() < 190))
					&& upgrades.get(0) != null) {
				game.getUpgrades().add(upgrades.get(0));
				upgrades.set(0, null);
			}
			if (getGeneratedEnemies() > 100 && upgrades.get(1) != null) {
				game.getUpgrades().add(upgrades.get(1));
				upgrades.set(1, null);
			}
		}
	}
}