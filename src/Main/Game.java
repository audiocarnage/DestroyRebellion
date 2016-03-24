package Main;

import GameState.GameState;
import GameState.GameStateManager;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Game implements Runnable {

	private static final String TITLE = "DESTROY THE REBELLION";
	private boolean running = false;
	private GameStateManager gsm;
	private LevelManager lm;
	private static final DisplayMode[] dm = DisplayModes.getDisplayModes();
	private static final int SCREEN_WIDTH = dm[dm.length-1].getWidth();
	private static final int SCREEN_HEIGHT = dm[dm.length-1].getHeight();
	private Thread thread;
	private Frame frame;
	private Background backgroundGame;
	private Background backgroundMenu;
	private Player player;
	private List<Enemy> enemies;
	private List<Projectile> playerShots;
	private List<Projectile> enemyShots;
	private List<Upgrade> upgrades;
	private int killCounter = 0;
	private long lastFrame = 0L;
	private long score = 0;

	public Game() {
		DisplayMode displayMode = new DisplayMode(dm[dm.length-1].getWidth(), dm[dm.length-1].getHeight(), dm[dm.length-1].getBitDepth(), dm[dm.length-1].getRefreshRate());
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();

		backgroundMenu = new Background(15, "gfx/weltraum.png");
		initialise();
		
		gsm = new GameStateManager();
		frame = new Frame(this, gsm);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		device.setFullScreenWindow(frame);
		device.setDisplayMode(displayMode);
		frame.createStrategy();
	}
	
	public void initialise() {
		backgroundGame = new Background(30, "gfx/weltraum.png");
		player = new Player(50, (int)(getScreenHeight() * 0.5), this);
		playerShots = player.getShots();
		enemies = new ArrayList<Enemy>();
		enemyShots = new ArrayList<Projectile>();
		upgrades = new ArrayList<Upgrade>();
		lm = new LevelManager(this);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void reset() {
		killCounter = 0;
		score = 0;
	}

	public void start() {
		if (!running) {
			thread = new Thread(this, TITLE);
			thread.start();			
		}
	}

	public void stop() {
		backgroundGame = null;
		player = null;
		Iterator<Projectile> iterPlayerShots = playerShots.iterator();
		while (iterPlayerShots.hasNext()) {
			iterPlayerShots.remove();
		}
		playerShots = null;
		Iterator<Enemy> iterEnemies = enemies.iterator();
		while (iterEnemies.hasNext()) {
			iterEnemies.remove();
		}
		enemies = null;
		Iterator<Projectile> iterEnemyShots = enemyShots.iterator();
		while (iterEnemyShots.hasNext()) {
			iterEnemyShots.remove();
		}
		enemyShots = null;
		Iterator<Upgrade> iterUpgrade = upgrades.iterator();
		while (iterUpgrade.hasNext()) {
			iterUpgrade.remove();
		}
		upgrades = null;
		lm = null;
		thread = null;
		running = false;
	}

	public void quit() {
		System.exit(0);
	}

	@Override
	public void run() {
		running = true;
		lastFrame = System.currentTimeMillis();
		while (running) {
			if (gsm.getState() == GameState.PLAY) {
				long currentFrame = System.currentTimeMillis();
				double timeSinceLastFrame = ((double)(currentFrame - lastFrame)) * 0.001d;
				lastFrame = currentFrame;

				lm.update(timeSinceLastFrame);

				backgroundGame.update(timeSinceLastFrame);
				player.update(timeSinceLastFrame);

				Iterator<Projectile> iterPlayerShots = playerShots.iterator();
				while (iterPlayerShots.hasNext()) {
					Projectile bullet = iterPlayerShots.next();
					bullet.update(timeSinceLastFrame);
					if (bullet.getPosX() > Game.getScreenWidth()) {
						iterPlayerShots.remove();
					}
				}

				Iterator<Enemy> iterEnemies = enemies.iterator();
				while (iterEnemies.hasNext()) {
					Enemy enemy = iterEnemies.next();
					enemy.update(timeSinceLastFrame);
					if (enemy.getImage() == null || enemy.isDisposable() 
							|| enemy.getPosX() < -enemy.getImage().getWidth()
							|| enemy.getPosY() < -enemy.getImage().getHeight()
							|| enemy.getPosY() > getScreenHeight()) {
						iterEnemies.remove();
					}
				}

				Iterator<Projectile> iterEnemyShots = enemyShots.iterator();
				while (iterEnemyShots.hasNext()) {
					Projectile bullet = iterEnemyShots.next();
					bullet.update(timeSinceLastFrame);
					if (bullet.getPosX() < 0) {
						iterEnemyShots.remove();
					}
				}

				Iterator<Upgrade> iterUpgrades = upgrades.iterator();
				while (iterUpgrades.hasNext()) {
					Upgrade upgrade = iterUpgrades.next();
					upgrade.update(timeSinceLastFrame);
					if (upgrade.getPosX() < -Upgrade.getImage().getWidth()) {
						iterUpgrades.remove();
					}
				}

				frame.repaintScreen(timeSinceLastFrame);

				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (gsm.getState() == GameState.MENU) {
				long currentFrame = System.currentTimeMillis();
				double timeSinceLastFrame = ((double)(currentFrame - lastFrame)) * 0.001d;
				lastFrame = currentFrame;

				backgroundMenu.update(timeSinceLastFrame);
				frame.repaintScreen(0);
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		stop();
	}
	
	
	public static String getTitle() {
		return TITLE;
	}

	public LevelManager getLevelManager() {
		return lm;
	}

	public Background getBackgroundGame() {
		return backgroundGame;
	}

	public Background getBackgroundMenu() {
		return backgroundMenu;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Projectile> getEnemyShots() {
		return enemyShots;
	}

	public List<Upgrade> getUpgrades() {
		return upgrades;
	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public void incrementKillCounter() {
		killCounter++;
	}

	public int getKillCounter() {
		return killCounter;
	}

	public long getScore() {
		return score;
	}
	public void incrementScore(int points) {
		score += points;
	}
}