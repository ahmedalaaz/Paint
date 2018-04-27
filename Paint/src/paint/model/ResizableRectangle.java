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
	final double SMALL_CIRCLE_RADIUS = 8;
	final Paint SMALL_CIRCLE_COLOR = Color.ORANGE;
	final double SMALL_CIRCLE_STROKE = 12;
	private boolean isSquare = false;
	protected Wrapper<Point2D> mouseLocation = new Wrapper<>();

	public ResizableRectangle(double x, double y, double width, double height, Shape shape) {

		this.selectedShape = shape;
		outerSelectorRectangle = new Rectangle(x, y, width, height);

		// top left resize handle:
		northWestSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to top left corner of Rectangle:
		northWestSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		northWestSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// top right resize handle:
		northEastSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to top right corner of Rectangle:
		northEastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		northEastSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// bottom left resize handle:
		southWestSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to bottom left corner of Rectangle:
		southWestSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		southWestSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// bottom right resize handle:
		southEastSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to bottom right corner of Rectangle:
		southEastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		southEastSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// north resize handle :
		northSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to top of Rectangle :
		northSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty().divide(2)));
		northSmallCircle.centerYProperty().bind(outerSelectorRectangle.yProperty());

		// south resize handle :
		southSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to bottom of Rectangle :
		southSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty().divide(2)));
		southSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty()));

		// west resize handle :
		westSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to west of Rectangle :
		westSmallCircle.centerXProperty().bind(outerSelectorRectangle.xProperty());
		westSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty().divide(2)));

		// east resize handle :
		eastSmallCircle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
		// bind to east of Rectangle :
		eastSmallCircle.centerXProperty()
				.bind(outerSelectorRectangle.xProperty().add(outerSelectorRectangle.widthProperty()));
		eastSmallCircle.centerYProperty()
				.bind(outerSelectorRectangle.yProperty().add(outerSelectorRectangle.heightProperty().divide(2)));
		// move handle:
		moveHandle = new Circle(SMALL_CIRCLE_RADIUS, SMALL_CIRCLE_COLOR);
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
		northWestSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		northWestSmallCircle.setStroke(Color.TRANSPARENT);
		moveHandle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		moveHandle.setStroke(Color.TRANSPARENT);
		eastSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		eastSmallCircle.setStroke(Color.TRANSPARENT);
		westSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		westSmallCircle.setStroke(Color.TRANSPARENT);
		southEastSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		southEastSmallCircle.setStroke(Color.TRANSPARENT);
		southSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		southSmallCircle.setStroke(Color.TRANSPARENT);
		southWestSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		southWestSmallCircle.setStroke(Color.TRANSPARENT);
		northSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		northSmallCircle.setStroke(Color.TRANSPARENT);
		northEastSmallCircle.setStrokeWidth(SMALL_CIRCLE_STROKE);
		northEastSmallCircle.setStroke(Color.TRANSPARENT);
		
		northWestSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthWestResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());

			}
		});
		northEastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthEastResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southWestSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthWestResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southEastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthEastResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		eastSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				MultipleResizeState resizeMoveState = new EastResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, 0);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		westSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				MultipleResizeState resizeMoveState = new WestResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, 0);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		northSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new NorthResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, 0, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});
		southSmallCircle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new SouthResize();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, 0, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		moveHandle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				MultipleResizeState resizeMoveState = new MovingHandle();
				try {
					ShapesController.getInstance(Main.getController()).resizeAllSelected(resizeMoveState, deltaX, deltaY);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}

		});
		outerSelectorRectangle.setOnMousePressed((event) -> {
			this.selectedShape.removeResizableRectangle();
		});
		outerSelectorRectangle.setStrokeWidth(2d);
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
			ShapesController.getInstance(Main.getController()).changeInitial(true);
		});
	}
	public Rectangle getNode() {
		return this.outerSelectorRectangle;
	}

	public void triggerWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= SMALL_CIRCLE_RADIUS + SMALL_CIRCLE_STROKE
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setX(newX);
			this.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
	}

	public void triggerNorthWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= (SMALL_CIRCLE_RADIUS + SMALL_CIRCLE_STROKE)
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setX(newX);
			this.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setY(newY);
			this.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerNorthCircleMove(double deltaX, double deltaY) {
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setY(newY);
			this.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerNorthEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth() - SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE
				&& newMaxX >= outerSelectorRectangle.getX() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			this.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		if (newY >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE && newY <= outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight()
				- 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setY(newY);
			this.setHeight(outerSelectorRectangle.getHeight() - deltaY);
		}
	}

	public void triggerEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX >= outerSelectorRectangle.getX() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE) {
			this.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
	}

	public void triggerSouthEastCircleMove(double deltaX, double deltaY) {
		double newMaxX = outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() + deltaX;
		if (newMaxX >= outerSelectorRectangle.getX() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE) {
			this.setWidth(outerSelectorRectangle.getWidth() + deltaX);
		}
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE) {
			this.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}

	public void triggerSouthCircleMove(double deltaX, double deltaY) {
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE) {
			this.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}

	public void triggerSouthWestCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		if (newX >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE
				&& newX <= outerSelectorRectangle.getX() + outerSelectorRectangle.getWidth() - 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setX(newX);
			this.setWidth(outerSelectorRectangle.getWidth() - deltaX);
		}
		double newMaxY = outerSelectorRectangle.getY() + outerSelectorRectangle.getHeight() + deltaY;
		if (newMaxY >= outerSelectorRectangle.getY() + 2 * (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE) {
			this.setHeight(outerSelectorRectangle.getHeight() + deltaY);
		}
	}
	public void triggerMovingHandleCircleMove(double deltaX, double deltaY) {
		double newX = outerSelectorRectangle.getX() + deltaX;
		double newMaxX = newX + outerSelectorRectangle.getWidth();
		if (newX >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE
				&& newMaxX <= outerSelectorRectangle.getParent().getBoundsInLocal().getWidth()
						- (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
			outerSelectorRectangle.setX(newX);
		}
		double newY = outerSelectorRectangle.getY() + deltaY;
		double newMaxY = newY + outerSelectorRectangle.getHeight();
		if (newY >= SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE
				&& newMaxY <= outerSelectorRectangle.getParent().getBoundsInLocal().getHeight()
						- (SMALL_CIRCLE_RADIUS+SMALL_CIRCLE_STROKE)) {
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
	protected void setWidth(double width) {
		double x = this.outerSelectorRectangle.getX();
		if(x+width <= 2*SMALL_CIRCLE_RADIUS || x+width > this.outerSelectorRectangle.getParent().getBoundsInLocal().getWidth())return;
		if(isSquare) {
			double y = this.outerSelectorRectangle.getY();
			if(y+width <= 2*SMALL_CIRCLE_RADIUS || y+width > this.outerSelectorRectangle.getParent().getBoundsInLocal().getHeight())return;
			this.outerSelectorRectangle.setHeight(width);
			this.outerSelectorRectangle.setWidth(width);
		}else {
			this.outerSelectorRectangle.setWidth(width);
		}
	}
	protected void setHeight(double height) {
		double y = this.outerSelectorRectangle.getY();
		if(y+height <= 2*SMALL_CIRCLE_RADIUS || y+height > this.outerSelectorRectangle.getParent().getBoundsInLocal().getHeight())return;
		if(isSquare) {
			double x = this.outerSelectorRectangle.getX();
			if(x+height <= 2*SMALL_CIRCLE_RADIUS || x+height > this.outerSelectorRectangle.getParent().getBoundsInLocal().getWidth())return;
			this.outerSelectorRectangle.setHeight(height);
			this.outerSelectorRectangle.setWidth(height);
		}else {
			this.outerSelectorRectangle.setHeight(height);
		}
	}
	

	public void makeSquare() {
		// TODO Auto-generated method stub
		this.isSquare = true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ResizableRectangle clone = new ResizableRectangle(this.outerSelectorRectangle.getX(), this.outerSelectorRectangle.getY()
				, this.outerSelectorRectangle.getWidth(), this.outerSelectorRectangle.getHeight(), this.selectedShape);
	 clone.isSquare = this.isSquare;
		return clone;
	}	
}
