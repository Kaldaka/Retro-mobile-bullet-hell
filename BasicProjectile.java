package gui;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasicProjectile extends Parent {
	
	private final static Double PROJECTILE_WIDTH = 2.0, PROJECTILE_HEIGHT = 10.0, SHIP_TOP = 20.0;
	protected Rectangle bullet;

	public BasicProjectile(MouseEvent mouse) {
		bullet = new Rectangle();
		bullet.setX(mouse.getX()-1);
		bullet.setY(mouse.getY()-SHIP_TOP);
		bullet.setWidth(PROJECTILE_WIDTH);
		bullet.setHeight(PROJECTILE_HEIGHT);
		bullet.setStroke(Color.WHITE);
		bullet.setFill(Color.WHITE);
		getChildren().add(bullet);
	}
}
