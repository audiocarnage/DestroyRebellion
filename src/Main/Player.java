package Main;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Player extends Spaceship {

	private Game game;
	private Shield shield;
	private WeaponSystem ws;
	private List<Projectile> shots = new ArrayList<Projectile>();
	private static final String[] IMG_ALIVE = {"gfx/destroyer.png"};
	private static final String[] IMG_SHIELDED = {"gfx/destroyerShielded.png"};
	private static final int STARTSPEED = 250;
	private static final int health = 240;

	public Player(int x, int y, Game game) {
		super(x, y, STARTSPEED, STARTSPEED, IMG_ALIVE, health);
		this.game = game;
		ws = new WeaponSystem(this);
		ws.setPrimaryWeapon(new IonCannon(ws));
		ws.setSecondaryWeapon(new MissileLauncher(ws));
	}

	public WeaponSystem getWeaponSystem() {
		return ws;
	}

	public List<Projectile> getShots() {
		return shots;
	}

	@Override
	public void update(double timeSinceLastFrame) {
		super.update(timeSinceLastFrame);
		if (isAlive()) {
			ws.setPrimaryWeaponShotIntervall(Math.abs(0.2*Math.cos(timeSinceLastFrame * System.nanoTime())));
			ws.update(timeSinceLastFrame);
			
			if (getPosX() < 0) {
				setPosX(0);
			}
			if (getPosY() < 0) {
				setPosY(0);
			}
			if (getPosX() > Game.getScreenWidth() - getBounding().width) {
				setPosX(Game.getScreenWidth() - getBounding().width);
			}
			if (getPosY() > Game.getScreenHeight() - getBounding().height) {
				setPosY(Game.getScreenHeight() - getBounding().height);
			}
			setBounding((int)getPosX(), (int)getPosY());
			collisionDetection();

			if (shield != null) {
				if (shield.getEnergy() <= 0) {
					shield = null;
					swapImage(IMG_ALIVE);	
				}
			}
		}
	}

	@Override
	public void firePrimary() {
		if (isAlive()) {
			ws.firePrimary();
		}
	}

	@Override
	public void fireSecondary() {
		if (isAlive()) {
			ws.fireSecondary();
		}
	}

	public boolean hasShield() {
		return (shield == null) ? false : true;
	}

	public Shield getShield() {
		return shield;
	}

	@Override
	protected void collisionDetection() {
		Iterator<Upgrade> upgrades = game.getUpgrades().iterator();
		while (upgrades.hasNext()) {
			Upgrade upgrade = upgrades.next();
			if (getBounding().intersects(upgrade.getBounding()) && isAlive()) {
				Collectable up = upgrade.getContent();
				if (up instanceof Shield) {
					shield = (Shield)up;
					swapImage(IMG_SHIELDED);
				} else if (up instanceof IonCannon) {
					ws.setPrimaryWeapon((Weapon)up);
				} else if (up instanceof TriIonCannon) {
					ws.setPrimaryWeapon((Weapon)up);
				} else if (up instanceof TurbolaserCannon) {
					ws.setPrimaryWeapon((Weapon)up);
				}
				upgrades.remove();
			}
		}

		Iterator<Projectile> enemyBullets = game.getEnemyShots().iterator();
		while (enemyBullets.hasNext()) {
			Projectile laser = enemyBullets.next();
			if (getBounding().intersects(laser.getBounding()) && isAlive()) {
				enemyBullets.remove();
				if (shield == null) {
					setHullHit();
					setHealth(getHealth() - laser.getDamagePower());
					if (getHealth() <= 0) {
						kill();
					}
				} else {
					int remainingDamage = laser.getDamagePower();
					remainingDamage -= shield.absorb(laser.getDamagePower());
					if (remainingDamage > 0) {
						setHealth(getHealth() - remainingDamage);
						if (getHealth() <= 0) {
							kill();
						}
					}
				}
			}
		}
	}
}