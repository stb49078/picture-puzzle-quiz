package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;

/**
 * "c:\Program Files\Java\jdk1.8.0_181\bin\java.exe" -jar picture-puzzle-quiz-1.0-SNAPSHOT.jar
 */
public class Application extends javafx.application.Application {
	private PuzzlePane puzzlePane;
	private VBox content;

	public static void main(final String[] args) {
		launch();
	}

	public void setImage(final int id) {
		try {
			puzzlePane.setImage(id);
			puzzlePane.update(content.getWidth(), content.getHeight());

		} catch (final MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(final Stage stage) {
		stage.setTitle("https://github.com/stb49078/picture-puzzle-quiz");
		stage.setFullScreen(true);
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

		{
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
		}
		{
			final Menu menuPieceSize = new Menu("Teilegröße");
			menuBar.getMenus().add(menuPieceSize);
			{
				final MenuItem itemSize = new MenuItem("130x130px");
				menuPieceSize.getItems().add(itemSize);
				itemSize.setOnAction(event -> {
					puzzlePane.setPieceSize(130, 130);
					puzzlePane.update(content.getWidth(), content.getHeight());
				});
			}
			{
				final MenuItem itemSize = new MenuItem("100x100px");
				menuPieceSize.getItems().add(itemSize);
				itemSize.setOnAction(event -> {
					puzzlePane.setPieceSize(100, 100);
					puzzlePane.update(content.getWidth(), content.getHeight());
				});
			}
			{
				final MenuItem itemSize = new MenuItem("80x80px");
				menuPieceSize.getItems().add(itemSize);
				itemSize.setOnAction(event -> {
					puzzlePane.setPieceSize(80, 80);
					puzzlePane.update(content.getWidth(), content.getHeight());
				});
			}
			{
				final MenuItem itemSize = new MenuItem("80x60px");
				menuPieceSize.getItems().add(itemSize);
				itemSize.setOnAction(event -> {
					puzzlePane.setPieceSize(80, 60);
					puzzlePane.update(content.getWidth(), content.getHeight());
				});
			}
		}
		{
			final Menu revealStrategyMenu = new Menu("Aufdecken");
			menuBar.getMenus().add(revealStrategyMenu);
			for (final RevealStrategy revealStrategy : RevealStrategy.values()) {
				final MenuItem revealStrategyMenuItem = new MenuItem(revealStrategy.getCaption());
				revealStrategyMenu.getItems().add(revealStrategyMenuItem);
				revealStrategyMenuItem.setOnAction(event -> {
					puzzlePane.setRevealStrategy(revealStrategy);
					puzzlePane.update(content.getWidth(), content.getHeight());
				});
			}
		}
		{
			final Menu exitMenu = new Menu("Beenden");
			menuBar.getMenus().add(exitMenu);

			final MenuItem exitMenuItem = new MenuItem("Freibier für den Programmierer!!!");
			exitMenuItem.setOnAction(event -> endApplication());
			exitMenu.getItems().add(exitMenuItem);
		}
		content.getChildren().add(menuBar);
		content.getChildren().add(puzzlePane);

		final Scene scene = new Scene(content);

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ENTER:
					puzzlePane.togglePaneVisibility();
					break;
				case SPACE:
					puzzlePane.revealPiece();
					break;
				case BACK_SPACE:
					puzzlePane.hidePiece();
					break;
				case END:
					puzzlePane.revealAllPieces();
			}
		});

		stage.setScene(scene);
		stage.show();
	}

	private void endApplication() {
		System.exit(0);
	}
}
