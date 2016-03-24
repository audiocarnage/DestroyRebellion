package Levels;

import Main.*;

public class Level03 extends Level {

	private static final int LEVELNUMBER = 3;
	private Game game;
	
	public Level03(Game game) {
		super(game, 500);
		this.game = game;
	}
	
	public void load() {
		
	}

	@Override
	public int getNumber() {
		return LEVELNUMBER;
	}

	@Override
	public void update(double timeSinceLastFrame) {
		int generatedEnemies = 0;
		if (!isLevelFinished()) {
			generatedEnemies = getEnemyGenerator().update(timeSinceLastFrame);
			incrementGeneratedEnnemies(generatedEnemies);
		}
		if (!isLevelFinished() 
				&& game.getEnemies().size() > 5 
				&& game.getUpgrades().size() < 1) {
			
		}
	}

	@Override
	public boolean isLevelFinished() {
		if (getGeneratedEnemies() > getEnemyNumber()) {
			return true;
		} else {
			return false;
		}
	}
}