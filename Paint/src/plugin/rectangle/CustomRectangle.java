package plugin.rectangle;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import paint.model.ResizableRectangle;
import paint.model.Shape;
import paint.view.Main;

public class CustomRectangle implements Shape {
	private Rectangle rectangle;
	ResizableRectangle  resizableRectangle;
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;
	public CustomRectangle(double width, double height) {
		rectangle = new Rectangle(width, height);
		properties.put("width", width);
		properties.put("length",height);
		onMousePressed =  new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(resizableRectangle != null) {
					CustomRectangle.this.removeResizableRectangle();
					return;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomRectangle.this.bindToResizableRectangle();
			}

		
		};
		
	}
	protected void bindToResizableRectangle() {
		resizableRectangle =  new ResizableRectangle(this.rectangle.getX(), this.rectangle.getY(), this.rectangle.getWidth()
				, this.rectangle.getHeight());
		Pane canvas = (Pane)this.rectangle.getParent();
		resizableRectangle.addToParent(canvas);
		this.rectangle.widthProperty().bind(resizableRectangle.getNode().widthProperty());
		this.rectangle.heightProperty().bind(resizableRectangle.getNode().heightProperty());
		this.rectangle.xProperty().bind(resizableRectangle.getNode().xProperty());
		this.rectangle.yProperty().bind(resizableRectangle.getNode().yProperty());
		
	}
	public double getWidth() {
		return rectangle.getWidth();
	}
	public double getHeight() {
		return rectangle.getHeight();
	}
	public void setWidth(double width) {
		rectangle.setWidth(width);
		properties.put("width", width);
	}
	public void setHeight(double height) {
		rectangle.setHeight(height);
		properties.put("length",height);
	}
	public double getX() {
		return rectangle.getX();
	}
	public void setX(double x) {
		
		rectangle.setX(x);
		properties.put("x",x);
	}
	public double getY() {
		return rectangle.getY();
	}
	public void setY(double y) {
		rectangle.setY(y);
		properties.put("y",y);
	}
	public Node getParent() {
		return rectangle.getParent();
	}
	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		if(rectangle.getParent() == null) {
		    rectangle.setX(position.getX());
			rectangle.setY(position.getY());
	        return ;    	
		}
		if (position.getX() >= 0 && position.getX() + rectangle.getWidth() <= rectangle.getParent().getBoundsInLocal().getWidth() ) {
            rectangle.setX(position.getX());
        }
		if (position.getY() >= 0 && position.getY()+ rectangle.getHeight() <= rectangle .getParent().getBoundsInLocal().getHeight() ) {
			rectangle.setY(position.getY());
        }
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point((int)rectangle.getX(), (int)rectangle.getY());
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		this.properties = properties;

	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	public void setColor(Object color) {
		// TODO Auto-generated method stub
		rectangle.setStroke((Paint)color);
	}

	@Override
	public Paint getColor() {
		// TODO Auto-generated method stub
		return rectangle.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		// TODO Auto-generated method stub
		rectangle.setFill((Paint)color);
	}

	@Override
	public Paint getFillColor() {
		// TODO Auto-generated method stub
		return rectangle.getFill();
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Pane root = (Pane)canvas;
		root.getChildren().add(rectangle);
	}

	public Object clone() throws CloneNotSupportedException{
		return null;
		
	}
	@Override
	public void turnOnSelectListener() {
		// TODO Auto-generated method stub
		rectangle.setOnMousePressed(onMousePressed);
			
	}
	@Override
	public void turnOffSelectListener() {
		// TODO Auto-generated method stub
		if(resizableRectangle!=null) {
			CustomRectangle.this.removeResizableRectangle();		}
		rectangle.removeEventHandler(MouseEvent.MOUSE_PRESSED,onMousePressed);
		rectangle.setOnMousePressed(null);
	}
	@Override
	public void removeResizableRectangle() {
		// TODO Auto-generated method stub
		this.resizableRectangle.remove();
		resizableRectangle = null;
		
	}
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return this.resizableRectangle != null;
	}
	@Override
	public void setStrokeWidth(Integer value) {
		// TODO Auto-generated method stub
		rectangle.setStrokeWidth(value);
	}
	@Override
	public void getStrokeWidth(Integer value) {
		// TODO Auto-generated method stub
		rectangle.getStrokeWidth();
	}
	

}
