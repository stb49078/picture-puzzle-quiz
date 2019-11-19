package at.stefanbauer.picturepuzzlequiz;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

	private double startDragX;
	private double startDragY;

	private Shape pieceStroke;
	private Shape pieceClip;
	private ImageView imageView = new ImageView();
	private Point2D dragAnchor;

	public Piece(Image image, final double correctX, final double correctY,
				 boolean topTab, boolean leftTab, boolean bottomTab, boolean rightTab,
				 final double deskWidth, final double deskHeight) {

		this.correctX = correctX;
		this.correctY = correctY;
		this.hasTopTab = topTab;
		this.hasLeftTab = leftTab;
		this.hasBottomTab = bottomTab;
		this.hasRightTab = rightTab;
		// configure clip
		pieceClip = createPiece();
		pieceClip.setFill(Color.WHITE);
		pieceClip.setStroke(null);
		// add a stroke
		pieceStroke = createPiece();
		pieceStroke.setFill(null);
		pieceStroke.setStroke(Color.BLACK);
		// create image view
		imageView.setImage(image);
		imageView.setClip(pieceClip);
		setFocusTraversable(true);
		getChildren().addAll(imageView, pieceStroke);
		// turn on caching so the jigsaw piece is fasr to draw when dragging
		setCache(true);
		// start in inactive state
		setInactive();
		// add listeners to support dragging
		setOnMousePressed(new EventHandler<>() {
			public void handle(MouseEvent me) {
				toFront();
				startDragX = getTranslateX();
				startDragY = getTranslateY();
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
			}
		});
		setOnMouseReleased(me -> {
			if (getTranslateX() < (10) && getTranslateX() > (-10) &&
					getTranslateY() < (10) && getTranslateY() > (-10)) {
				setTranslateX(0);
				setTranslateY(0);
				setInactive();
			}
		});
		setOnMouseDragged(me -> {
			double newTranslateX = startDragX
					+ me.getSceneX() - dragAnchor.getX();
			double newTranslateY = startDragY
					+ me.getSceneY() - dragAnchor.getY();
			double minTranslateX = -45f - correctX;
			double maxTranslateX = (deskWidth - Piece.SIZE_X + 50f) - correctX;
			double minTranslateY = -30f - correctY;
			double maxTranslateY = (deskHeight - Piece.SIZE_Y + 70f) - correctY;
			if ((newTranslateX > minTranslateX) &&
					(newTranslateX < maxTranslateX) &&
					(newTranslateY > minTranslateY) &&
					(newTranslateY < maxTranslateY)) {
				setTranslateX(newTranslateX);
				setTranslateY(newTranslateY);
			}
		});
	}

	private Shape createPiece() {
		Shape shape = createPieceRectangle();
		if (hasRightTab) {
			shape = Shape.union(shape,
					createPieceTab(69.5f, 0f,
							10f, 17.5f,
							50f, -12.5f,
							11.5f, 25f, 56.25f, -14f, 6.25f, 56.25f, 14f, 6.25f));
		}
		if (hasBottomTab) {
			shape = Shape.union(shape,
					createPieceTab(0f, 69.5f, 17.5f, 10f, -12.5f, 50f, 25f,
							11f, -14f, 56.25f, 6.25f, 14f, 56.25f, 6.25f));
		}
		if (hasLeftTab) {
			shape = Shape.subtract(shape,
					createPieceTab(-31f, 0f, 10f, 17.5f, -50f, -12.5f, 11f,
							25f, -43.75f, -14f, 6.25f, -43.75f, 14f, 6.25f));
		}
		if (hasTopTab) {
			shape = Shape.subtract(shape,
					createPieceTab(0f, -31f, 17.5f, 10f, -12.5f, -50f, 25f,
							12.5f, -14f, -43.75f, 6.25f, 14f, -43.75f, 6.25f));
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

		Ellipse e = new Ellipse(
				eclipseCenterX * SIZE_X / 100.0d, eclipseCenterY * SIZE_Y / 100.0d,
				eclipseRadiusX * SIZE_X / 100.0d, eclipseRadiusY * SIZE_Y / 100.0d);
		Rectangle r = new Rectangle(
				rectangleX * SIZE_X / 100.0d, rectangleY * SIZE_Y / 100.0d,
				rectangleWidth * SIZE_X / 100.0d, rectangleHeight * SIZE_Y / 100.0d);
		Shape tab = Shape.union(e, r);
		Circle c1 = new Circle(circle1CenterX * SIZE_X / 100.0d, circle1CenterY * SIZE_Y / 100.0d, circle1Radius);
		tab = Shape.subtract(tab, c1);
		Circle c2 = new Circle(circle2CenterX * SIZE_X / 100.0d, circle2CenterY * SIZE_Y / 100.0d, circle2Radius);
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
}
