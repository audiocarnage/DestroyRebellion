package Main;

import GameState.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Game game;
	private GameStateManager gsm;
	private Menu menu;
	private BufferStrategy buffStrat;
	private final Background backgroundGame;
	private final Background backgroundMenu;
	private Font fontLarge = new Font("SansSerif", Font.BOLD, 24);
	private Font fontSmall = new Font("SansSerif", Font.BOLD, 16);
	private Rectangle healthBar;
	private Rectangle shieldBar;
	private Rectangle reloadBar;
	private Random random = new Random();
	private LevelManager lm;

	public Frame(Game game, GameStateManager gsm) {
		super(Game.getTitle());
		this.game = game;
		this.gsm = gsm;
		menu = new Menu();
		backgroundGame = game.getBackgroundGame();
		backgroundMenu = game.getBackgroundMenu();
		healthBar = new Rectangle(20, 30, game.getPlayer().getHealth(), 20);
		shieldBar = new Rectangle(20, 60, game.getPlayer().getHealth(), 20);
		reloadBar = new Rectangle(300, 30, game.getPlayer().getHealth(), 20);
		lm = game.getLevelManager();

		addKeyListener(new KeyInput());
		gsm.setState(GameState.MENU);
		hideMouseCursor();
	}

	private void hideMouseCursor() {
		BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
		this.setCursor(blankCursor);
	}

	public void createStrategy() {
		createBufferStrategy(2);
		buffStrat = getBufferStrategy();
	}

	public void repaintScreen(double timeSinceLastFrame) {
		if (gsm.getState() == GameState.PLAY) {
			if (KeyInput.isKeyDown(KeyEvent.VK_W)) {
				game.getPlayer().setPosY(game.getPlayer().getPosY() - (game.getPlayer().getSpeedX() * timeSinceLastFrame));
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_S)) {
				game.getPlayer().setPosY(game.getPlayer().getPosY() + (game.getPlayer().getSpeedX() * timeSinceLastFrame));
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_A)) {
				game.getPlayer().setPosX(game.getPlayer().getPosX() - (game.getPlayer().getSpeedX() * timeSinceLastFrame));
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_D)) {
				game.getPlayer().setPosX(game.getPlayer().getPosX() + (game.getPlayer().getSpeedX() * timeSinceLastFrame));
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_SPACE)) {
				game.getPlayer().firePrimary();
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_J)) {
				game.getPlayer().fireSecondary();
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_ESCAPE)) {
				gsm.setState(GameState.MENU);
			}
		} else if (gsm.getState() == GameState.MENU) {
			if (KeyInput.isKeyDown(KeyEvent.VK_UP)) {
				menu.moveUp();
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_DOWN)) {
				menu.moveDown();
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_ENTER)) {
				gsm.setState(menu.selectOption());
				switch (gsm.getState()) {
				case PLAY :
					game.run();
					break;
				case RESTART :
					game.stop();
					game.initialise();
					game.start();
					gsm.setState(GameState.PLAY);
					game.run();
					break;
				case HELP :
					game.stop();
					break;
				case QUIT :
					game.quit();
					break;
				case MENU :
					break;
				}
			}
		}

		Graphics g = buffStrat.getDrawGraphics();
		draw(g);
		g.dispose();
		buffStrat.show();		
	}

	private void draw(Graphics g) {
		if (gsm.getState() == GameState.PLAY) {
			g.fillRect(0, 0, Game.getScreenWidth(), Game.getScreenHeight());
			g.drawImage(backgroundGame.getBackground(), backgroundGame.getPositionX(), 0, null);
			g.drawImage(backgroundGame.getBackground(), backgroundGame.getPositionX() + backgroundGame.getBackground().getWidth(), 0, null);
			
			if (!game.getPlayer().isDisposable()) {
				for (Upgrade upgrade : game.getUpgrades()) {
					g.drawImage(Upgrade.getImage(), upgrade.getBounding().x, upgrade.getBounding().y, null);
				}
			}
			for (Projectile enemyShot : game.getEnemyShots()) {
				if (enemyShot instanceof DarkMatterRocket) {
					g.drawImage(DarkMatterRocket.getImage(), enemyShot.getBounding().x, enemyShot.getBounding().y, null);
				} else if (enemyShot instanceof Turbolaser){
					g.drawImage(Turbolaser.getImage(), enemyShot.getBounding().x, enemyShot.getBounding().y, null);
				} else if (enemyShot instanceof Bullet) {
					g.drawImage(Bullet.getImage(), enemyShot.getBounding().x, enemyShot.getBounding().y, null);
				}
			}
			for (Enemy enemy : game.getEnemies())  {
				g.drawImage(enemy.getImage(), enemy.getBounding().x, enemy.getBounding().y, null);
				if (enemy.getHullHit() && enemy.isAlive()) {
					g.drawImage(Spaceship.getImageHullHit(), (int)(enemy.getBounding().x + (Spaceship.getImageHullHit().getWidth() * random.nextDouble())),
							(int)(enemy.getBounding().y + (Spaceship.getImageHullHit().getHeight() * random.nextDouble())), null);
				}
			}
			for (Projectile playerShot : game.getPlayer().getShots()) {
				if (playerShot instanceof Bullet) {
					g.drawImage(Bullet.getImage(), playerShot.getBounding().x, playerShot.getBounding().y, null);
				} else if (playerShot instanceof Rocket) {
					g.drawImage(Rocket.getImage(), playerShot.getBounding().x, playerShot.getBounding().y, null);
				} else if (playerShot instanceof Turbolaser) {
					g.drawImage(Turbolaser.getImage(), playerShot.getBounding().x, playerShot.getBounding().y, null);
				}
			}

			if (!game.getPlayer().isDisposable()) {
				g.drawImage(game.getPlayer().getImage(), game.getPlayer().getBounding().x, game.getPlayer().getBounding().y, null);
			}
			if (game.getPlayer().getHullHit() && game.getPlayer().isAlive()) {
				g.drawImage(Spaceship.getImageHullHit(), (int)(game.getPlayer().getBounding().x + (Spaceship.getImageHullHit().getWidth() * random.nextDouble())),
						(int)(game.getPlayer().getBounding().y + (Spaceship.getImageHullHit().getHeight() * random.nextDouble())), null);
			}

			g.setColor(Color.GREEN);
			if (game.getPlayer().getHealth() <= healthBar.width * 0.5 && game.getPlayer().getHealth() > healthBar.width * 0.25) {
				g.setColor(Color.ORANGE);
			}
			if (game.getPlayer().getHealth() <= healthBar.width * 0.25) {
				g.setColor(Color.RED);
			}
			g.drawRect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
			if (game.getPlayer().getHealth() >= 0) {
				g.fillRect(healthBar.x, healthBar.y, game.getPlayer().getHealth(), healthBar.height);
			}
			g.setColor(Color.WHITE);
			g.drawString("Hull", healthBar.x+10, healthBar.y+15);

			if (game.getPlayer().hasShield()) {
				g.setColor(Color.CYAN);
				g.drawRect(shieldBar.x, shieldBar.y, shieldBar.width, shieldBar.height);
				g.fillRect(shieldBar.x, shieldBar.y, (int)(shieldBar.width * game.getPlayer().getShield().getEnergy() / game.getPlayer().getShield().getMaxEnergy()), shieldBar.height);
				g.setColor(Color.WHITE);
				g.drawString("Shield", shieldBar.x+10, shieldBar.y+15);
			}

			g.setFont(fontLarge);
			g.setColor(Color.GREEN);
			g.drawString("Level " + lm.getLevel().getNumber(), 20, 20);
			g.drawString("Score " + game.getScore(), 150, 20);

			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(reloadBar.x, reloadBar.y, reloadBar.width, reloadBar.height);
			double shotIntervall = game.getPlayer().getWeaponSystem().getSecondaryWeaponShotIntervall();
			double passedTimeShot = game.getPlayer().getWeaponSystem().getTimeSinceLastShotSecondary();
			if (passedTimeShot < shotIntervall) {
				g.fillRect(reloadBar.x, reloadBar.y, (int)(passedTimeShot * reloadBar.width / shotIntervall), reloadBar.height);
				g.setFont(fontSmall);
				g.setColor(Color.WHITE);
				g.drawString("Reloading...", reloadBar.x+10, healthBar.y+15);
			} else {
				g.fillRect(reloadBar.x, reloadBar.y, reloadBar.width, reloadBar.height);
				g.setFont(fontSmall);
				g.setColor(Color.WHITE);
				g.drawString("Missile ready", reloadBar.x+10, healthBar.y+15);
			}

			if (!game.getPlayer().isAlive() && game.getPlayer().isDisposable()) {
				Font fontGO = new Font("SansSerif", Font.BOLD, 72);
				String go = "GAME OVER";
				g.setFont(fontGO);
				g.setColor(Color.RED);
				FontMetrics fm = getFontMetrics(getFont());
				g.drawString(go, (int)((Game.getScreenWidth() - fm.stringWidth(go)) * 0.25), (int)(Game.getScreenHeight() * 0.5));
			}
		} else if (gsm.getState() == GameState.MENU) {
			g.drawImage(backgroundMenu.getBackground(), backgroundMenu.getPositionX(), 0, null);
			g.drawImage(backgroundMenu.getBackground(), backgroundMenu.getPositionX() + backgroundMenu.getBackground().getWidth(), 0, null);
			menu.render(g);
		}
	}
}