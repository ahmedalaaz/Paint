
package plugin.ellipse;

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
import javafx.scene.shape.Ellipse;
import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomEllipse implements Shape {

	private Ellipse ellipse;
	private Class<CustomEllipse> mClass = CustomEllipse.class;
	public Class<CustomEllipse> getmClass() {
		return mClass;
	}
	private ResizableRectangle resizableRectangle;
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;
	public CustomEllipse() {
		ellipse = new Ellipse();
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomEllipse.this.resizableRectangle = null;
				}
				CustomEllipse.this.bindToResizableEllipse();
			}
		};
	}
	public CustomEllipse(double radiusX, double radiusY) {
		ellipse = new Ellipse(radiusX, radiusY);
		properties.put("radiusX", radiusX);
		properties.put("radiusY", radiusY);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomEllipse.this.resizableRectangle = null;
				}
				CustomEllipse.this.bindToResizableEllipse();
			}
		};
	}

	protected void bindToResizableEllipse() {
		resizableRectangle = new ResizableRectangle(this.ellipse.getCenterX() - this.ellipse.getRadiusX(),
				this.ellipse.getCenterY() - this.ellipse.getRadiusY(), (this.ellipse.getRadiusX()) * 2,
				(this.ellipse.getRadiusY()) * 2, this);
		Pane canvas = (Pane) this.ellipse.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
		this.ellipse.centerXProperty().bind(resizableRectangle.getNode().xProperty().add(ellipse.radiusXProperty()));
		this.ellipse.centerYProperty().bind(resizableRectangle.getNode().yProperty().add(ellipse.radiusYProperty()));
		this.ellipse.radiusXProperty().bind(resizableRectangle.getNode().widthProperty().divide(2));
		this.ellipse.radiusYProperty().bind(resizableRectangle.getNode().heightProperty().divide(2));
	}

	public double getRadiusX() {
		return ellipse.getRadiusX();
	}

	public double getRadiusY() {
		return ellipse.getRadiusY();
	}

	public void setRadiusX(double radiusX) {
		if(ellipse.getParent() == null)ellipse.setRadiusX(radiusX);
		else if (radiusX + ellipse.getCenterX() < ((Region) ellipse.getParent()).getWidth()
				&& ellipse.getCenterX() - radiusX > 0)
			ellipse.setRadiusX(radiusX);
		properties.put("RadiusX", radiusX);
	}

	public void setRadiusY(double radiusY) {
		if(ellipse.getParent() == null)ellipse.setRadiusY(radiusY);
		else if (radiusY + ellipse.getCenterY() < ((Region) ellipse.getParent()).getHeight()
				&& ellipse.getCenterY() - radiusY > 0)
			ellipse.setRadiusY(radiusY);
		properties.put("RadiusY", radiusY);
	}

	public double getCenterX() {
		return ellipse.getCenterX();
	}

	public void setCenterX(double x) {

		ellipse.setCenterX(x);
		properties.put("x", x);
	}

	public double getCenterY() {
		return ellipse.getCenterY();
	}

	public void setCenterY(double y) {
		ellipse.setCenterY(y);
		properties.put("y", y);
	}

	public Node getParent() {
		return ellipse.getParent();
	}

	@Override
	public void setPosition(Point position) {
		if (ellipse.getParent() == null) {
			ellipse.setCenterX(position.getX());
			ellipse.setCenterY(position.getY());
			properties.put("x" , position.getX());
			properties.put("y" , position.getY());
			return;
		}
		if (position.getX() - ellipse.getRadiusX() >= 0
				&& position.getX() + ellipse.getRadiusX() <= ellipse.getParent().getBoundsInLocal().getWidth()) {
			ellipse.setCenterX(position.getX());
			properties.put("x" , position.getX());

		}
		if (position.getY() - ellipse.getRadiusY() >= 0
				&& position.getY() + ellipse.getRadiusY() <= ellipse.getParent().getBoundsInLocal().getHeight()) {
			ellipse.setCenterY(position.getY());
			properties.put("y" , position.getY());

		}
		
	}

	@Override
	public Point getPosition() {
		return new Point((int) ellipse.getCenterX(), (int) ellipse.getCenterY());
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
		ellipse.setStroke((Paint) color);
	}

	@Override
	public Object getColor() {
		return ellipse.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		ellipse.setFill((Paint) color);

	}

	@Override
	public Object getFillColor() {
		return ellipse.getFill();
	}

	@Override
	public void turnOnSelectListener() {
		ellipse.setOnMousePressed(onMousePressed);

	}

	@Override
	public void turnOffSelectListener() {
		if (resizableRectangle != null) {
			CustomEllipse.this.removeResizableRectangle();
		}
		ellipse.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		ellipse.setOnMousePressed(null);
	}

	@Override
	public void removeResizableRectangle() {
		this.resizableRectangle.remove();
		resizableRectangle = null;
	}

	@Override
	public void draw(Object canvas) {
		Pane root = (Pane) canvas;
		if(!root.getChildren().contains(ellipse))
		root.getChildren().add(ellipse);
	}

	@Override
	public boolean isSelected() {
		return this.resizableRectangle != null;
	}

	@Override
	public void setStrokeWidth(Integer value) {
		ellipse.setStrokeWidth(value);
	}

	@Override
	public void getStrokeWidth(Integer value) {
		ellipse.getStrokeWidth();
	}

	@Override
	public void removeFromParent() {
		Pane parent = (Pane) this.ellipse.getParent();
		parent.getChildren().remove(this.ellipse);
	}

	public Object clone() throws CloneNotSupportedException {
		return null;

	}

	@SuppressWarnings("unchecked")
	public String getJSONString() {
		// TODO Auto-generated method stub
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("x", Double.toString((this.getCenterX())));
		shapeObj.put("y", Double.toString((this.getCenterY())));
		shapeObj.put("radiusX", Double.toString((this.getRadiusX())));
		shapeObj.put("radiusY", Double.toString((this.getRadiusY())));
		shapeObj.put("strokeWidth", Double.toString((this.ellipse.getStrokeWidth())));
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
		double radiusX = Double.parseDouble((String)(shape.get("radiusX")));
		double radiusY = Double.parseDouble((String)(shape.get("radiusY")));
		Paint fill = Paint.valueOf((String)shape.get("fill"));
		Paint stroke = Paint.valueOf((String)shape.get("stroke"));
		double strokeWidth = Double.parseDouble((String)(shape.get("strokeWidth")));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setCenterX(x);
		this.setCenterY(y);
		this.setStrokeWidth((int)strokeWidth);
		this.setRadiusX(radiusX);
		this.setRadiusY(radiusY);
	}
	@Override
	public ResizableRectangle getResizableRectangle() {
		// TODO Auto-generated method stub
		return this.resizableRectangle;
	}
}
