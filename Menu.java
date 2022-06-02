package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Menu extends ToolBar {
	
	private Button resume, mainMenu, exit;
	private int menuOffset = -Driver.SCREEN_X;

	public Menu() {
		resume = new Button("Resume");
		mainMenu = new Button("Main Menu");
		exit = new Button("Exit");
		this.setStyle("-fx-background-color: Purple");
		this.setTranslateX(menuOffset);
		this.getItems().addAll(resume, mainMenu, exit);
	}
}
