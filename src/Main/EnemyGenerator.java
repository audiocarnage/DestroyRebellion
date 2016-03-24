package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator extends Generator {

	private static List<String[]> images = new ArrayList<String[]>();
	private double timeSinceLastEnemySpawn = 0;
	private static final double ENEMY_SPAWN_INTERVALL = 3d;
	private static final int STARTSPEED = 75;
	private static final int VARIABLESPEED = 100;
	private static final int BASIC_ENEMY_KILLPOINTS = 10;
	private static final int BASIC_ENEMY_HEALTHPOINTS = 24;
	private static final int XWING_KILLPOINTS = 25;
	private static final int XWING_HEALTHPOINTS = 48;
	private Game game;

	static {
		images.add(new String[]{"gfx/FighterF1a.png", "gfx/FighterF1b.png", "gfx/FighterF1c.png", "gfx/FighterF1d.png"});
		images.add(new String[]{"gfx/FighterF2a.png", "gfx/FighterF2b.png", "gfx/FighterF2c.png", "gfx/FighterF2d.png"});
		images.add(new String[]{"gfx/FighterF3a.png", "gfx/FighterF3b.png", "gfx/FighterF3c.png", "gfx/FighterF3d.png"});
		images.add(new String[]{"gfx/X-Wing.png"});
	}

	public EnemyGenerator(Game game) {
		this.game = game;
	}

	public int update(double timeSinceLastFrame) {
		timeSinceLastEnemySpawn += timeSinceLastFrame;
		int numberOfEnemiesToSpawn = 0;
		if (game.getPlayer().isAlive() && timeSinceLastEnemySpawn > ENEMY_SPAWN_INTERVALL) {
			if (game.getEnemies().size() != 0) {
				numberOfEnemiesToSpawn = (int)Math.abs(
						game.getLevelManager().getLevel().getNumber() + 
						(timeSinceLastEnemySpawn * 10 / game.getEnemies().size() * Math.sin(timeSinceLastEnemySpawn)));
			} else {
				numberOfEnemiesToSpawn = game.getLevelManager().getLevel().getNumber();
			}
			for (int i = 0; i < numberOfEnemiesToSpawn; i++) {
				spawnEnemy();
			}
			timeSinceLastEnemySpawn -= ENEMY_SPAWN_INTERVALL;
		}
		return numberOfEnemiesToSpawn;
	}

	private int calculateSpeed(int seed) {
		Random rand = new Random();
		return STARTSPEED + rand.nextInt(seed);
	}

	private void spawnEnemy() {
		Random rand = new Random();
		if (rand.nextDouble() < 0.5) {
			game.getEnemies().add(new Enemy(Game.getScreenWidth() + (int)(1.75 * rand.nextInt(100)),
					rand.nextInt(Game.getScreenHeight() - 80), 
					calculateSpeed(VARIABLESPEED),
					calculateSpeed(VARIABLESPEED),
					images.get(rand.nextInt(images.size()-1)),
					game,
					BASIC_ENEMY_KILLPOINTS,
					BASIC_ENEMY_HEALTHPOINTS));
		} else {
			game.getEnemies().add(new Enemy(Game.getScreenWidth() + (int)(1.75 * rand.nextInt(100)),
					rand.nextInt(Game.getScreenHeight() - 120), 
					calculateSpeed(VARIABLESPEED),
					calculateSpeed(VARIABLESPEED),
					images.get(images.size()-1),
					game,
					XWING_KILLPOINTS,
					XWING_HEALTHPOINTS));
		}
	}
}