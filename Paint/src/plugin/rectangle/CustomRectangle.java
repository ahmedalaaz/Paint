package plugin.rectangle;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import paint.model.Shape;

public class CustomRectangle implements Shape {
	private Rectangle rectangle;
	public CustomRectangle(double width, double height) {
		rectangle = new Rectangle(width, height);	
	}	
	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		rectangle.setX(position.getX());
		rectangle.setY(position.getY());
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
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFillColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return null;
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
