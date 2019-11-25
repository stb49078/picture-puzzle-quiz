package at.stefanbauer.picturepuzzlequiz;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * big thanks to https://github.com/shemnon/javafx-gradle/blob/master/samples/Ensemble2/src/main/java/ensemble/samples/graphics/PuzzlePieces.java
 * for the original version of this part
 */
public class PuzzlePane extends VBox {
	private final List<Piece> pieces = new ArrayList<>();
	private Timeline timeline;

	public PuzzlePane() {
		this.getChildren().add(new Text("Bitte Bild wählen"));
		setAlignment(Pos.BASELINE_CENTER);
	}

	public void setImage(final int id, double width, double height) throws MalformedURLException {
		this.getChildren().clear();

		File imgFile = ImageFinder.findImage(id).orElseThrow(IllegalStateException::new);

		String localUrl = imgFile.toURI().toURL().toString();
		Image image = new Image(localUrl, width - 10, height - 50, true, false);

		int numOfColumns = (int) (image.getWidth() / Piece.SIZE_X);
		int numOfRows = (int) (image.getHeight() / Piece.SIZE_Y);

		final Desk desk = new Desk(numOfColumns, numOfRows);

		pieces.clear();

		for (int col = 0; col < numOfColumns; col++) {
			for (int row = 0; row < numOfRows; row++) {
				int x = col * Piece.SIZE_X;
				int y = row * Piece.SIZE_Y;
				final Piece piece = new Piece(image, x, y,
				                              row > 0, col > 0,
				                              row < numOfRows - 1, col < numOfColumns - 1);
				pieces.add(piece);
			}
		}

		desk.getChildren().addAll(pieces);
		// create button box
	/*	Button shuffleButton = new Button("Shuffle");
		shuffleButton.setStyle("-fx-font-size: 2em;");
		shuffleButton.setOnAction(actionEvent -> {
			if (timeline != null) timeline.stop();
			timeline = new Timeline();
			for (final Piece piece : pieces) {
				piece.setActive();
				double shuffleX = Math.random() *
				                  (desk.getWidth() - Piece.SIZE_X + 48f) -
				                  24f - piece.getCorrectX();
				double shuffleY = Math.random() *
				                  (desk.getHeight() - Piece.SIZE_Y + 30f) -
				                  15f - piece.getCorrectY();
				timeline.getKeyFrames().add(
						new KeyFrame(Duration.seconds(1),
						             new KeyValue(piece.translateXProperty(), shuffleX),
						             new KeyValue(piece.translateYProperty(), shuffleY)));
			}
			timeline.playFromStart();
		});
		Button solveButton = new Button("Solve");
		solveButton.setStyle("-fx-font-size: 2em;");
		solveButton.setOnAction(actionEvent -> {
			if (timeline != null) timeline.stop();
			timeline = new Timeline();
			for (final Piece piece : pieces) {
				piece.setInactive();
				timeline.getKeyFrames().add(
						new KeyFrame(Duration.seconds(1),
						             new KeyValue(piece.translateXProperty(), 0),
						             new KeyValue(piece.translateYProperty(), 0)));
			}
			timeline.playFromStart();
		});
		HBox buttonBox = new HBox(8);
		buttonBox.getChildren().addAll(shuffleButton, solveButton);
		// create vbox for desk and buttons
		VBox vb = new VBox(10);
		vb.getChildren().addAll(desk, buttonBox);
		getChildren().addAll(vb);*/

		getChildren().add(desk);
	}

	public void revealPiece() {
		List<Piece> collect = pieces.stream()
				.filter(Piece::isPieceHidden)
				.collect(Collectors.toList());
		Collections.shuffle(collect);
		collect.stream()
				.findAny()
				.ifPresent(Piece::show);

	}

	public void hidePiece() {
		pieces.stream()
				.filter(Piece::isPieceShown)
				.min((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
				.ifPresent(Piece::hide);
	}
}