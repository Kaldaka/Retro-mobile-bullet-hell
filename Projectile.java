package gui;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class Projectile extends Parent{
	
	private BasicProjectile b1;
	private shotgunProjectile b2;
	private Upgrade upgrade = Upgrade.NONE;
	private boolean auto;
	private double projectileSpeed;
	protected final double projectileTravelLength = -100.0;
	private final double slow = 2000.0, medium = 1000.0, fast = 500.0;

	protected Projectile(MouseEvent mouse) {
		switch (this.upgrade) {
		case NONE:
			b1 = new BasicProjectile(mouse);
			getChildren().add(b1);
			break;
		case MEDIUM_SPEED:
			b1 = new BasicProjectile(mouse);
			getChildren().add(b1);
			break;
		case FAST_SPEED:
			b1 = new BasicProjectile(mouse);
			getChildren().add(b1);
			break;
		case AUTO:
			b1 = new BasicProjectile(mouse);
			getChildren().add(b1);
			break;
		case SHOTGUN:
			//b2 = new ShotgunProjectile(mouse);
			getChildren().add(b2);
			break;
		}
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
}
