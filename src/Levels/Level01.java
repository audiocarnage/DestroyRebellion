package Levels;

import Main.*;
import java.util.List;
import java.util.ArrayList;

public class Level01 extends Level {
	
	private static final int LEVELNUMBER = 1;
	private List<Upgrade> upgrades = new ArrayList<Upgrade>();
	private Game game;
	private static final int UPGRADE_SPEEDX = 50;
	private static final int ENEMIES_TO_GENERATE = 120;
	
	public Level01(Game game) {
		super(game, ENEMIES_TO_GENERATE);
		this.game = game;
	}
	
	public void load() {
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
		}
		if (!isLevelFinished() && game.getScore() > 0 && game.getUpgrades().size() < 1) {
			if (getGeneratedEnemies() > 20 && getGeneratedEnemies() < 40
					&& upgrades.get(0) != null) {
				game.getUpgrades().add(upgrades.get(0));
				upgrades.set(0, null);
			}
			if (getGeneratedEnemies() > 60 && upgrades.get(1) != null) {
				game.getUpgrades().add(upgrades.get(1));
				upgrades.set(1, null);
			}
		}
	}
}