
package plugin.roundrectangle;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import paint.model.ResizableRectangle;
import paint.model.Shape;
import plugin.rectangle.CustomRectangle;


public class CustomRoundRectangle implements Shape{

    private Rectangle roundrectangle;
	private ResizableRectangle resizableRectangle;
	private Class<CustomRectangle> mClass = CustomRectangle.class;

	public Class<CustomRectangle> getmClass() {
		return mClass;
	}

	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;

	public CustomRoundRectangle() {
		roundrectangle = new Rectangle();
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomRoundRectangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomRoundRectangle.this.bindToResizableRoundRectangle();
			}
		};
	}

	public CustomRoundRectangle(double width, double height) {
		roundrectangle = new Rectangle(width, height);
		properties.put("width", width);
		properties.put("length", height);
		onMousePressed = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (resizableRectangle != null) {
					CustomRoundRectangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomRoundRectangle.this.bindToResizableRoundRectangle();
			}
		};
	}
    protected void bindToResizableRoundRectangle() {
		resizableRectangle = new ResizableRectangle(this.roundrectangle.getX(), this.roundrectangle.getY(),
				this.roundrectangle.getWidth(), this.roundrectangle.getHeight(), this);
		Pane canvas = (Pane) this.roundrectangle.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
		this.roundrectangle.widthProperty().bind(resizableRectangle.getNode().widthProperty());
		this.roundrectangle.heightProperty().bind(resizableRectangle.getNode().heightProperty());
		this.roundrectangle.xProperty().bind(resizableRectangle.getNode().xProperty());
		this.roundrectangle.yProperty().bind(resizableRectangle.getNode().yProperty());

	}
    
    public double getWidth() {
		return roundrectangle.getWidth();
	}

	public double getHeight() {
		return roundrectangle.getHeight();
	}

	public void setWidth(double width) {
		roundrectangle.setWidth(width);
		properties.put("width", width);
	}

	public void setHeight(double height) {
		roundrectangle.setHeight(height);
		properties.put("length", height);
	}

	public double getX() {
		return roundrectangle.getX();
	}

	public void setX(double x) {

		roundrectangle.setX(x);
		properties.put("x", x);

	}

	public double getY() {
		return roundrectangle.getY();
	}

	public void setY(double y) {
		roundrectangle.setY(y);
		properties.put("y", y);
	}

	public javafx.scene.Node getParent() {
		return roundrectangle.getParent();
	}
        public void setArcWidth(double arcWidth){
            roundrectangle.setArcWidth(arcWidth);
            properties.put("arcWidth", arcWidth);
        }
        public double getArcWidth(){
            return roundrectangle.getArcWidth();
        }
        public void setArcHeight(double arcHeight){
            roundrectangle.setArcHeight(arcHeight);
            properties.put("arcHeight", arcHeight);
        }
        public double getArcHeight(){
            return roundrectangle.getArcHeight();
        }
    @Override
    public void setPosition(Point position) {
     
        if (roundrectangle.getParent() == null) {
			roundrectangle.setX(position.getX());
			roundrectangle.setY(position.getY());
			properties.put("x", position.getX());
			properties.put("y", position.getY());
			return;
		}
		if (position.getX() >= 0
				&& position.getX() + roundrectangle.getWidth() <= roundrectangle.getParent().getBoundsInLocal().getWidth()) {
			roundrectangle.setX(position.getX());
			properties.put("x", position.getX());
		}
		if (position.getY() >= 0
				&& position.getY() + roundrectangle.getHeight() <= roundrectangle.getParent().getBoundsInLocal().getHeight()) {
			roundrectangle.setY(position.getY());
			properties.put("y", position.getY());
		}
    }

    @Override
    public Point getPosition() {
      return new Point((int) roundrectangle.getX(), (int) roundrectangle.getY());
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
       roundrectangle.setStroke((Paint) color);
    }

    @Override
    public Object getColor() {
     return roundrectangle.getStroke();
    }

    @Override
    public void setFillColor(Object color) {
       roundrectangle.setFill((Paint) color);
    }

    @Override
    public Object getFillColor() {
     return roundrectangle.getFill();
    }

    @Override
    public void turnOnSelectListener() {
      roundrectangle.setOnMousePressed(onMousePressed);
    }

    @Override
    public void turnOffSelectListener() {
      if (resizableRectangle != null) {
			CustomRoundRectangle.this.removeResizableRectangle();
		}
		roundrectangle.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		roundrectangle.setOnMousePressed(null);
    }

    @Override
    public void removeResizableRectangle() {
     this.resizableRectangle.remove();
		resizableRectangle = null;
    }

    @Override
    public void draw(Object canvas) {
     Pane root = (Pane) canvas;
		if (!root.getChildren().contains(roundrectangle))
			root.getChildren().add(roundrectangle);
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
     roundrectangle.setStrokeWidth(value);
    }

    @Override
    public Integer getStrokeWidth() {
     return (int) roundrectangle.getStrokeWidth();
    }

    @Override
    public void removeFromParent() {
    Pane parent = (Pane) this.roundrectangle.getParent();
		parent.getChildren().remove(this.roundrectangle);
    }

    @Override
    public Node getXMLNode(Document document) {
        Element shape = document.createElement("shape");
		// create name element
		Element x = document.createElement("x");
		x.appendChild(document.createTextNode(Double.toString(this.getX())));
		shape.appendChild(x);
		Element y = document.createElement("y");
		y.appendChild(document.createTextNode(Double.toString(this.getY())));
		shape.appendChild(y);
		Element width = document.createElement("width");
		width.appendChild(document.createTextNode(Double.toString(this.getWidth())));
		shape.appendChild(width);
		Element height = document.createElement("height");
		height.appendChild(document.createTextNode(Double.toString(this.getHeight())));
		shape.appendChild(height);
                Element archeight = document.createElement("archeight");
		archeight.appendChild(document.createTextNode(Double.toString(this.getArcHeight())));
		shape.appendChild(archeight);
                Element arcwidth = document.createElement("arcwidth");
		arcwidth.appendChild(document.createTextNode(Double.toString(this.getArcWidth())));
		shape.appendChild(arcwidth);
                Element fill = document.createElement("fill");
		fill.appendChild(document.createTextNode(this.getFillColor().toString()));
		shape.appendChild(fill);
		Element stroke = document.createElement("stroke");
		stroke.appendChild(document.createTextNode(this.getColor().toString()));
		shape.appendChild(stroke);
		Element strokeWidth = document.createElement("strokeWidth");
		strokeWidth.appendChild(document.createTextNode(Double.toString(this.getStrokeWidth())));
		shape.appendChild(strokeWidth);
		Element cl = document.createElement("class");
		cl.appendChild(document.createTextNode(this.getClass().toString()));
		shape.appendChild(cl);

		return shape;
    }

    @Override
    public String getJSONString() {
      JSONObject shapeObj = new JSONObject();
		shapeObj.put("x", Double.toString((this.getX())));
		shapeObj.put("y", Double.toString((this.getY())));
		shapeObj.put("width", Double.toString((this.getWidth())));
		shapeObj.put("height", Double.toString((this.getHeight())));
                shapeObj.put("archeight", Double.toString((this.getArcHeight())));
                shapeObj.put("arcwidth", Double.toString((this.getArcWidth())));
		shapeObj.put("stroke", this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		shapeObj.put("strokeWidth", Double.toString((this.roundrectangle.getStrokeWidth())));
		return shapeObj.toJSONString();
    }

    @Override
    public void loadJSON(JSONObject jsonObj) {
        double x = Double.parseDouble((String) (jsonObj.get("x")));
		double y = Double.parseDouble((String) (jsonObj.get("y")));
		double width = Double.parseDouble((String) (jsonObj.get("width")));
		double height = Double.parseDouble((String) (jsonObj.get("height")));
                double arcwidth = Double.parseDouble((String) (jsonObj.get("arcwidth")));
		double archeight = Double.parseDouble((String) (jsonObj.get("archeight")));
		double strokeWidth = Double.parseDouble((String) (jsonObj.get("strokeWidth")));
		Paint fill = Paint.valueOf((String) jsonObj.get("fill"));
		Paint stroke = Paint.valueOf((String) jsonObj.get("stroke"));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX(x);
		this.setY(y);
		this.setStrokeWidth((int) strokeWidth);
		this.setWidth(width);
		this.setHeight(height);
                this.setArcWidth(arcwidth);
		this.setArcHeight(archeight);
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
                double archeight = Double.parseDouble(
				(String) (element.getElementsByTagName("archeight").item(0).getChildNodes().item(0).getNodeValue()));
		double arcwidth = Double.parseDouble(
				(String) (element.getElementsByTagName("arcwidth").item(0).getChildNodes().item(0).getNodeValue()));
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
                this.setArcWidth(arcwidth);
		this.setArcHeight(archeight);
    }
    
    public Object clone() throws CloneNotSupportedException {
        CustomRoundRectangle clone = new CustomRoundRectangle(this.getWidth(), this.getHeight());
		clone.resizableRectangle = null;
		clone.properties = properties;
		clone.setColor(this.getColor());
                clone.setArcHeight(this.getArcHeight());
                clone.setArcWidth(this.getArcWidth());
		clone.setFillColor(this.getFillColor());
		clone.setPosition(this.getPosition());
		if(this.roundrectangle.getOnMousePressed() != null)
			clone.roundrectangle.setOnMousePressed(clone.onMousePressed);clone.setStrokeWidth(this.getStrokeWidth());
		return clone;
    }
    
}
