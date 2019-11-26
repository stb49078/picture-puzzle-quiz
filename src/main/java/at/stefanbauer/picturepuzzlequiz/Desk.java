package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Desk extends Pane {
	Desk(final int numOfColumns, final int numOfRows) {
		setStyle("-fx-background-color: #000000; " +
		         "-fx-border-color: #464646; " +
		         "-fx-effect: innershadow( two-pass-box , rgba(0,0,0,0.8) , 15, 0.0 , 0 , 4 );");

		final double DESK_WIDTH = Piece.SIZE_X * numOfColumns;
		final double DESK_HEIGHT = Piece.SIZE_Y * numOfRows;

		setPrefSize(DESK_WIDTH, DESK_HEIGHT);
		setMaxSize(DESK_WIDTH, DESK_HEIGHT);

		autosize();

		// create path for lines
		final Path grid = new Path();
		grid.setStroke(Color.rgb(70, 70, 70));
		getChildren().add(grid);

		// create vertical lines
		for (int col = 0; col < numOfColumns - 1; col++) {
			grid.getElements().addAll(new MoveTo(Piece.SIZE_X + Piece.SIZE_X * col, 5),
			                          new LineTo(Piece.SIZE_X + Piece.SIZE_X * col, Piece.SIZE_Y * numOfRows - 5));
		}

		// create horizontal lines
		for (int row = 0; row < numOfRows - 1; row++) {
			grid.getElements().addAll(new MoveTo(5, Piece.SIZE_Y + Piece.SIZE_Y * row),
			                          new LineTo(Piece.SIZE_X * numOfColumns - 5, Piece.SIZE_Y + Piece.SIZE_Y * row));
		}
	}

	@Override
	protected void layoutChildren() {
	}
}
