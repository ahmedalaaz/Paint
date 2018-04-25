
package plugin.line;

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
import javafx.scene.shape.Line;
import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomLine implements Shape {

	private Line line;
	private Class<CustomLine> mClass = CustomLine.class;

	public Class<CustomLine> getmClass() {
		return mClass;
	}

	private ResizableRectangle resizableRectangle;
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;

	public CustomLine() {
		line = new Line();
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomLine.this.resizableRectangle = null;
				}
				CustomLine.this.bindToResizableEllipse();
			}
		};
	}

	public CustomLine(double startX, double startY, double endX, double endY) {
		line = new Line(startX, startY, endX, endY);
		properties.put("startX", startX);
		properties.put("startY", startY);
		properties.put("endX", endX);
		properties.put("endY", endY);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomLine.this.resizableRectangle = null;
				}
				CustomLine.this.bindToResizableEllipse();
			}
		};
	}

	protected void bindToResizableEllipse() {
		double slope = (line.getEndY() - line.getStartY()) / (line.getEndX() - line.getStartX());
		if (line.getStartX() > line.getEndX()) {
			double tempx = line.getStartX();
			double tempy = line.getStartY();
			line.setStartX(line.getEndX());
			line.setStartY(line.getEndY());
			line.setEndX(tempx);
			line.setEndY(tempy);
		}
		double angle = Math.atan(slope);
		double diffX = line.getEndX() - line.getStartX();
		double diffY = line.getEndY() - line.getStartY();
		double lineLength = Math.sqrt((diffX) * (diffX) + (diffY) * (diffY));
		if (slope < 0) {
			if (Math.abs(lineLength * Math.cos(angle)) <= 30) {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getEndY(), 40, lineLength, this);
				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty()
						.add(resizableRectangle.getNode().widthProperty().divide(2)));
				line.startYProperty().bind(
						resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
				line.endXProperty().bind(resizableRectangle.getNode().xProperty()
						.add(resizableRectangle.getNode().widthProperty().divide(2)));
				line.endYProperty().bind(resizableRectangle.getNode().yProperty());
			} else if (Math.abs(lineLength * Math.sin(angle)) <= 30) {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getEndY(), lineLength, 40, this);

				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty());
				line.startYProperty().bind(resizableRectangle.getNode().yProperty()
						.add(resizableRectangle.getNode().heightProperty().divide(2)));
				line.endXProperty().bind(
						resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
				line.endYProperty().bind(resizableRectangle.getNode().yProperty()
						.add(resizableRectangle.getNode().heightProperty().divide(2)));
			} else {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getEndY(),
						Math.abs(lineLength * Math.cos(angle)), Math.abs(lineLength * Math.sin(angle)), this);

				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty());
				line.startYProperty().bind(
						resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
				line.endXProperty().bind(
						resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
				line.endYProperty().bind(resizableRectangle.getNode().yProperty());
			}
		} else {
			if (Math.abs(lineLength * Math.cos(angle)) <= 30) {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getStartY(), 40, lineLength, this);
				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty()
						.add(resizableRectangle.getNode().widthProperty().divide(2)));
				line.startYProperty().bind(resizableRectangle.getNode().yProperty());
				line.endXProperty().bind(resizableRectangle.getNode().xProperty()
						.add(resizableRectangle.getNode().widthProperty().divide(2)));
				line.endYProperty().bind(
						resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
			} else if (Math.abs(lineLength * Math.sin(angle)) <= 30) {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getStartY(), lineLength, 40, this);

				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty());
				line.startYProperty().bind(resizableRectangle.getNode().yProperty()
						.add(resizableRectangle.getNode().heightProperty().divide(2)));
				line.endXProperty().bind(
						resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
				line.endYProperty().bind(resizableRectangle.getNode().yProperty()
						.add(resizableRectangle.getNode().heightProperty().divide(2)));
			} else {
				resizableRectangle = new ResizableRectangle(line.getStartX(), line.getStartY(),
						Math.abs(lineLength * Math.cos(angle)), Math.abs(lineLength * Math.sin(angle)), this);

				Pane canvas = (Pane) this.line.getParent();
				resizableRectangle.addToParent(canvas);
				// Binding properties :
				line.startXProperty().bind(resizableRectangle.getNode().xProperty());
				line.startYProperty().bind(resizableRectangle.getNode().yProperty());
				line.endXProperty().bind(
						resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
				line.endYProperty().bind(
						resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
			}

		}
	}

	public double getStartX() {
		return line.getStartX();
	}

	public double getStartY() {
		return line.getStartY();
	}

	public double getEndX() {
		return line.getEndX();
	}

	public double getEndY() {
		return line.getEndY();
	}

	public void setStartX(double startX) {
		line.setStartX(startX);
		properties.put("startX", startX);
	}

	public void setStartY(double startY) {
		line.setStartY(startY);
		properties.put("startY", startY);
	}

	public void setEndX(double endX) {
		line.setEndX(endX);
		properties.put("endX", endX);
	}

	public void setEndY(double endY) {
		line.setEndY(endY);
		properties.put("endY", endY);
	}

	public Node getParent() {
		return line.getParent();
	}

	@Override
	public void setPosition(Point position) {

	}

	@Override
	public Point getPosition() {
		return null;
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
		line.setStroke((Paint) color);
	}

	@Override
	public Object getColor() {
		return line.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		line.setFill((Paint) color);

	}

	@Override
	public Object getFillColor() {
		return line.getFill();
	}

	@Override
	public void turnOnSelectListener() {
		line.setOnMousePressed(onMousePressed);

	}

	@Override
	public void turnOffSelectListener() {
		if (resizableRectangle != null) {
			CustomLine.this.removeResizableRectangle();
		}
		line.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		line.setOnMousePressed(null);
	}

	@Override
	public void removeResizableRectangle() {
		this.resizableRectangle.remove();
		resizableRectangle = null;
	}

	@Override
	public void draw(Object canvas) {
		Pane root = (Pane) canvas;
		if (!root.getChildren().contains(line))
			root.getChildren().add(line);
	}

	@Override
	public boolean isSelected() {
		return this.resizableRectangle != null;
	}

	@Override
	public void setStrokeWidth(Integer value) {
		line.setStrokeWidth(value);
	}

	@Override
	public Integer getStrokeWidth() {
		return (int) line.getStrokeWidth();
	}

	@Override
	public void removeFromParent() {
		Pane parent = (Pane) this.line.getParent();
		parent.getChildren().remove(this.line);
	}

	public Object clone() throws CloneNotSupportedException {
		CustomLine clone = new CustomLine(this.getStartX(), this.getStartY(), this.getEndX(), this.getEndY());
		clone.resizableRectangle = null;
		clone.properties = properties;
		clone.setColor(this.getColor());
		clone.setFillColor(this.getFillColor());
		clone.setPosition(this.getPosition());
		if (this.line.getOnMousePressed() != null)
			clone.line.setOnMousePressed(clone.onMousePressed);
		clone.setStrokeWidth(this.getStrokeWidth());
		return clone;

	}

	@SuppressWarnings("unchecked")
	public String getJSONString() {
		// TODO Auto-generated method stub
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("startX", Double.toString((this.getStartX())));
		shapeObj.put("startY", Double.toString((this.getStartY())));
		shapeObj.put("endX", Double.toString((this.getEndX())));
		shapeObj.put("endY", Double.toString((this.getEndY())));
		shapeObj.put("strokeWidth", Double.toString((this.line.getStrokeWidth())));
		shapeObj.put("stroke", this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		return shapeObj.toJSONString();
	}

	@Override
	public org.w3c.dom.Node getXMLNode(Document doc) {
		// TODO Auto-generated method stub
		Element shape = doc.createElement("shape");
		// create name element
		Element startX = doc.createElement("startX");
		startX.appendChild(doc.createTextNode(Double.toString(this.getStartX())));
		shape.appendChild(startX);
		Element startY = doc.createElement("startY");
		startY.appendChild(doc.createTextNode(Double.toString(this.getStartY())));
		shape.appendChild(startY);
		Element endX = doc.createElement("endX");
		endX.appendChild(doc.createTextNode(Double.toString(this.getEndX())));
		shape.appendChild(endX);
		Element endY = doc.createElement("endY");
		endY.appendChild(doc.createTextNode(Double.toString(this.getEndY())));
		shape.appendChild(endY);
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
		double startX = Double.parseDouble((String) (shape.get("startX")));
		double startY = Double.parseDouble((String) (shape.get("startY")));
		double endX = Double.parseDouble((String) (shape.get("endX")));
		double endY = Double.parseDouble((String) (shape.get("endY")));
		Paint fill = Paint.valueOf((String) shape.get("fill"));
		Paint stroke = Paint.valueOf((String) shape.get("stroke"));
		double strokeWidth = Double.parseDouble((String) (shape.get("strokeWidth")));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setStrokeWidth((int) strokeWidth);
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);
	}

	@Override
	public ResizableRectangle getResizableRectangle() {
		// TODO Auto-generated method stub
		return this.resizableRectangle;
	}

	@Override
	public void loadXML(Element element) {
		// TODO Auto-generated method stub
		double startX = Double.parseDouble(
				(String) (element.getElementsByTagName("startX").item(0).getChildNodes().item(0).getNodeValue()));
		double startY = Double.parseDouble(
				(String) (element.getElementsByTagName("startY").item(0).getChildNodes().item(0).getNodeValue()));
		double endX = Double.parseDouble(
				(String) (element.getElementsByTagName("endX").item(0).getChildNodes().item(0).getNodeValue()));
		double endY = Double.parseDouble(
				(String) (element.getElementsByTagName("endY").item(0).getChildNodes().item(0).getNodeValue()));

		Paint fill = Paint.valueOf(
				(String) (element.getElementsByTagName("fill").item(0).getChildNodes().item(0).getNodeValue()));
		Paint stroke = Paint.valueOf(
				(String) (element.getElementsByTagName("stroke").item(0).getChildNodes().item(0).getNodeValue()));
		double strokeWidth = Double.parseDouble(
				(String) (element.getElementsByTagName("strokeWidth").item(0).getChildNodes().item(0).getNodeValue()));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setStrokeWidth((int) strokeWidth);
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);

	}
}
