package paint.model;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import paint.controller.ShapesController;
import paint.view.Main;

public class ResizableRectangle {
	protected Rectangle outerSelectorRectangle;
	protected Pane tempParent;
	protected Circle northWestSmallCircle;
	protected Circle northEastSmallCircle;
	protected Circle southWestSmallCircle;
	protected Circle southEastSmallCircle;
	protected Circle northSmallCircle;
	protected Circle southSmallCircle;
	protected Circle westSmallCircle;
	protected Circle eastSmallCircle;
	protected Circle moveHandle;
	protected Shape selectedShape;
	final double smallCirclesRadius = 10;
	final Paint smallCircleColor = Color.ORANGE;
	protected Wrapper<Point2D> mouseLocation = new Wrapper<>();

	public ResizableRectangle(double x, double y, double width, double height, Shape shape) {

		this.selectedShape = shape;
		outerSelectorRectangle = new Rectangle(x, y, width, height);

		// top left resize handle:
		northWestSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to top left corner of Rectangle:
		northWestSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		northWestSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// top right resize handle:
		northEastSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to top right corner of Rectangle:
		northEastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		northEastSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// bottom left resize handle:
		southWestSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to bottom left corner of Rectangle:
		southWestSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		southWestSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// bottom right resize handle:
		southEastSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to bottom right corner of Rectangle:
		southEastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		southEastSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// north resize handle :
		northSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to top of Rectangle :
		northSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty().divide(2)));
		northSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// south resize handle :
		southSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to bottom of Rectangle :
		southSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty().divide(2)));
		southSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// west resize handle :
		westSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to west of Rectangle :
		westSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		westSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty().divide(2)));

		// east resize handle :
		eastSmallCircle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to east of Rectangle :
		eastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		eastSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty().divide(2)));
		// move handle:
		moveHandle = new Circle(smallCirclesRadius, smallCircleColor);
		// bind to bottom center of Rectangle:
		moveHandle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty().divide(2)));
		moveHandle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty().divide(2)));

		setUpDragging(northWestSmallCircle, mouseLocation);
		setUpDragging(northEastSmallCircle, mouseLocation);
		setUpDragging(northSmallCircle, mouseLocation);
		setUpDragging(southEastSmallCircle, mouseLocation);
		setUpDragging(southSmallCircle, mouseLocation);
		setUpDragging(southWestSmallCircle, mouseLocation);
		setUpDragging(eastSmallCircle, mouseLocation);
		setUpDragging(westSmallCircle, mouseLocation);
		setUpDragging(moveHandle, mouseLocation);
		northWestSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthWestResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());

			}
		});
		northEastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthEastResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southWestSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthWestResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southEastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthEastResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		eastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				MultipleResizeState resizeMoveState = new EastResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, 0);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		westSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				MultipleResizeState resizeMoveState = new WestResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, 0);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		northSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, 0, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthResize();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, 0, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		moveHandle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new MovingHandle();
				ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}

		});
		outerSelectorRectangle.setOnMousePressed((event) -> {
			this.selectedShape.removeResizableRectangle();
		});
		outerSelectorRectangle.setStrokeWidth(6d);
		outerSelectorRectangle.getStrokeDashArray().addAll(46d, 2d, 4d);
		outerSelectorRectangle.setFill(Color.TRANSPARENT);
		outerSelectorRectangle.setStroke(Paint.valueOf("#B0C4DE"));
	}

	private void setUpDragging(Circle circle, Wrapper<Point2D> mouseLocation) {

		circle.setOnDragDetected(event -> {
			circle.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});

		circle.setOnMouseReleased(event -> {
			circle.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}
	public Rectangle getNode() {
		return this.outerSelectorRectangle;
	}

	public void triggerWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= 2 * smallCirclesRadius
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * smallCirclesRadius) {
			outerSelectorRectangle.setX(newX);
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
	}

	public void triggerNorthWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= 2 * smallCirclesRadius
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * smallCirclesRadius) {
			outerSelectorRectangle.setX(newX);
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= 2 * smallCirclesRadius && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setY(newY);
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerNorthCircleMove(double deltaX, double deltaY) {
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= 2 * smallCirclesRadius && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setY(newY);
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerNorthEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth() - 2 * smallCirclesRadius
				&& newMaxX >= outerSelectorRectangle.getX() + 2 * smallCirclesRadius) {
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= 2 * smallCirclesRadius && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setY(newY);
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX >= outerSelectorRectangle.getX() + 2 * smallCirclesRadius
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
	}

	public void triggerSouthEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX >= outerSelectorRectangle.getX() + 2 * smallCirclesRadius
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * smallCirclesRadius
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}

	public void triggerSouthCircleMove(double deltaX, double deltaY) {
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * smallCirclesRadius
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}

	public void triggerSouthWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= 2 * smallCirclesRadius
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * smallCirclesRadius) {
			outerSelectorRectangle.setX(newX);
			outerSelectorRectangle.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * smallCirclesRadius
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}
	public void triggerMovingHandleCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		double newMaxX = newX + outerSelectorRectangle.getWidth();
		if (newX >= 2 * smallCirclesRadius
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setX(newX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		double newMaxY = newY + outerSelectorRectangle.getHeight();
		if (newY >= 2 * smallCirclesRadius
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- 2 * smallCirclesRadius) {
			outerSelectorRectangle.setY(newY);
		}
	}

	static class Wrapper<T> {
		T value;
	}

	public void addToParent(Pane parent) {
		parent.getChildren().addAll(this.outerSelectorRectangle, this.northEastSmallCircle, this.northSmallCircle,
				this.northWestSmallCircle, this.southEastSmallCircle, this.southSmallCircle, this.southWestSmallCircle,
				this.westSmallCircle, this.eastSmallCircle, this.moveHandle);
	}

	public void remove() {
		// TODO Auto-generated method stub
		Pane canvas = (Pane) this.outerSelectorRectangle.getParent();
		tempParent = canvas;
		canvas.getChildren().removeAll(this.outerSelectorRectangle, this.northEastSmallCircle, this.northSmallCircle,
				this.northWestSmallCircle, this.southEastSmallCircle, this.southSmallCircle, this.southWestSmallCircle,
				this.westSmallCircle, this.eastSmallCircle, this.moveHandle);
	}

}
