
package plugin.circle;

import java.awt.Point;



import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomCircle implements Shape {

	private Circle circle;
	private Class<CustomCircle> mClass = CustomCircle.class;
	public Class<CustomCircle> getmClass() {
		return mClass;
	}
	private ResizableRectangle resizableRectangle;
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;
	public CustomCircle() {
		circle = new Circle();
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomCircle.this.resizableRectangle = null;
				}
				CustomCircle.this.bindToResizableEllipse();
			}
		};
	}
	public CustomCircle(double radius) {
		circle = new Circle(radius);
		properties.put("radius", radius);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomCircle.this.resizableRectangle = null;
				}
				CustomCircle.this.bindToResizableEllipse();
			}
		};
	}

	protected void bindToResizableEllipse() {
		resizableRectangle = new ResizableRectangle(this.circle.getCenterX() - this.circle.getRadius(),
				this.circle.getCenterY() - this.circle.getRadius(), (this.circle.getRadius()) * 2,
				(this.circle.getRadius()) * 2, this);
		Pane canvas = (Pane) this.circle.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
		this.circle.centerXProperty().bind(resizableRectangle.getNode().xProperty().add(circle.radiusProperty()));
		this.circle.centerYProperty().bind(resizableRectangle.getNode().yProperty().add(circle.radiusProperty()));
		this.circle.radiusProperty().bind(resizableRectangle.getNode().widthProperty().divide(2));
		this.resizableRectangle.makeSquare();
	}

	public double getRadius() {
		return circle.getRadius();
	}
	public void setRadius(double radius) {
		if(circle.getParent() == null)circle.setRadius(radius);
		else if (radius + circle.getCenterX() < ((Region) circle.getParent()).getWidth()
				&& circle.getCenterX() - radius > 0&&radius + circle.getCenterY() < ((Region) circle.getParent()).getHeight()
				&& circle.getCenterY() - radius > 0)
			circle.setRadius(radius);
		properties.put("radius", radius);
	}

	public double getCenterX() {
		return circle.getCenterX();
	}

	public void setCenterX(double x) {

		circle.setCenterX(x);
		properties.put("x", x);
	}

	public double getCenterY() {
		return circle.getCenterY();
	}

	public void setCenterY(double y) {
		circle.setCenterY(y);
		properties.put("y", y);
	}

	public Node getParent() {
		return circle.getParent();
	}

	@Override
	public void setPosition(Point position) {
		if (circle.getParent() == null) {
			circle.setCenterX(position.getX());
			circle.setCenterY(position.getY());
			properties.put("x" , position.getX());
			properties.put("y" , position.getY());
			return;
		}
		if (position.getX() - circle.getRadius() >= 0
				&& position.getX() + circle.getRadius() <= circle.getParent().getBoundsInLocal().getWidth()) {
			circle.setCenterX(position.getX());
			properties.put("x" , position.getX());

		}
		if (position.getY() - circle.getRadius() >= 0
				&& position.getY() + circle.getRadius() <= circle.getParent().getBoundsInLocal().getHeight()) {
			circle.setCenterY(position.getY());
			properties.put("y" , position.getY());

		}
		
	}

	@Override
	public Point getPosition() {
		return new Point((int) circle.getCenterX(), (int) circle.getCenterY());
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return properties;
	}

	@Override
	public void setColor(Object color) {
		circle.setStroke((Paint) color);
	}

	@Override
	public Object getColor() {
		return circle.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		circle.setFill((Paint) color);

	}

	@Override
	public Object getFillColor() {
		return circle.getFill();
	}

	@Override
	public void turnOnSelectListener() {
		circle.setOnMousePressed(onMousePressed);

	}

	@Override
	public void turnOffSelectListener() {
		if (resizableRectangle != null) {
			CustomCircle.this.removeResizableRectangle();
		}
		circle.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		circle.setOnMousePressed(null);
	}

	@Override
	public void removeResizableRectangle() {
		this.resizableRectangle.remove();
		resizableRectangle = null;
	}

	@Override
	public void draw(Object canvas) {
		Pane root = (Pane) canvas;
		if(!root.getChildren().contains(circle))
		root.getChildren().add(circle);
	}

	@Override
	public boolean isSelected() {
		return this.resizableRectangle != null;
	}

	@Override
	public void setStrokeWidth(Integer value) {
		circle.setStrokeWidth(value);
	}

	@Override
	public Integer getStrokeWidth() {
		return (int) circle.getStrokeWidth();
	}
	@Override
	public void removeFromParent() {
		Pane parent = (Pane) this.circle.getParent();
		parent.getChildren().remove(this.circle);
	}

	public Object clone() throws CloneNotSupportedException {
		CustomCircle clone =  new CustomCircle(this.getRadius());
		clone.resizableRectangle = (ResizableRectangle)resizableRectangle.clone();
		clone.properties = properties;
		clone.setColor(this.getColor());
		clone.setFillColor(this.getFillColor());
		clone.setPosition(this.getPosition());
		clone.circle.setOnMousePressed(clone.circle.getOnMousePressed());
		clone.setStrokeWidth(this.getStrokeWidth());
		return clone;

	}

	@SuppressWarnings("unchecked")
	public String getJSONString() {
		// TODO Auto-generated method stub
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("x", Double.toString((this.getCenterX())));
		shapeObj.put("y", Double.toString((this.getCenterY())));
		shapeObj.put("radius", Double.toString((this.getRadius())));
		shapeObj.put("strokeWidth", Double.toString((this.circle.getStrokeWidth())));
		shapeObj.put("stroke",this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		return shapeObj.toJSONString();
	}


	@Override
	public String getXMLString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadJSON(JSONObject shape) {
		// TODO Auto-generated method stub
		double x =  Double.parseDouble((String)(shape.get("x")));
		double y = Double.parseDouble((String)(shape.get("y")));
		double radius = Double.parseDouble((String)(shape.get("radius")));
		Paint fill = Paint.valueOf((String)shape.get("fill"));
		Paint stroke = Paint.valueOf((String)shape.get("stroke"));
		double strokeWidth = Double.parseDouble((String)(shape.get("strokeWidth")));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setCenterX(x);
		this.setCenterY(y);
		this.setStrokeWidth((int)strokeWidth);
		this.setRadius(radius);
	}
	@Override
	public ResizableRectangle getResizableRectangle() {
		// TODO Auto-generated method stub
		return this.resizableRectangle;
	}
}
