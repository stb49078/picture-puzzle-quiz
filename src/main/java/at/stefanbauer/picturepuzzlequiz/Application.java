package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Application extends javafx.application.Application {
	private PuzzlePane puzzlePane;
	private VBox content;

	public static void main(final String[] args) {
		launch();
	}

	public void setImage(final int id) {
		try {
			puzzlePane.setImage(id, content.getWidth(), content.getHeight());
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(final Stage stage) {
		stage.setTitle("blub");
		//stage.initStyle(StageStyle.UTILITY);
		//TODO stage.setFullScreen(true);
		stage.setMaximized(true);

		content = new VBox();
		puzzlePane = new PuzzlePane();
		final MenuBar menuBar = new MenuBar();

		content.setStyle("-fx-background-color: #000000; " +
		                 "-fx-border-color: #000000; " +
		                 "-fx-text-fill: #cccccc");

		menuBar.setStyle("-fx-background-color: #000000; " +
		                 "-fx-border-color: #000000; " +
		                 "-fx-text-fill: #cccccc");

		final Menu menuPicture = new Menu("Bild");

		for (int i = 1; i <= 20; i++) {
			if (ImageFinder.findImage(i).isPresent()) {
				final MenuItem imageItem = new MenuItem("Bild " + i);
				final int finalI = i;
				imageItem.setOnAction(event -> setImage(finalI));
				menuPicture.getItems().add(imageItem);
			}
		}

		menuBar.getMenus().add(menuPicture);

		final Menu exitMenu = new Menu("Beenden");
		menuBar.getMenus().add(exitMenu);

		final MenuItem exitMenuItem = new MenuItem("bis zum nächsten mal");
		exitMenuItem.setOnAction(event -> endApplication());
		exitMenu.getItems().add(exitMenuItem);

		content.getChildren().add(menuBar);
		content.getChildren().add(puzzlePane);

		final Scene scene = new Scene(content);

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ENTER:
				case SPACE:
					puzzlePane.revealPiece();
					break;
				case BACK_SPACE:
					puzzlePane.hidePiece();
					break;
			}
		});

		stage.setScene(scene);
		stage.show();
	}

	private void endApplication() {
		System.exit(0);
	}
}
