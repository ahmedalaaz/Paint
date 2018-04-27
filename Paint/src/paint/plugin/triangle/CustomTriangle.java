
package paint.plugin.triangle;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomTriangle implements Shape {

	private Polygon triangle;
	private ResizableRectangle resizableRectangle;
	private Class<CustomTriangle> mClass = CustomTriangle.class;

	public Class<CustomTriangle> getmClass() {
		return mClass;
	}

	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;

	public CustomTriangle() {
		triangle = new Polygon();
		onMousePressed = (MouseEvent event) -> {
			// TODO Auto-generated method stub
			if (resizableRectangle != null) {
				CustomTriangle.this.resizableRectangle = null;
			}
			// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
			CustomTriangle.this.bindToResizableRectangle();
		};
	}

	public CustomTriangle(double x1Top, double y1Top) {
		triangle = new Polygon(x1Top, y1Top);
		properties.put("x1Top", x1Top);
		properties.put("y1Top", y1Top);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomTriangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomTriangle.this.bindToResizableRectangle();
			}
		};
	}
	protected void bindToResizableRectangle() {
		if(this.getX2Right() < this.getX3Left()) {
			double xTop = this.getX1Top();
			double xRight = this.getX3Left();
			double xLeft = this.getX2Right();
			double yTop =  this.getY1Top();
			double yBase = this.getY2Right();
			this.RemoveAllPoints();
			this.setX1Top(xTop);
			this.setY1Top(yTop);
			this.setX2Right(xRight);
			this.setY2Right(yBase);
			this.setX3Left(xLeft);
			this.setY3Left(yBase);
		}
		if(this.getY1Top() < this.getY2Right()) {
		resizableRectangle = new ResizableRectangle(this.getX3Left(), this.getY1Top(),
				this.getX2Right() - this.getX3Left(), this.getY3Left() - this.getY1Top(), this);
		Pane canvas = (Pane) this.triangle.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
		this.x1TopProperty().bind(
				resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty().divide(2)));
		this.y1TopProperty().bind(resizableRectangle.getNode().yProperty());
		this.x2RightProperty().bind(resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
		this.y2RightProperty()
				.bind(resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
		this.x3LeftProperty()
				.bind(resizableRectangle.getNode().xProperty());
		this.y3LeftProperty()
				.bind(resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
		}else {
			resizableRectangle = new ResizableRectangle(this.getX3Left(), this.getY3Left(),
					this.getX2Right() - this.getX3Left(), -1*this.getY3Left() + this.getY1Top(), this);
			Pane canvas = (Pane) this.triangle.getParent();
			resizableRectangle.addToParent(canvas);
			// Binding properties :
			this.x1TopProperty().bind(
					resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty().divide(2)));
			this.y1TopProperty().bind(resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
			this.x2RightProperty().bind(resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
			this.y2RightProperty()
					.bind(resizableRectangle.getNode().yProperty());
			this.x3LeftProperty()
					.bind(resizableRectangle.getNode().xProperty());
			this.y3LeftProperty()
					.bind(resizableRectangle.getNode().yProperty());
				
			
			
		}
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
		triangle.setStroke((Paint) color);
	}

	@Override
	public Object getColor() {
		return triangle.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		triangle.setFill((Paint) color);
	}

	@Override
	public Object getFillColor() {
		return triangle.getFill();
	}

	@Override
	public void turnOnSelectListener() {
		triangle.setOnMousePressed(onMousePressed);
	}

	@Override
	public void turnOffSelectListener() {
		if (resizableRectangle != null) {
			CustomTriangle.this.removeResizableRectangle();
		}
		triangle.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		triangle.setOnMousePressed(null);
	}

	@Override
	public void removeResizableRectangle() {
		this.resizableRectangle.remove();
		resizableRectangle = null;
	}

	@Override
	public void draw(Object canvas) {
		Pane root = (Pane) canvas;
		if (!root.getChildren().contains(triangle))
			root.getChildren().add(triangle);
	}

	@Override
	public boolean isSelected() {
		return this.resizableRectangle != null;
	}

	@Override
	public ResizableRectangle getResizableRectangle() {
		return this.resizableRectangle;
	}

	@Override
	public void setStrokeWidth(Integer value) {
		triangle.setStrokeWidth(value);
	}

	@Override
	public Integer getStrokeWidth() {
		return (int) triangle.getStrokeWidth();
	}

	@Override
	public void removeFromParent() {
		Pane parent = (Pane) this.triangle.getParent();
		parent.getChildren().remove(this.triangle);
	}


	
	public Object clone() throws CloneNotSupportedException {
		CustomTriangle clone = new CustomTriangle();
		clone.resizableRectangle = null;
		clone.properties = properties;
		clone.setColor(this.getColor());
		clone.setFillColor(this.getFillColor());
		if(this.triangle.getOnMousePressed() != null)
			clone.triangle.setOnMousePressed(clone.onMousePressed);clone.setX1Top(this.getX1Top());
		clone.setY1Top(this.getY1Top());
		clone.setX2Right(this.getX2Right());
		clone.setY2Right(this.getY2Right());
		clone.setX3Left(this.getX3Left());
		clone.setY3Left(this.getY3Left());
		clone.setStrokeWidth(this.getStrokeWidth());
		return clone;	
	
	}

	public void RemoveAllPoints() {
		triangle.getPoints().removeAll(triangle.getPoints());
	}

	DoubleProperty x1TopProperty() {
		DoubleProperty x1Property = new SimpleDoubleProperty(triangle.getPoints().get(0));
		x1Property.addListener(new ChangeListener<Number>() {

			@Override

			public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

				triangle.getPoints().set(0, (double) x);

			}
		});
		return x1Property;
	}

	DoubleProperty y1TopProperty() {
		DoubleProperty y1Property = new SimpleDoubleProperty(triangle.getPoints().get(1));

		y1Property.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {

				triangle.getPoints().set(1, (double) y);
			}
		});
		return y1Property;
	}

	DoubleProperty x2RightProperty() {
		DoubleProperty x2Property = new SimpleDoubleProperty(triangle.getPoints().get(2));
		x2Property.addListener(new ChangeListener<Number>() {

			@Override

			public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

				triangle.getPoints().set(2, (double) x);
			}
		});

		return x2Property;
	}

	DoubleProperty y2RightProperty() {
		DoubleProperty y2Property = new SimpleDoubleProperty(triangle.getPoints().get(3));

		y2Property.addListener(new ChangeListener<Number>() {

			@Override

			public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {

				triangle.getPoints().set(3, (double) y);

			}
		});
		return y2Property;
	}

	DoubleProperty x3LeftProperty() {
		DoubleProperty x3Property = new SimpleDoubleProperty(triangle.getPoints().get(4));
		x3Property.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

				triangle.getPoints().set(4, (double) x);

			}
		});
		return x3Property;
	}

	DoubleProperty y3LeftProperty() {
		DoubleProperty y3Property = new SimpleDoubleProperty(triangle.getPoints().get(5));
		y3Property.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {

				triangle.getPoints().set(5, (double) y);
			}
		});
		return y3Property;
	}

	public Node getParent() {
		return triangle.getParent();
	}

	public void setX1Top(double x1) {

		if (this.triangle.getParent() != null) {
			if (x1 < 0)
				x1 = 5;
			if (x1 >= this.triangle.getParent().getBoundsInLocal().getWidth())
				x1 = triangle.getParent().getBoundsInLocal().getWidth() - 5;

		}
		triangle.getPoints().add(0, x1);
		properties.put("X1Top", x1);
	}

	public double getX1Top() {
		return triangle.getPoints().get(0);
	}

	public void setY1Top(double y1) {
		if (this.triangle.getParent() != null) {
			if (y1 < 0)
				y1 = 5;
			if (y1 >= this.triangle.getParent().getBoundsInLocal().getHeight())
				y1 = triangle.getParent().getBoundsInLocal().getHeight() - 5;

		}
		triangle.getPoints().add(1, y1);
		properties.put("Y1Top", y1);
	}

	public double getY1Top() {
		return triangle.getPoints().get(1);
	}

	public void setX2Right(double x2) {
		if (this.triangle.getParent() != null) {
			if (x2 < 0)
				x2 = 5;
			if (x2 >= this.triangle.getParent().getBoundsInLocal().getWidth())
				x2 = triangle.getParent().getBoundsInLocal().getWidth() - 5;

		}
		triangle.getPoints().add(2, x2);
		properties.put("X2Top", x2);
	}

	public double getX2Right() {
		return triangle.getPoints().get(2);
	}

	public void setY2Right(double y2) {
		if (this.triangle.getParent() != null) {
			if (y2 < 0)
				y2 = 5;
			if (y2 >= this.triangle.getParent().getBoundsInLocal().getHeight())
				y2 = triangle.getParent().getBoundsInLocal().getHeight() - 5;

		}

		triangle.getPoints().add(3, y2);
		properties.put("Y2Top", y2);
	}

	public double getY2Right() {
		return triangle.getPoints().get(3);
	}

	public void setX3Left(double x3) {
		if (this.triangle.getParent() != null) {
			if (x3 < 0)
				x3 = 5;
			if (x3 >= this.triangle.getParent().getBoundsInLocal().getWidth())
				x3 = triangle.getParent().getBoundsInLocal().getWidth() - 5;

		}
		triangle.getPoints().add(4, x3);
		properties.put("X3Top", x3);
	}

	public double getX3Left() {
		return triangle.getPoints().get(4);
	}

	public void setY3Left(double y3) {
		if (this.triangle.getParent() != null) {
			if (y3 < 0)
				y3 = 5;
			if (y3 >= this.triangle.getParent().getBoundsInLocal().getHeight())
				y3 = triangle.getParent().getBoundsInLocal().getHeight() - 5;

		}
		triangle.getPoints().add(5, y3);
		properties.put("Y3Top", y3);
	}

	public double getY3Left() {
		return triangle.getPoints().get(5);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getJSONString() {
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("x1Top", Double.toString((this.getX1Top())));
		shapeObj.put("y1Top", Double.toString((this.getY1Top())));
		shapeObj.put("x2Right", Double.toString((this.getX2Right())));
		shapeObj.put("y2Right", Double.toString((this.getY2Right())));
		shapeObj.put("x3Left", Double.toString((this.getX3Left())));
		shapeObj.put("y3Left", Double.toString((this.getY3Left())));
		shapeObj.put("stroke", this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		shapeObj.put("strokeWidth", Double.toString((this.triangle.getStrokeWidth())));
		return shapeObj.toJSONString();
	}

	@Override
	public void loadJSON(JSONObject jsonObj) {
		double x1Top = Double.parseDouble((String) (jsonObj.get("x1Top")));
		double y1Top = Double.parseDouble((String) (jsonObj.get("y1Top")));
		double x2Right = Double.parseDouble((String) (jsonObj.get("x2Right")));
		double y2Right = Double.parseDouble((String) (jsonObj.get("y2Right")));
		double x3Left = Double.parseDouble((String) (jsonObj.get("x3Left")));
		double y3Left = Double.parseDouble((String) (jsonObj.get("y3Left")));
		double strokeWidth = Double.parseDouble((String) (jsonObj.get("strokeWidth")));
		Paint fill = Paint.valueOf((String) jsonObj.get("fill"));
		Paint stroke = Paint.valueOf((String) jsonObj.get("stroke"));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX1Top(x1Top);
		this.setY1Top(y1Top);
		this.setX2Right(x2Right);
		this.setY2Right(y2Right);
		this.setX3Left(x3Left);
		this.setY3Left(y3Left);
		this.setStrokeWidth((int) strokeWidth);

	}

	@Override
	public void loadXML(Element element) {
		double x1Top = Double.parseDouble(
				(String) (element.getElementsByTagName("x1Top").item(0).getChildNodes().item(0).getNodeValue()));
		double y1Top = Double.parseDouble(
				(String) (element.getElementsByTagName("y1Top").item(0).getChildNodes().item(0).getNodeValue()));
		double x2Right = Double.parseDouble(
				(String) (element.getElementsByTagName("x2Right").item(0).getChildNodes().item(0).getNodeValue()));
		double y2Right = Double.parseDouble(
				(String) (element.getElementsByTagName("y2Right").item(0).getChildNodes().item(0).getNodeValue()));
		double x3Left = Double.parseDouble(
				(String) (element.getElementsByTagName("x3Left").item(0).getChildNodes().item(0).getNodeValue()));
		double y3Left = Double.parseDouble(
				(String) (element.getElementsByTagName("y3Left").item(0).getChildNodes().item(0).getNodeValue()));
		Paint fill = Paint.valueOf(
				(String) (element.getElementsByTagName("fill").item(0).getChildNodes().item(0).getNodeValue()));
		Paint stroke = Paint.valueOf(
				(String) (element.getElementsByTagName("stroke").item(0).getChildNodes().item(0).getNodeValue()));
		double strokeWidth = Double.parseDouble(
				(String) (element.getElementsByTagName("strokeWidth").item(0).getChildNodes().item(0).getNodeValue()));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX1Top(x1Top);
		this.setY1Top(y1Top);
		this.setX2Right(x2Right);
		this.setY2Right(y2Right);
		this.setX3Left(x3Left);
		this.setY3Left(y3Left);
		this.setStrokeWidth((int) strokeWidth);

	}
	@Override
	public org.w3c.dom.Node getXMLNode(Document doc) {
		// TODO Auto-generated method stub
		Element shape = doc.createElement("shape");
		// create name element
		Element x1Top = doc.createElement("x1Top");
		x1Top.appendChild(doc.createTextNode(Double.toString(this.getX1Top())));
		shape.appendChild(x1Top);
		Element y1Top = doc.createElement("y1Top");
		y1Top.appendChild(doc.createTextNode(Double.toString(this.getY1Top())));
		shape.appendChild(y1Top);
		Element x2Right = doc.createElement("x2Right");
		x2Right.appendChild(doc.createTextNode(Double.toString(this.getX2Right())));
		shape.appendChild(x2Right);
		Element y2Right = doc.createElement("y2Right");
		y2Right.appendChild(doc.createTextNode(Double.toString(this.getY2Right())));
		shape.appendChild(y2Right);
		Element x3Left = doc.createElement("x3Left");
		x3Left.appendChild(doc.createTextNode(Double.toString(this.getX3Left())));
		shape.appendChild(x3Left);
		Element y3Left = doc.createElement("y3Left");
		y3Left.appendChild(doc.createTextNode(Double.toString(this.getY3Left())));
		shape.appendChild(y3Left);
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


}
