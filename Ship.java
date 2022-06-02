package gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship extends Parent {
	
	private Polygon ship;
	private final double shipX = 180.0, shipY = 650.0, shipTop = 20.0, shipCorner = 10.0;
	public Upgrade upgrade;
	
	public Ship(Upgrade upgrade) {
		ship = new Polygon();
		ship.getPoints().addAll(new Double[] {
				(shipX), (shipY-shipTop),
				(shipX-shipCorner), (shipY+shipCorner),
				(shipX+shipCorner), (shipY+shipCorner)
			});
		this.upgrade = upgrade;
		ship.setStroke(Color.PURPLE);
		ship.setFill(Color.WHITE);
		
		getChildren().add(ship);
	}
	
	public void setUpgrade(Upgrade newUpgrade) {
		this.upgrade = newUpgrade;
	}
	
	public Upgrade getUpgrade() {
		return this.upgrade;
	}
	
	public double getShipX() {
		return this.shipX;
	}
	
	public double getShipY() {
		return this.shipY;
	}
}
