package gui;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Projectile extends Parent{
	
	private TranslateTransition fire, fire2, fire3, fire4;
	private BasicProjectile b1;
	private shotgunProjectile b2;
	private EnemyProjectile ep1;
	private Upgrade upgrade = Upgrade.NONE, eUpgrade = Upgrade.ENEMY;
	private boolean auto;
	private double projectileSpeed;
	protected final double projectileTravelLength = -1000.0;
	private final double verySlow = 8000.0, slow = 2000.0, medium = 1000.0, fast = 500.0;

	protected Projectile(MouseEvent mouse) {
		b1 = new BasicProjectile(mouse);
		getChildren().add(b1);
	}
	
	protected Projectile(Enemy enemy) {
		ep1 = new EnemyProjectile(enemy);
		getChildren().add(ep1);
	}
	
	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
		
		switch (this.upgrade){
		case NONE:
			this.auto = false;
			this.projectileSpeed = slow;
			break;
		case MEDIUM_SPEED:
			this.auto = false;
			this.projectileSpeed = medium;
			break;
		case FAST_SPEED:
			this.auto = false;
			this.projectileSpeed = fast;
			break;
		case AUTO:
			this.auto = true;
			this.projectileSpeed = fast;
			break;
		case SHOTGUN:
			this.auto = false;
			this.projectileSpeed = medium;
			break;
		case ENEMY:
			this.projectileSpeed = verySlow;
			break;
		default:
			break;
		}
	}
	protected void shipFire() {
		fire = new TranslateTransition();
		fire.setByY(this.getProjectileTravelLength());
		fire.setDuration(Duration.millis(this.getProjectileSpeed()));
		fire.setCycleCount(1);
		fire.setNode(this);
		fire.play();
	}
	
	protected void enemyFire(Enemy e1, int i) {
		fire = new TranslateTransition();
		fire.setDuration(Duration.millis(this.getProjectileSpeed()));
		fire.setCycleCount(1);
		fire.setNode(this);
		switch (i) {
		case 0:
			fire.setByY(-projectileTravelLength);
			fire.setByX(projectileTravelLength-projectileTravelLength);
			fire.play();
			break;
		case 1:
			fire.setByY(projectileTravelLength-projectileTravelLength);
			fire.setByX(-projectileTravelLength);
			fire.play();
			break;
		case 2:
			fire.setByY(projectileTravelLength);
			fire.setByX(projectileTravelLength-projectileTravelLength);
			fire.play();
			break;
		case 3:
			fire.setByY(projectileTravelLength-projectileTravelLength);
			fire.setByX(projectileTravelLength);
			fire.play();
			break;
		}
	}
	
	public String getUpgradeString() {
		return this.upgrade.toString();
	}
	
	public Upgrade getUpgradeEnum() {
		return this.upgrade;
	}
	
	protected double getProjectileSpeed() {
		return this.projectileSpeed;
	}
	
	protected double getProjectileTravelLength() {
		return this.projectileTravelLength;
	}
	
	protected void stopProjectile() {
		fire.stop();
	}

	public void startProjectile() {
		fire.play();
	}
}
