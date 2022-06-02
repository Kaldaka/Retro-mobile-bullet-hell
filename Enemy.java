package gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Parent{

	private Rectangle basicEnemy;
	private double x = 180.0, y = 20.0, width = 30.0, height = 30.0;
	
	protected Enemy() {
		basicEnemy = new Rectangle(x,y,width,height);
		basicEnemy.setStroke(Color.CRIMSON);
		basicEnemy.setFill(Color.WHITE);
		getChildren().add(basicEnemy);
	}
}
