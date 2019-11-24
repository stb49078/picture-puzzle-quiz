package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Application extends javafx.application.Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws MalformedURLException {
		//String javaVersion = System.getProperty("java.version");
		//String javafxVersion = System.getProperty("javafx.version");
		//Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");


		//Scene scene = new Scene(new StackPane(l), 640, 480);


		VBox content = new VBox();
		PuzzlePieces puzzlePieces = new PuzzlePieces();
		MenuBar menuBar = new MenuBar();
		// --- Menu File
		Menu menuFile = new Menu("File");

		// --- Menu Edit
		Menu menuEdit = new Menu("Edit");

		// --- Menu View
		Menu menuView = new Menu("View");

		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);


		content.getChildren().add(menuBar);
		content.getChildren().add(puzzlePieces);
		Scene scene = new Scene(content, 640, 480);
		stage.setScene(scene);
		stage.show();
	}
}
