package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Node that represents a puzzle piece
 */
public class Piece extends Parent {
	public static final int SIZE_X = 100;
	public static final int SIZE_Y = 100;//todo -> 80?
	private final double correctX;
	private final double correctY;

	private final boolean hasTopTab;
	private final boolean hasLeftTab;
	private final boolean hasBottomTab;
	private final boolean hasRightTab;

	private ImageView imageView = new ImageView();
	private boolean pieceShown = false;

	public Piece(final Image image, final double correctX, final double correctY, final boolean topTab, final boolean leftTab, final boolean bottomTab, final boolean rightTab) {
		this.correctX = correctX;
		this.correctY = correctY;
		this.hasTopTab = topTab;
		this.hasLeftTab = leftTab;
		this.hasBottomTab = bottomTab;
		this.hasRightTab = rightTab;


		final Shape pieceClip = createPiece();
		pieceClip.setFill(Color.WHITE);
		pieceClip.setStroke(null);


		final Shape pieceStroke = createPiece();
		pieceStroke.setFill(null);
		pieceStroke.setStroke(Color.BLACK);

		imageView.setImage(image);
		imageView.setClip(pieceClip);
		setFocusTraversable(true);
		getChildren().addAll(imageView, pieceStroke);
		//imageView.setVisible(false);

		setCache(true);
		setInactive();
	}

	private Shape createPiece() {
		final double xFactor = Piece.SIZE_X / 100.0d;
		final double yFactor = Piece.SIZE_Y / 100.0d;

		Shape shape = createPieceRectangle();

		if (hasRightTab) {
			shape = Shape.union(shape, createPieceTab(69.5f * xFactor, 0f * yFactor,
			                                          10f * xFactor, 17.5f * yFactor,
			                                          50f * xFactor, -12.5f * yFactor,
			                                          11.5f * xFactor, 25f * yFactor,
			                                          56.25f * xFactor, -14f * yFactor, 6.25f,
			                                          56.25f * xFactor, 14f * yFactor, 6.25f));
		}

		if (hasBottomTab) {
			shape = Shape.union(shape, createPieceTab(0f * xFactor, 69.5f * yFactor,
			                                          17.5f * xFactor, 10f * yFactor,
			                                          -12.5f * xFactor, 50f * yFactor,
			                                          25f * xFactor, 11f * yFactor,
			                                          -14f * xFactor, 56.25f * yFactor, 6.25f,
			                                          14f * xFactor, 56.25f * yFactor, 6.25f));
		}

		if (hasLeftTab) {
			shape = Shape.subtract(shape, createPieceTab(-31f * xFactor, 0f * yFactor,
			                                             10f * xFactor, 17.5f * yFactor,
			                                             -50f * xFactor, -12.5f * yFactor,
			                                             11f * xFactor, 25f * yFactor,
			                                             -43.75f * xFactor, -14f * yFactor, 6.25f,
			                                             -43.75f * xFactor, 14f * yFactor, 6.25f));
		}

		if (hasTopTab) {
			shape = Shape.subtract(shape, createPieceTab(0f * xFactor, -31f * yFactor,
			                                             17.5f * xFactor, 10f * yFactor,
			                                             -12.5f * xFactor, -50f * yFactor,
			                                             25f * xFactor, 12.5f * yFactor,
			                                             -14f * xFactor, -43.75f * yFactor, 6.25f,
			                                             14f * xFactor, -43.75f * yFactor, 6.25f));
		}

		shape.setTranslateX(correctX);
		shape.setTranslateY(correctY);
		shape.setLayoutX(50f * xFactor);
		shape.setLayoutY(50f * yFactor);
		return shape;
	}

	private Rectangle createPieceRectangle() {
		final double xFactor = Piece.SIZE_X / 100.0d;
		final double yFactor = Piece.SIZE_Y / 100.0d;

		final Rectangle rec = new Rectangle();
		rec.setX(-50 * yFactor);
		rec.setY(-50 * xFactor);
		rec.setWidth(SIZE_X);
		rec.setHeight(SIZE_Y);
		return rec;
	}

	private Shape createPieceTab(final double eclipseCenterX, final double eclipseCenterY, final double eclipseRadiusX, final double eclipseRadiusY,
	                             final double rectangleX, final double rectangleY, final double rectangleWidth, final double rectangleHeight,
	                             final double circle1CenterX, final double circle1CenterY, final double circle1Radius,
	                             final double circle2CenterX, final double circle2CenterY, final double circle2Radius) {

		final Ellipse e = new Ellipse(eclipseCenterX * SIZE_X, eclipseCenterY * SIZE_Y,
		                              eclipseRadiusX * SIZE_X, eclipseRadiusY * SIZE_Y);

		final Rectangle r = new Rectangle(rectangleX * SIZE_X, rectangleY * SIZE_Y,
		                                  rectangleWidth * SIZE_X, rectangleHeight * SIZE_Y);

		Shape tab = Shape.union(e, r);
		final Circle c1 = new Circle(circle1CenterX * SIZE_X, circle1CenterY * SIZE_Y, circle1Radius);
		tab = Shape.subtract(tab, c1);
		final Circle c2 = new Circle(circle2CenterX * SIZE_X, circle2CenterY * SIZE_Y, circle2Radius);
		tab = Shape.subtract(tab, c2);
		return tab;
	}

	public void setActive() {
		setDisable(false);
		setEffect(new DropShadow());
		toFront();
	}

	public void setInactive() {
		setEffect(null);
		setDisable(true);
		toBack();
	}

	public double getCorrectX() {
		return correctX;
	}

	public double getCorrectY() {
		return correctY;
	}

	public boolean isPieceShown() {
		return pieceShown;
	}

	public void setPieceShown(final boolean pieceShown) {
		this.pieceShown = pieceShown;
		imageView.setVisible(pieceShown);
	}

	public boolean isPieceHidden() {
		return !pieceShown;
	}

	public void show() {
		setPieceShown(true);
	}

	public void hide() {
		setPieceShown(false);
	}
}
