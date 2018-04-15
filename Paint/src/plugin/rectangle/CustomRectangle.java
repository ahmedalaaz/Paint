package plugin.rectangle;

import java.awt.Point;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import paint.model.Shape;

public class CustomRectangle implements Shape {
	private Rectangle rectangle;
	public CustomRectangle(double width, double height) {
		rectangle = new Rectangle(width, height);	
	
	}
	public double getWidth() {
		return rectangle.getWidth();
	}
	public double getHeight() {
		return rectangle.getHeight();
	}
	public void setWidth(double width) {
		rectangle.setWidth(width);
	}
	public void setHeight(double height) {
		rectangle.setHeight(height);
	}
	public double getX() {
		return rectangle.getX();
	}
	public void setX(double x) {
		rectangle.setX(x);
	}
	public double getY() {
		return rectangle.getY();
	}
	public void setY(double y) {
		rectangle.setY(y);
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

	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return null;
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

}
