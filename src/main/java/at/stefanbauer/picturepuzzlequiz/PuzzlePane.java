package at.stefanbauer.picturepuzzlequiz;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * big thanks to https://github.com/shemnon/javafx-gradle/blob/master/samples/Ensemble2/src/main/java/ensemble/samples/graphics/PuzzlePieces.java
 * for the original version of this part
 */
public class PuzzlePane extends VBox {
	private final List<Piece> pieces = new ArrayList<>();

	private int pieceWidth = 100;
	private int pieceHeight = 100;
	private String imageURL;
	private RevealStrategy revealStrategy = RevealStrategy.RANDOM;

	public PuzzlePane() {
		this.getChildren().add(new Text("Bitte Bild wählen"));
		setAlignment(Pos.BASELINE_CENTER);
	}

	public void setPieceWidth(final int pieceWidth) {
		this.pieceWidth = pieceWidth;
	}

	public void setPieceHeight(final int pieceHeight) {
		this.pieceHeight = pieceHeight;
	}

	public void setImage(final int id) throws MalformedURLException {
		final File file = ImageFinder.findImage(id).orElseThrow(IllegalStateException::new);
		imageURL = file.toURI().toURL().toString();
	}

	public void update(final double maxDeskWidth, final double maxDeskHeight) {
		if (imageURL == null) return;

		final Image image = new Image(imageURL, maxDeskWidth - 10, maxDeskHeight - 50, true, false);
		this.getChildren().clear();

		final int numOfColumns = (int) (image.getWidth() / pieceWidth);
		final int numOfRows = (int) (image.getHeight() / pieceHeight);

		final Desk desk = new Desk(numOfColumns, numOfRows, pieceWidth, pieceHeight);

		pieces.clear();

		for (int col = 0; col < numOfColumns; col++) {
			for (int row = 0; row < numOfRows; row++) {
				pieces.add(new Piece(image, pieceWidth, pieceHeight,
				                     col * pieceWidth, row * pieceHeight,
				                     row > 0, col > 0,
				                     row < numOfRows - 1, col < numOfColumns - 1));
			}
		}

		desk.getChildren().addAll(pieces);
		getChildren().add(desk);
	}

	public void revealPiece() {
		revealStrategy.showPiece(pieces);
			}

	public void hidePiece() {
		revealStrategy.hidePiece(pieces);
	}

	public void togglePaneVisibility() {
		setVisible(!isVisible());
	}

	public void revealAllPieces() {
		pieces.forEach(Piece::show);
	}

	public void setPieceSize(final int pieceWidth, final int pieceHeight) {
		setPieceHeight(pieceHeight);
		setPieceWidth(pieceWidth);
	}

	public void setRevealStrategy(RevealStrategy revealStrategy) {
		this.revealStrategy = revealStrategy;
	}
}