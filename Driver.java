package gui;

//Contains GUI elements and main methods.
//By: Elliot Warren

import javafx.application.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.util.ArrayList;

public class Driver extends Application {
	
	private Pane gamePlane;
	private BorderPane screen;
	private Menu menu;
	protected static final int SCREEN_X = 360, SCREEN_Y = 772;
	private Ship ship;
	private Projectile bullet;
	private Enemy e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
	private int levelCounter;
	private TranslateTransition moveMenu, fire;
	private boolean enemyHit = false, enemyPresent = false, menuIsOut = false;
	private Scene scene;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		screen = new BorderPane();
		gamePlane = new Pane();
		menu = new Menu();
		screen.setTop(menu);
		screen.setCenter(gamePlane);
		screen.setStyle("-fx-background-color: Black");
		screen.setOnKeyPressed(this::openMenu);
		scene = new Scene(screen, SCREEN_X, SCREEN_Y); //quarter size of s22 display
		scene.setCursor(Cursor.NONE);
		
		ship = new Ship(Upgrade.NONE);
		gamePlane.getChildren().add(ship);
		gamePlane.setOnMouseMoved(this::moveShip);
		gamePlane.setOnMouseDragged(this::moveShip);
		gamePlane.setOnMouseClicked(this::fireWeapon);
		
		if (!enemyPresent) {
			advanceLevel();
		}
		
		stage.setTitle("Test Game");
		stage.setScene(scene);
		stage.show();
	}
	
	private void advanceLevel() {
		levelCounter++;
		
		switch (levelCounter){
			case 1:
				e1 = new Enemy();
				gamePlane.getChildren().add(e1);
				enemyPresent = true;
				break;
			case 2:
				e1 = new Enemy();
				e2 = new Enemy();
				enemyPresent = true;
				break;
		}
	}
	
	private void moveShip(MouseEvent mouse) {
		//System.out.println(mouse.getX() + " " + mouse.getY());
		ship.setTranslateX(mouse.getX()-ship.getShipX());
		ship.setTranslateY(mouse.getY()-ship.getShipY());
	}
	
	//TODO: fix the upgrade mechanic. Its dumb right now.
	private void fireWeapon(MouseEvent mouse) {
		if (ship.getUpgrade() == Upgrade.NONE) {
			bullet = new Projectile(mouse);
			bullet.setUpgrade(Upgrade.NONE);
			gamePlane.getChildren().add(bullet);
			if (gamePlane.getChildren().get(1) == e1) {
				collisionTimer.start();
			}
			fire = new TranslateTransition();
			fire.setByY(bullet.projectileTravelLength);
			fire.setDuration(Duration.millis(bullet.getProjectileSpeed()));
			fire.setCycleCount(1);
			fire.setNode(bullet);
			fire.play();
			System.out.println(bullet.getProjectileSpeed());
			fire.setOnFinished(e -> {if (gamePlane.getChildren().contains(e1)) {
					removeElement(2);
				} else if (gamePlane.getChildren().size() > 1){
					removeElement(1);
				}
			});
		} 
	}
	
	AnimationTimer collisionTimer = new AnimationTimer() {
		@Override
		public void handle(long timeStamp) {
			if (collisionDetected()) {
				removeElement(2);
				removeElement(1);
			}
		}
	};
	
	//TODO: fix collision. Only works with last projectile
	private boolean collisionDetected() {
		if (gamePlane.getChildren().size()>=2) {
			System.out.println("checking for collisions");
			if (e1.getBoundsInParent().contains(bullet.getBoundsInParent())) {
				System.out.println(e1.getBoundsInParent());
				System.out.println("intersection");
			/*if (gamePlane.getChildren().get(1).getBoundsInParent().intersects(gamePlane.getChildren().get(2).getBoundsInParent())) {
				System.out.println("detected");
				collisionTimer.stop();*/
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//TODO: fix removal. Fucking up if 2 projectiles are in enemy.
	private void removeElement(int element) {
		gamePlane.getChildren().remove(element);
	}
	
	//TODO: fix so it isn't interruptible
	private void openMenu(KeyEvent key) {
		if (key.getCode() == KeyCode.P && menuIsOut == false) {
			menuIsOut = true;
			moveMenu = new TranslateTransition();
			moveMenu.setByX(SCREEN_X);
			moveMenu.setDuration(Duration.millis(SCREEN_X));
			moveMenu.setCycleCount(1);
			moveMenu.setNode(menu);
			moveMenu.play();
		} else if (key.getCode() == KeyCode.P && menuIsOut == true) {
			menuIsOut = false;
			moveMenu = new TranslateTransition();
			moveMenu.setByX(-SCREEN_X);
			moveMenu.setDuration(Duration.millis(SCREEN_X));
			moveMenu.setCycleCount(1);
			moveMenu.setNode(menu);
			moveMenu.play();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
