package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Desk extends Pane {
	public Desk(final int numOfColumns, final int numOfRows, final double pieceWidth, final double pieceHeight) {
		setStyle("-fx-background-color: #000000; " +
		         "-fx-border-color: #464646; " +
		         "-fx-effect: innershadow( two-pass-box , rgba(0,0,0,0.8) , 15, 0.0 , 0 , 4 );");

		final double deskWidth = pieceWidth * numOfColumns;
		final double deskHeight = pieceHeight * numOfRows;

		setPrefSize(deskWidth, deskHeight);
		setMaxSize(deskWidth, deskHeight);

		autosize();

		// create path for lines
		final Path grid = new Path();
		grid.setStroke(Color.rgb(70, 70, 70));
		getChildren().add(grid);

		// create vertical lines
		for (int col = 0; col < numOfColumns - 1; col++) {
			grid.getElements().addAll(new MoveTo(pieceWidth * (col + 1), 5),
			                          new LineTo(pieceWidth * (col + 1), pieceHeight * numOfRows - 5));
		}

		// create horizontal lines
		for (int row = 0; row < numOfRows - 1; row++) {
			grid.getElements().addAll(new MoveTo(5, pieceHeight * (row + 1)),
			                          new LineTo(pieceWidth * numOfColumns - 5, pieceHeight * (row + 1)));
		}
	}

	@Override
	protected void layoutChildren() {
	}
}
