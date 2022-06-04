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
	private static final double OFF_SCREEN = -50.0;
	private Ship ship;
	private Projectile bullet;
	private Enemy e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
	private int levelCounter;
	private TranslateTransition moveMenu, fire;
	private boolean enemyPresent = false, menuIsOut = false;
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
		scene = new Scene(screen, SCREEN_X, SCREEN_Y); // quarter size of s22 display
		scene.setCursor(Cursor.NONE);

		ship = new Ship(Upgrade.NONE);
		gamePlane.getChildren().add(ship);
		gamePlane.setOnMouseMoved(this::moveShip);
		gamePlane.setOnMouseDragged(this::moveShip);
		gamePlane.setOnMouseClicked(this::fireWeapon);
		
		advanceLevel();

		stage.setTitle("Test Game");
		stage.setScene(scene);
		stage.show();
	}

	private void advanceLevel() {
		levelCounter++;
		
		switch (levelCounter) {
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
		// System.out.println(mouse.getX() + " " + mouse.getY());
		ship.setTranslateX(mouse.getX() - ship.getShipX());
		ship.setTranslateY(mouse.getY() - ship.getShipY());
	}

	// TODO: fix the upgrade mechanic. Its dumb right now.
	private void fireWeapon(MouseEvent mouse) {
		if (ship.getUpgrade() == Upgrade.NONE) {
			bullet = new Projectile(mouse);
			bullet.setUpgrade(Upgrade.NONE);
			gamePlane.getChildren().add(bullet);
			System.out.println("shot " + bullet);
			collisionTimer.start();
			fire = new TranslateTransition();
			fire.setByY(bullet.getProjectileTravelLength());
			fire.setDuration(Duration.millis(bullet.getProjectileSpeed()));
			fire.setCycleCount(1);
			fire.setNode(bullet);
			fire.play();
		}
	}

	AnimationTimer collisionTimer = new AnimationTimer() {
		@Override
		public void handle(long timeStamp) {
			if (collisionDetected()) {
				gamePlane.getChildren().remove(e1);
				levelWinSequence();
				if (!enemyPresent) {
					advanceLevel();
				}
			}
		}
	};

	private boolean collisionDetected() {
		System.out.println("detecting " + bullet);
		switch (levelCounter) {
		case 1:
			if (gamePlane.getChildren().size() == 1) {
				collisionTimer.stop();
				break;
			}
			if (gamePlane.getChildren().contains(bullet)) {
				Node tempBullet = bulletTracker();
				if (gamePlane.getChildren().contains(e1) && e1.getBoundsInParent().intersects(tempBullet.getBoundsInParent())) {
					System.out.println("collision found hit by " + tempBullet);
					gamePlane.getChildren().remove(tempBullet);
					return true;
				} else if (tempBullet.getBoundsInParent().getMinY() <= OFF_SCREEN) {
					gamePlane.getChildren().remove(tempBullet);
					return false;
				}
			}
		case 2:
			return false;
		}
		return false;
	}

	private Node bulletTracker() {
		for (Node item : gamePlane.getChildren()) {
			if (item.getClass() == Projectile.class) {
				return item;
			}
		}
		return null;
	}

	private void levelWinSequence() {
		// TODO: write level win seq incl: message, countdown, advance level
	}

	// TODO: fix so it isn't interruptible
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
