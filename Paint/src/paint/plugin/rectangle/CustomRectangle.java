package paint.plugin.rectangle;

import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomRectangle implements Shape {
	private Rectangle rectangle;
	private ResizableRectangle resizableRectangle;
	private Class<CustomRectangle> mClass = CustomRectangle.class;

	public Class<CustomRectangle> getmClass() {
		return mClass;
	}

	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;

	public CustomRectangle() {
		rectangle = new Rectangle();
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomRectangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomRectangle.this.bindToResizableRectangle();
			}
		};
	}

	public CustomRectangle(double width, double height) {
		rectangle = new Rectangle(width, height);
		properties.put("width", width);
		properties.put("length", height);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomRectangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomRectangle.this.bindToResizableRectangle();
			}
		};
	}

	protected void bindToResizableRectangle() {
		resizableRectangle = new ResizableRectangle(this.rectangle.getX(), this.rectangle.getY(),
				this.rectangle.getWidth(), this.rectangle.getHeight(), this);
		Pane canvas = (Pane) this.rectangle.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
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
		properties.put("length", height);
	}

	public double getX() {
		return rectangle.getX();
	}

	public void setX(double x) {

		rectangle.setX(x);
		properties.put("x", x);

	}

	public double getY() {
		return rectangle.getY();
	}

	public void setY(double y) {
		rectangle.setY(y);
		properties.put("y", y);
	}

	public Node getParent() {
		return rectangle.getParent();
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		if (rectangle.getParent() == null) {
			rectangle.setX(position.getX());
			rectangle.setY(position.getY());
			properties.put("x", position.getX());
			properties.put("y", position.getY());
			return;
		}
		if (position.getX() >= 0
				&& position.getX() + rectangle.getWidth() <= rectangle.getParent().getBoundsInLocal().getWidth()) {
			rectangle.setX(position.getX());
			properties.put("x", position.getX());
		}
		if (position.getY() >= 0
				&& position.getY() + rectangle.getHeight() <= rectangle.getParent().getBoundsInLocal().getHeight()) {
			rectangle.setY(position.getY());
			properties.put("y", position.getY());
		}
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point((int) rectangle.getX(), (int) rectangle.getY());
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
		rectangle.setStroke((Paint) color);
	}

	@Override
	public Paint getColor() {
		// TODO Auto-generated method stub
		return rectangle.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		// TODO Auto-generated method stub
		rectangle.setFill((Paint) color);
	}

	@Override
	public Paint getFillColor() {
		// TODO Auto-generated method stub
		return rectangle.getFill();
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Pane root = (Pane) canvas;
		if (!root.getChildren().contains(rectangle))
			root.getChildren().add(rectangle);
	}

	public Object clone() throws CloneNotSupportedException {
		CustomRectangle clone = new CustomRectangle(this.getWidth(), this.getHeight());
		clone.resizableRectangle = null;
		clone.properties = properties;
		clone.setColor(this.getColor());
		clone.setFillColor(this.getFillColor());
		clone.setPosition(this.getPosition());
		if(this.rectangle.getOnMousePressed() != null)
			clone.rectangle.setOnMousePressed(clone.onMousePressed);clone.setStrokeWidth(this.getStrokeWidth());
		return clone;

	}

	@Override
	public void turnOnSelectListener() {
		// TODO Auto-generated method stub
		rectangle.setOnMousePressed(onMousePressed);

	}

	@Override
	public void turnOffSelectListener() {
		// TODO Auto-generated method stub
		if (resizableRectangle != null) {
			CustomRectangle.this.removeResizableRectangle();
		}
		rectangle.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
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
	public Integer getStrokeWidth() {
		// TODO Auto-generated method stub
		return (int) rectangle.getStrokeWidth();
	}

	@Override
	public void removeFromParent() {
		// TODO Auto-generated method stub
		Pane parent = (Pane) this.rectangle.getParent();
		parent.getChildren().remove(this.rectangle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getJSONString() {
		// TODO Auto-generated method stub
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("x", Double.toString((this.getX())));
		shapeObj.put("y", Double.toString((this.getY())));
		shapeObj.put("width", Double.toString((this.getWidth())));
		shapeObj.put("height", Double.toString((this.getHeight())));
		shapeObj.put("stroke", this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		shapeObj.put("strokeWidth", Double.toString((this.rectangle.getStrokeWidth())));
		return shapeObj.toJSONString();
	}

	@Override
	public org.w3c.dom.Node getXMLNode(Document doc) {
		// TODO Auto-generated method stub
		Element shape = doc.createElement("shape");
		// create name element
		Element x = doc.createElement("x");
		x.appendChild(doc.createTextNode(Double.toString(this.getX())));
		shape.appendChild(x);
		Element y = doc.createElement("y");
		y.appendChild(doc.createTextNode(Double.toString(this.getY())));
		shape.appendChild(y);
		Element width = doc.createElement("width");
		width.appendChild(doc.createTextNode(Double.toString(this.getWidth())));
		shape.appendChild(width);
		Element height = doc.createElement("height");
		height.appendChild(doc.createTextNode(Double.toString(this.getHeight())));
		shape.appendChild(height);
		Element fill = doc.createElement("fill");
		fill.appendChild(doc.createTextNode(this.getFillColor().toString()));
		shape.appendChild(fill);
		Element stroke = doc.createElement("stroke");
		stroke.appendChild(doc.createTextNode(this.getColor().toString()));
		shape.appendChild(stroke);
		Element strokeWidth = doc.createElement("strokeWidth");
		strokeWidth.appendChild(doc.createTextNode(Double.toString(this.getStrokeWidth())));
		shape.appendChild(strokeWidth);
		Element cl = doc.createElement("class");
		cl.appendChild(doc.createTextNode(this.getClass().toString()));
		shape.appendChild(cl);

		return shape;
	}

	@Override
	public void loadJSON(JSONObject shape) {
		// TODO Auto-generated method stub
		double x = Double.parseDouble((String) (shape.get("x")));
		double y = Double.parseDouble((String) (shape.get("y")));
		double width = Double.parseDouble((String) (shape.get("width")));
		double height = Double.parseDouble((String) (shape.get("height")));
		double strokeWidth = Double.parseDouble((String) (shape.get("strokeWidth")));
		Paint fill = Paint.valueOf((String) shape.get("fill"));
		Paint stroke = Paint.valueOf((String) shape.get("stroke"));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX(x);
		this.setY(y);
		this.setStrokeWidth((int) strokeWidth);
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public ResizableRectangle getResizableRectangle() {
		// TODO Auto-generated method stub
		return this.resizableRectangle;
	}

	@Override
	public void loadXML(Element element) {
		double x = Double.parseDouble(
				(String) (element.getElementsByTagName("x").item(0).getChildNodes().item(0).getNodeValue()));
		double y = Double.parseDouble(
				(String) (element.getElementsByTagName("y").item(0).getChildNodes().item(0).getNodeValue()));
		double height = Double.parseDouble(
				(String) (element.getElementsByTagName("height").item(0).getChildNodes().item(0).getNodeValue()));
		double width = Double.parseDouble(
				(String) (element.getElementsByTagName("width").item(0).getChildNodes().item(0).getNodeValue()));

		Paint fill = Paint.valueOf(
				(String) (element.getElementsByTagName("fill").item(0).getChildNodes().item(0).getNodeValue()));
		Paint stroke = Paint.valueOf(
				(String) (element.getElementsByTagName("stroke").item(0).getChildNodes().item(0).getNodeValue()));
		double strokeWidth = Double.parseDouble(
				(String) (element.getElementsByTagName("strokeWidth").item(0).getChildNodes().item(0).getNodeValue()));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX(x);
		this.setY(y);
		this.setStrokeWidth((int) strokeWidth);
		this.setWidth(width);
		this.setHeight(height);
	}

}
