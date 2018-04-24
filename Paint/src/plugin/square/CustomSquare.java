package plugin.square;

import java.awt.Point;


import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import paint.model.ResizableRectangle;
import paint.model.Shape;

public class CustomSquare implements Shape {
	private Rectangle square;
	private ResizableRectangle  resizableRectangle;
	private Class<CustomSquare> mClass = CustomSquare.class;
	public Class<CustomSquare> getmClass() {
		return mClass;
	}
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;
	public CustomSquare() {
		square = new Rectangle();
		onMousePressed =  new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(resizableRectangle != null) {
					CustomSquare.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomSquare.this.bindToResizableRectangle();
			}
		};
	}
	public CustomSquare(double side) {
		square = new Rectangle(side, side);
		properties.put("side", side);
		onMousePressed =  new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(resizableRectangle != null) {
					CustomSquare.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomSquare.this.bindToResizableRectangle();
			}
		};
	}
	protected void bindToResizableRectangle() {
		resizableRectangle =  new ResizableRectangle(this.square.getX(), this.square.getY(), this.square.getWidth()
				, this.square.getHeight(),this);
		Pane canvas = (Pane)this.square.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
		this.square.widthProperty().bind(resizableRectangle.getNode().widthProperty());
		this.square.heightProperty().bind(resizableRectangle.getNode().heightProperty());
		this.resizableRectangle.makeSquare();
		this.square.xProperty().bind(resizableRectangle.getNode().xProperty());
		this.square.yProperty().bind(resizableRectangle.getNode().yProperty());
		
	}
	public double getSide() {
		return square.getWidth();
	}
	public void setSide(double side) {
		square.setWidth(side);
		square.setHeight(side);
		properties.put("side", side);
	}
	public double getX() {
		return square.getX();
	}
	public void setX(double x) {
		
		square.setX(x);
		properties.put("x",x);
		
	}
	public double getY() {
		return square.getY();
	}
	public void setY(double y) {
		square.setY(y);
		properties.put("y",y);
	}
	public Node getParent() {
		return square.getParent();
	}
	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		if(square.getParent() == null) {
		    square.setX(position.getX());
			square.setY(position.getY());
			properties.put("x" , position.getX());
			properties.put("y", position.getY());
	        return ;    	
		}
		if (position.getX() >= 0 && position.getX() + square.getWidth() <= square.getParent().getBoundsInLocal().getWidth() ) {
            square.setX(position.getX());
            properties.put("x", position.getX());
        }
		if (position.getY() >= 0 && position.getY()+ square.getHeight() <= square .getParent().getBoundsInLocal().getHeight() ) {
			square.setY(position.getY());
			properties.put("y", position.getY());
        }
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point((int)square.getX(), (int)square.getY());
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
		square.setStroke((Paint)color);
	}

	@Override
	public Paint getColor() {
		// TODO Auto-generated method stub
		return square.getStroke();
	}

	@Override
	public void setFillColor(Object color) {
		// TODO Auto-generated method stub
		square.setFill((Paint)color);
	}

	@Override
	public Paint getFillColor() {
		// TODO Auto-generated method stub
		return square.getFill();
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Pane root = (Pane)canvas;
		if(!root.getChildren().contains(square))
		root.getChildren().add(square);
	}

	public Object clone() throws CloneNotSupportedException{
		CustomSquare clone =  new CustomSquare(this.getSide());
		clone.resizableRectangle = (ResizableRectangle)resizableRectangle.clone();
		clone.properties = properties;
		clone.setColor(this.getColor());
		clone.setFillColor(this.getFillColor());
		clone.setPosition(this.getPosition());
		clone.square.setOnMousePressed(clone.square.getOnMousePressed());
		clone.setStrokeWidth(this.getStrokeWidth());
		return clone;
		
	}
	@Override
	public void turnOnSelectListener() {
		// TODO Auto-generated method stub
		square.setOnMousePressed(onMousePressed);
			
	}
	@Override
	public void turnOffSelectListener() {
		// TODO Auto-generated method stub
		if(resizableRectangle!=null) {
			CustomSquare.this.removeResizableRectangle();	
			}
		square.removeEventHandler(MouseEvent.MOUSE_PRESSED,onMousePressed);
		square.setOnMousePressed(null);
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
		square.setStrokeWidth(value);
	}
	@Override
	public Integer getStrokeWidth() {
		// TODO Auto-generated method stub
		return (int) square.getStrokeWidth();
	}
	@Override
	public void removeFromParent() {
		// TODO Auto-generated method stub
		Pane parent = (Pane)this.square.getParent();
		parent.getChildren().remove(this.square);
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getJSONString() {
		// TODO Auto-generated method stub
		JSONObject shapeObj = new JSONObject();
		shapeObj.put("x",  Double.toString((this.getX())));
		shapeObj.put("y",  Double.toString((this.getY())));
		shapeObj.put("side",  Double.toString((this.getSide())));
		shapeObj.put("stroke",this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		shapeObj.put("strokeWidth", Double.toString((this.square.getStrokeWidth())));
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
		double side = Double.parseDouble((String)(shape.get("side")));
		double strokeWidth = Double.parseDouble((String)(shape.get("strokeWidth")));
		Paint fill = Paint.valueOf((String)shape.get("fill"));
		Paint stroke = Paint.valueOf((String)shape.get("stroke"));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX(x);
		this.setY(y);
		this.setStrokeWidth((int)strokeWidth);
		this.setSide(side);
	}
	@Override
	public ResizableRectangle getResizableRectangle() {
		// TODO Auto-generated method stub
		return this.resizableRectangle;
	}
	
	
	

}
