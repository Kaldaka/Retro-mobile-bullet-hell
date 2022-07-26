package gui;

public enum Upgrade {
	NONE ("None"), MEDIUM_SPEED ("Fast Shooting"), FAST_SPEED ("Lightning Bullets"), AUTO ("Full Auto"), SHOTGUN ("Shotgun"), ENEMY ("Enemy)");
	
	private String upgradeDescription;
	
	private Upgrade (String upgradeDescription) {
		this.upgradeDescription = upgradeDescription;
	}
	
	@Override
	public String toString() {
		return this.upgradeDescription;
	}
}
