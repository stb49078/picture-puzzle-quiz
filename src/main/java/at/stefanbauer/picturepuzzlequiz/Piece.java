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

	public Piece(Image image, final double correctX, final double correctY, boolean topTab, boolean leftTab, boolean bottomTab, boolean rightTab) {
		this.correctX = correctX;
		this.correctY = correctY;
		this.hasTopTab = topTab;
		this.hasLeftTab = leftTab;
		this.hasBottomTab = bottomTab;
		this.hasRightTab = rightTab;


		Shape pieceClip = createPiece();
		pieceClip.setFill(Color.WHITE);
		pieceClip.setStroke(null);


		Shape pieceStroke = createPiece();
		pieceStroke.setFill(null);
		pieceStroke.setStroke(Color.BLACK);

		imageView.setImage(image);
		imageView.setClip(pieceClip);

		getChildren().addAll(imageView, pieceStroke);
		//imageView.setVisible(false);

		setCache(true);
		setInactive();
	}

	private Shape createPiece() {
		double xFactor = Piece.SIZE_X / 100.0d;
		double yFactor = Piece.SIZE_Y / 100.0d;

		Shape shape = createPieceRectangle();

		if (hasRightTab) {
			shape = Shape.union(shape, createPieceTab(69.5f * xFactor, 0f*yFactor,
			                                          10f*xFactor, 17.5f*yFactor,
			                                          50f*xFactor, -12.5f*yFactor,
			                                          11.5f*xFactor, 25f*yFactor,
			                                          56.25f*xFactor, -14f*yFactor, 6.25f,
			                                          56.25f*xFactor, 14f*yFactor, 6.25f));
		}

		if (hasBottomTab) {
			shape = Shape.union(shape, createPieceTab(0f, 69.5f,
			                                          17.5f, 10f,
			                                          -12.5f, 50f,
			                                          25f, 11f,
			                                          -14f, 56.25f, 6.25f,
			                                          14f, 56.25f, 6.25f));
		}

		if (hasLeftTab) {
			shape = Shape.subtract(shape, createPieceTab(-31f, 0f,
			                                             10f, 17.5f,
			                                             -50f, -12.5f,
			                                             11f, 25f,
			                                             -43.75f, -14f, 6.25f,
			                                             -43.75f, 14f, 6.25f));
		}

		if (hasTopTab) {
			shape = Shape.subtract(shape, createPieceTab(0f, -31f,
			                                             17.5f, 10f,
			                                             -12.5f, -50f,
			                                             25f, 12.5f,
			                                             -14f, -43.75f, 6.25f,
			                                             14f, -43.75f, 6.25f));
		}

		shape.setTranslateX(correctX);
		shape.setTranslateY(correctY);
		shape.setLayoutX(50f);
		shape.setLayoutY(50f);
		return shape;
	}

	private Rectangle createPieceRectangle() {
		Rectangle rec = new Rectangle();
		rec.setX(-50);
		rec.setY(-50);
		rec.setWidth(SIZE_X);
		rec.setHeight(SIZE_Y);
		return rec;
	}

	private Shape createPieceTab(double eclipseCenterX, double eclipseCenterY, double eclipseRadiusX, double eclipseRadiusY,
	                             double rectangleX, double rectangleY, double rectangleWidth, double rectangleHeight,
	                             double circle1CenterX, double circle1CenterY, double circle1Radius,
	                             double circle2CenterX, double circle2CenterY, double circle2Radius) {

		Ellipse e = new Ellipse(eclipseCenterX * SIZE_X, eclipseCenterY * SIZE_Y,
		                        eclipseRadiusX * SIZE_X, eclipseRadiusY * SIZE_Y);

		Rectangle r = new Rectangle(rectangleX * SIZE_X, rectangleY * SIZE_Y,
		                            rectangleWidth * SIZE_X, rectangleHeight * SIZE_Y);

		Shape tab = Shape.union(e, r);
		Circle c1 = new Circle(circle1CenterX * SIZE_X, circle1CenterY * SIZE_Y, circle1Radius);
		tab = Shape.subtract(tab, c1);
		Circle c2 = new Circle(circle2CenterX * SIZE_X, circle2CenterY * SIZE_Y, circle2Radius);
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

	public void setPieceShown(boolean pieceShown) {
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
