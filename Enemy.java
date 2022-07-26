package gui;

import javafx.animation.RotateTransition;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Enemy extends Parent{

	private Rectangle basicEnemy;
	private RotateTransition spin;
	private double x = 165.0, y = 20.0, width = 30.0, height = 30.0;
	
	protected Enemy() {
		basicEnemy = new Rectangle(x,y,width,height);
		basicEnemy.setStroke(Color.CRIMSON);
		basicEnemy.setFill(Color.WHITE);
		getChildren().add(basicEnemy);
	}
	
	//TODO: make stop if no enemies
	protected void enemySpin() {
		spin = new RotateTransition();
		spin.setDuration(Duration.millis(20000));
		spin.setNode(this);
		spin.setByAngle(360);
		spin.setAutoReverse(false);
		spin.play();
		spin.setOnFinished(e -> this.enemySpin());
	}
	
	protected double getWidth() {
		return this.width;
	}

	public void stopSpin() {
		spin.stop();
	}
	
	protected void startSpin() {
		spin.play();	
	}
}
