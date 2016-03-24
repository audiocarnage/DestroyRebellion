package Main;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class Enemy extends Spaceship {

	private Game game;
	private WeaponSystem ws;
	private List<Projectile> shots;
	private int killPoints = 0;
	private static final double DEFAULT_PRIMARY_WEAPON_SHOT_INTERVALL = 3d;
	private static final double DEFAULT_SECONDARY_WEAPON_SHOT_INTERVALL = 6d;
	private static final double DEFAULT_SHOT_PROBABILITY = 0.95;

	public Enemy(int x, int y, int speedX, int speedY, String[] imageAlive, 
			Game game, int killPoints, int healthPoints) {
		super(x, y, speedX, speedY, imageAlive, healthPoints);
		this.game = game;
		shots = game.getEnemyShots();
		this.killPoints = killPoints;
		ws = new WeaponSystem(this);
		ws.setPrimaryWeapon(new TurbolaserCannon(ws));
		ws.setSecondaryWeapon(new MissileLauncher(ws));
		ws.setPrimaryWeaponShotIntervall(DEFAULT_PRIMARY_WEAPON_SHOT_INTERVALL - (game.getLevelManager().getLevel().getNumber() * 0.1));
		ws.setSecondaryWeaponShotIntervall(DEFAULT_SECONDARY_WEAPON_SHOT_INTERVALL - (game.getLevelManager().getLevel().getNumber() * 0.1));
	}
	
	public WeaponSystem getWeaponSystem() {
		return ws;
	}

	@Override
	public void update(double timeSinceLastFrame) {
		super.update(timeSinceLastFrame);
		ws.update(timeSinceLastFrame);

		setPosX(getPosX() - (getSpeedX() * timeSinceLastFrame));
		//setPosY(getPosY() - (getSpeedY() * timeSinceLastFrame));
		/* enemy will show up on the right side
		if (getPosX() < -getImage().getWidth()) {
			setPosX(Game.getScreenWidth());
			setPosY(random.nextInt(Game.getScreenHeight()-getImage().getHeight()));
			revive();
		}*/

		setBounding((int)getPosX(), (int)getPosY());
		collisionDetection();
		
		Random rand = new Random();
		if (game.getPlayer().isAlive() && game.getPlayer().getPosX() < getPosX() && isAlive() 
				&& rand.nextDouble() > DEFAULT_SHOT_PROBABILITY) {
			firePrimary();
		}
		if (game.getPlayer().isAlive() && game.getPlayer().getPosX() < getPosX() && isAlive()
				&& rand.nextDouble() > DEFAULT_SHOT_PROBABILITY) {
			fireSecondary();
		}
	}

	@Override
	protected void collisionDetection() {
		Iterator<Projectile> iterPlayerShots = game.getPlayer().getShots().iterator();
		while (iterPlayerShots.hasNext()) {
			Projectile shot = iterPlayerShots.next();
			if (getBounding().intersects(shot.getBounding()) && isAlive()) {
				iterPlayerShots.remove();
				setHullHit();
				setHealth(getHealth() - shot.getDamagePower());
				if (getHealth() <= 0) {
					kill();
					game.incrementScore(killPoints);
					game.incrementKillCounter();
				}
			}
		}
	}
	
	@Override
	public List<Projectile> getShots() {
		return shots;
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
}