package gui;

//Contains GUI elements and main methods.
//By: Elliot Warren
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class Driver extends Application {
	
	/**
	 * object instantiation
	 */

	private Pane gamePlane;
	private BorderPane screen;
	private Menu menu;
	protected static final double SCREEN_MIN_Y = -50.0, SCREEN_MAX_Y = 772.0, SCREEN_MIN_X = 0.0, SCREEN_MAX_X = 360.0;
	private Ship ship;
	private Projectile bullet, b1, b2, b3, b4, b5, b6, b7, b8;
	private Timeline timer;
	private Enemy e1, e2;
	private int levelCounter, enemyCounter, hit;
	private ArrayList<Node> shipBulletList = new ArrayList<Node>(), enemyBulletList = new ArrayList<Node>();
	private TranslateTransition moveMenu;
	private boolean menuIsOut = false, menuIsMoving = false, shipHit = false, enemyHit = false, levelLost = false;
	private Scene scene;

	/**
	 * main setup, base elements
	 */
	
	@Override
	public void start(Stage stage) throws Exception {

		screen = new BorderPane();
		gamePlane = new Pane();
		menu = new Menu();
		screen.setTop(menu);
		screen.setCenter(gamePlane);
		screen.setStyle("-fx-background-color: Black");
		screen.setOnKeyPressed(this::openMenu);
		scene = new Scene(screen, SCREEN_MAX_X, SCREEN_MAX_Y); // quarter size of s22 display
		scene.setCursor(Cursor.NONE);

		ship = new Ship(Upgrade.NONE);
		gamePlane.getChildren().add(ship);
		gamePlane.setOnMouseMoved(this::moveShip);
		gamePlane.setOnMouseDragged(this::moveShip);
		switch(ship.getUpgrade()) {
		case NONE:
			gamePlane.setOnMouseClicked(this::fireWeapon);
		case AUTO:
		}

		advanceLevel();

		stage.setTitle("Test Game");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Main helper methods. Make things happen
	 * @param mouse
	 */

	private void moveShip(MouseEvent mouse) {
		//System.out.println(mouse.getX() + " " + mouse.getY());
		if (!menuIsOut) {
			ship.setTranslateX(mouse.getX() - ship.getShipX());
			ship.setTranslateY(mouse.getY() - ship.getShipY());
			switch(ship.getUpgrade()) {
			case NONE:
				gamePlane.setOnMouseClicked(this::fireWeapon);
			case AUTO:
	
			}
		}
	}

	// TODO: fix the upgrade mechanic. Its dumb right now.
	private void fireWeapon(MouseEvent mouse) {
		if (!menuIsOut) {
			bullet = new Projectile(mouse);
			bullet.setUpgrade(ship.getUpgrade());
			gamePlane.getChildren().add(bullet);
			shipBulletList.add(bullet);
			bullet.shipFire();
		}
	}
	
	private void enemyBulletHandler(int n) {
		collisionTimer.start();
		switch (n) {
		case 1:
			timer = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
				for (int i = 0; i<=3; i++) {
					bullet = new Projectile(e1);
					bullet.setUpgrade(Upgrade.ENEMY);
					gamePlane.getChildren().add(bullet);
					enemyBulletList.add(bullet);
					bullet.enemyFire(e1, i);
				}
			}));
			timer.setCycleCount(Animation.INDEFINITE);
			timer.play();
			break;
		case 2:
			
		}
	}

	AnimationTimer collisionTimer = new AnimationTimer() {
		@Override
		public void handle(long timeStamp) {
			switch (collisionDetected()) {
				case 0:
					break;
				case 1:
					timer.stop();
					if (enemyCounter < 1) {
						levelWinSequence();
					} 
					break;
				case 2:
					scene.setCursor(Cursor.DEFAULT);
					gameOverSequence();
					break;
			}
		}
	};

	private int collisionDetected() {
		if (gamePlane.getChildren().size() == 1) {
			collisionTimer.stop();
		}
		switch (levelCounter) {
		case 1:
			for (Node item : shipBulletList) {
				Bounds itemBounds = item.getBoundsInParent();
				if (e1.getBoundsInParent().intersects(itemBounds)) {
					System.out.println("enemy collision found hit by " + item);
					gamePlane.getChildren().remove(item);
					shipBulletList.remove(item);
					gamePlane.getChildren().remove(e1);
					enemyCounter--;
					enemyHit = true;
					return 1;
				} else if (item.getBoundsInParent().getMinY() <= SCREEN_MIN_Y) {
					gamePlane.getChildren().remove(item);
					shipBulletList.remove(item);
					return 0;
				}
			}
			for (Node item : enemyBulletList) {
				double itemMinY = item.getBoundsInParent().getMinY();
				double itemMaxY = item.getBoundsInParent().getMaxY();
				double itemMinX = item.getBoundsInParent().getMinX();
				double itemMaxX = item.getBoundsInParent().getMaxX();
				Bounds itemBounds = item.getBoundsInParent();
				if (ship.getBoundsInParent().intersects(itemBounds)) {
					System.out.println("ship collision found hit by " + item);
					gamePlane.getChildren().remove(item);
					enemyBulletList.remove(item);
					gamePlane.getChildren().remove(ship);
					collisionTimer.stop();
					shipHit = true;
					return 2;
				} else if (itemMinY < SCREEN_MIN_Y || itemMaxY > SCREEN_MAX_Y || itemMinX < SCREEN_MIN_X || itemMaxX > SCREEN_MAX_X) {
					gamePlane.getChildren().remove(item);
					enemyBulletList.remove(item);
					return 0;
				}
			}
		case 2:
			return 0;
		}
		return 0;
	}
	
	/**
	 * Game status change section
	 */

	private void levelWinSequence() {
		// TODO: write level win seq incl: message, countdown, advance level
		System.out.println("level won");
		//advanceLevel();
	}
	
	private void advanceLevel() {
		levelCounter++;

		switch (levelCounter) {
		case 1:
			e1 = new Enemy();
			gamePlane.getChildren().add(e1);
			collisionTimer.start();
			enemyBulletHandler(levelCounter);
			e1.enemySpin();
			enemyCounter++;
			break;
		case 2:
			e1 = new Enemy();
			e2 = new Enemy();
			enemyCounter+=2;
			break;
		}
	}
	
	private void gameOverSequence() {
		// TODO: write game over seq incl: message, buttons
		System.out.println("level lost");
		levelLost = true;
		KeyEvent lostLevel = new KeyEvent(null, null, null, KeyCode.P, false, false, false, false);
		openMenu(lostLevel);
	}

	// TODO: fix so that ship stops moving when menu is out
	private void openMenu(KeyEvent key) {
		System.out.println(key);
		moveMenu = new TranslateTransition();
		moveMenu.setDuration(Duration.millis(SCREEN_MAX_X));
		moveMenu.setCycleCount(1);
		moveMenu.setNode(menu);
		if (key.getCode() == KeyCode.P && !menuIsOut && !menuIsMoving) {
			moveMenu.setByX(SCREEN_MAX_X);
			moveMenu.play();
			scene.setCursor(Cursor.DEFAULT);
			e1.stopSpin();
			collisionTimer.stop();
			timer.stop();
			for (Node item : enemyBulletList) {
				((Projectile)item).stopProjectile();
			}
			for (Node item : shipBulletList) {
				((Projectile)item).stopProjectile();
			}
			menuIsMoving = true;
			moveMenu.setOnFinished(finish -> {
				menuIsOut = true;
				menuIsMoving = false;
			});
		} else if (key.getCode() == KeyCode.P && menuIsOut && !menuIsMoving && !levelLost) {
			moveMenu.setByX(-SCREEN_MAX_X);
			moveMenu.play();
			scene.setCursor(Cursor.NONE);
			e1.startSpin();
			collisionTimer.start();
			timer.play();
			for (Node item : enemyBulletList) {
				((Projectile)item).startProjectile();
			}
			for (Node item : shipBulletList) {
				((Projectile)item).startProjectile();
			}
			menuIsMoving = true;
			moveMenu.setOnFinished(finish -> {
				menuIsOut = false;
				menuIsMoving = false;
			});
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}