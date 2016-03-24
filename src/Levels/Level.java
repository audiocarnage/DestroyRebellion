package Levels;

import java.util.List;
import java.util.Random;
import Main.*;

public abstract class Level {
	
	private int numberOfEnemies = 0;
	private int generatedEnemies = 0;
	private EnemyGenerator enemyGenerator;
	private UpgradeGenerator upgradeGenerator;
	private Game game;
	private Random rand = new Random();

	public Level(Game game, int numberOfEnemies) {
		this.game = game;
		this.numberOfEnemies = numberOfEnemies;
		enemyGenerator = new EnemyGenerator(game);
		upgradeGenerator = new UpgradeGenerator(game);
	}	
	
	protected int getEnemyNumber() {
		return numberOfEnemies;
	}
	
	protected int getGeneratedEnemies() {
		return generatedEnemies;
	}
	
	protected void incrementGeneratedEnnemies(int numberOfGeneratedEnemies) {
		generatedEnemies += numberOfGeneratedEnemies;
	}
	
	protected int getRandomScreenYPosition() {
		return rand.nextInt(Game.getScreenHeight()-100);
	}
	
	protected List<Upgrade> getUpgrades() {
		return game.getUpgrades();
	}
	
	protected EnemyGenerator getEnemyGenerator() {
		return enemyGenerator;
	}
	
	protected UpgradeGenerator getUpgradeGenerator() {
		return upgradeGenerator;
	}

	public abstract void load();
	
	public abstract int getNumber();
	
	public abstract void update (double timeSinceLastFrame);
	
	public abstract boolean isLevelFinished();
}