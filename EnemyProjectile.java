package gui;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EnemyProjectile extends Parent {
	
	private static final double PROJECTILE_DIAMETER = 5.0;
	private Circle b1, b2, b3, b4;

	public EnemyProjectile(Enemy enemy) {
		b1 = new Circle();
		b1.setCenterX(enemy.getBoundsInParent().getCenterX());
		b1.setCenterY(enemy.getBoundsInParent().getCenterY());
		b1.setRadius(PROJECTILE_DIAMETER);
		b1.setFill(Color.WHITE);
		b1.setStroke(Color.WHITE);
		getChildren().addAll(b1);
	}
}
