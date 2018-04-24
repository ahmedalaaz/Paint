
package plugin.triangle;

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
import paint.model.ResizableRectangle;
import paint.model.Shape;



public class CustomTriangle implements Shape {

        private Polygon triangle;
	private ResizableRectangle  resizableRectangle;
	private Class<CustomTriangle> mClass = CustomTriangle.class;
	public Class<CustomTriangle> getmClass() {
		return mClass;
	}
	private Map<String, Double> properties = new HashMap<>();
	EventHandler<MouseEvent> onMousePressed;
	public CustomTriangle() {
		triangle = new Polygon();
		onMousePressed =  (MouseEvent event) -> {
                    // TODO Auto-generated method stub
                    if(resizableRectangle != null) {
                        CustomTriangle.this.resizableRectangle = null;
                    }
                    // rectangle.getStrokeDashArray().addAll(42d,6d,4d);
                    CustomTriangle.this.bindToResizableRectangle();
                };
	}
        
        public CustomTriangle(double x1Top, double y1Top) {
		triangle = new Polygon(x1Top,y1Top);
		properties.put("x1Top",x1Top);
		properties.put("y1Top",y1Top);
		onMousePressed =  new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(resizableRectangle != null) {
					CustomTriangle.this.resizableRectangle = null;
				}
				// rectangle.getStrokeDashArray().addAll(42d,6d,4d);
				CustomTriangle.this.bindToResizableRectangle();
			}
		};
	}
        
        protected void bindToResizableRectangle() {
		resizableRectangle =  new ResizableRectangle(this.getX2Right(), this.getY1Top(),this.getX3Left()-this.getX2Right()
				, this.getY3Left()-this.getY1Top(),this);
		Pane canvas = (Pane)this.triangle.getParent();
		resizableRectangle.addToParent(canvas);
		// Binding properties :
	        this.x1TopProperty().bind(resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty().divide(2)));
                this.y1TopProperty().bind(resizableRectangle.getNode().yProperty());
                this.x2RightProperty().bind(resizableRectangle.getNode().xProperty());
                this.y2RightProperty().bind(resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
                this.x3LeftProperty().bind(resizableRectangle.getNode().xProperty().add(resizableRectangle.getNode().widthProperty()));
                this.y3LeftProperty().bind(resizableRectangle.getNode().yProperty().add(resizableRectangle.getNode().heightProperty()));
		
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
        triangle.setStroke((Paint)color);
    }

    @Override
    public Object getColor() {
        return triangle.getStroke();
    }

    @Override
    public void setFillColor(Object color) {
        triangle.setFill((Paint)color);
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
        if(resizableRectangle!=null) {
	CustomTriangle.this.removeResizableRectangle();		
        }
        triangle.removeEventHandler(MouseEvent.MOUSE_PRESSED,onMousePressed);
	triangle.setOnMousePressed(null);
    }

    @Override
    public void removeResizableRectangle() {
        this.resizableRectangle.remove();
		resizableRectangle = null;
    }

    @Override
    public void draw(Object canvas) {
        Pane root = (Pane)canvas;
        if(!root.getChildren().contains(triangle))
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
        Pane parent = (Pane)this.triangle.getParent();
	parent.getChildren().remove(this.triangle);
    }

    @Override
    public String getXMLString() {
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public String getJSONString() {
                JSONObject shapeObj = new JSONObject();
		shapeObj.put("x1Top",  Double.toString((this.getX1Top())));
		shapeObj.put("y1Top",  Double.toString((this.getY1Top())));
                shapeObj.put("x2Right",  Double.toString((this.getX2Right())));
                shapeObj.put("y2Right",  Double.toString((this.getY2Right())));
                shapeObj.put("x3Left",  Double.toString((this.getX3Left())));
                shapeObj.put("y3Left",  Double.toString((this.getY3Left())));
		shapeObj.put("stroke",this.getColor());
		shapeObj.put("fill", this.getFillColor());
		shapeObj.put("class", this.getClass());
		shapeObj.put("strokeWidth", Double.toString((this.triangle.getStrokeWidth())));
		return shapeObj.toJSONString();
    }

    @Override
    public void loadJSON(JSONObject jsonObj) {
                double x1Top =  Double.parseDouble((String)(jsonObj.get("x1Top")));
		double y1Top = Double.parseDouble((String)(jsonObj.get("y1Top")));
		double x2Right = Double.parseDouble((String)(jsonObj.get("x2Right")));
		double y2Right= Double.parseDouble((String)(jsonObj.get("y2Right")));
                double x3Left = Double.parseDouble((String)(jsonObj.get("x3Left")));
		double y3Left= Double.parseDouble((String)(jsonObj.get("y3Left")));
		double strokeWidth = Double.parseDouble((String)(jsonObj.get("strokeWidth")));
		Paint fill = Paint.valueOf((String)jsonObj.get("fill"));
		Paint stroke = Paint.valueOf((String)jsonObj.get("stroke"));
		this.setColor(stroke);
		this.setFillColor(fill);
		this.setX1Top(x1Top);
                this.setY1Top(y1Top);
                this.setX2Right(x2Right);
                this.setY2Right(y2Right);
                this.setX3Left(x3Left);
                this.setY3Left(y3Left);
		this.setStrokeWidth((int)strokeWidth);
		
}
    public Object clone() throws CloneNotSupportedException{
		return null;
	}
    public void RemoveAllPoints(){
        triangle.getPoints().removeAll(triangle.getPoints());
    }
    DoubleProperty x1TopProperty (){
        DoubleProperty x1Property = new SimpleDoubleProperty(triangle.getPoints().get(0));
        x1Property.addListener(new ChangeListener<Number>() {

                @Override

                public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

                    triangle.getPoints().set(0, (double) x);
                
                }
            });
        return x1Property;
    }
    DoubleProperty y1TopProperty (){
        DoubleProperty y1Property = new SimpleDoubleProperty(triangle.getPoints().get(1));
               
                y1Property.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {

                    triangle.getPoints().set(1, (double) y);
                }
            });
            return y1Property;
    }
    DoubleProperty x2RightProperty (){
        DoubleProperty x2Property = new SimpleDoubleProperty(triangle.getPoints().get(2));
        x2Property.addListener(new ChangeListener<Number>() {

                @Override

                public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

                    triangle.getPoints().set(2, (double) x);
                }
            });

        return x2Property;
    }
    DoubleProperty y2RightProperty (){
        DoubleProperty y2Property = new SimpleDoubleProperty(triangle.getPoints().get(3));

            y2Property.addListener(new ChangeListener<Number>() {

                @Override

                public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {

                    triangle.getPoints().set(3, (double) y);

                }
            });
            return y2Property;
    }
    DoubleProperty x3LeftProperty (){
        DoubleProperty x3Property = new SimpleDoubleProperty(triangle.getPoints().get(4));
        x3Property.addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {

                    triangle.getPoints().set(4, (double) x);

                }
           });
        return x3Property;
    }
    DoubleProperty y3LeftProperty (){
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
   public void setX1Top(double x1){
       triangle.getPoints().add(0, x1);
       properties.put("X1Top",x1);
   }
   public double getX1Top (){
       return triangle.getPoints().get(0);
   }
   public void setY1Top(double y1){
       triangle.getPoints().add(1, y1);
       properties.put("Y1Top",y1);
   }
   public double getY1Top (){
       return triangle.getPoints().get(1);
   }
   public void setX2Right(double x2){
       
       triangle.getPoints().add(2, x2);
       properties.put("X2Top",x2);
   }
   public double getX2Right (){
       return triangle.getPoints().get(2);
   }
   public void setY2Right(double y2){
       triangle.getPoints().add(3, y2);
       properties.put("Y2Top",y2);
   }
   public double getY2Right (){
       return triangle.getPoints().get(3);
   }
   public void setX3Left(double x3){
       triangle.getPoints().add(4, x3);
       properties.put("X3Top",x3);
   }
   public double getX3Left (){
       return triangle.getPoints().get(4);
   }
   public void setY3Left(double y3){
       
       triangle.getPoints().add(5, y3);
       properties.put("Y3Top",y3);
   }
   public double getY3Left (){
       return triangle.getPoints().get(5);
   }

   
}
