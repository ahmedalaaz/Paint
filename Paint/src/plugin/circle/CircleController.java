
package plugin.circle;

import com.jfoenix.controls.JFXButton;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;

public class CircleController extends AnchorPane implements CommandPane {
     
    private String toolName;
	final private String TOOL = "circle";
	@FXML
	private JFXButton circleButton;
	private CustomCircle circle;
	private double circleStartX;
	private double circleStartY;
    @Override
    public void execute(Object canvas, MouseEvent event) {
        if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
        	Pane parent = (Pane)canvas;
        	if(event.getX() + 50 >= parent.getBoundsInLocal().getWidth() ||
        			event.getX() - 50 <= 0  || event.getY()+50 >= parent.getBoundsInLocal().getHeight() ||
        			event.getY() -50 <= 0)return;
        	circle = new CustomCircle(50);
        	circle.setPosition(new Point((int)event.getX(), (int)event.getY()));
        	circle.setFillColor(Paint.valueOf("#FFFFFF"));
        	circle.setColor(Paint.valueOf("#000000"));
			circleStartX = event.getX();
			circleStartY = event.getY();
			Main.getController().addShape(circle);;
			circle.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(circle == null) 
				return;
            double offsetX = event.getX() - circleStartX;
            double offsetY = event.getY() - circleStartY;
            if (offsetX > 0) {
              
                if (event.getX() > ((Region) circle.getParent()).getWidth())
                  circle.setRadius(((Region) circle.getParent()).getWidth() - circleStartX);
                
                else{
                
                    circle.setRadius(offsetX);
                }
                	
            } else {
               if (event.getX() < 0){
                   
                   circle.setRadius(circleStartX);
               }       
               else{
                
                circle.setRadius(circleStartX - event.getX());
               }
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) circle.getParent()).getHeight())
                	circle.setRadius(((Region) circle.getParent()).getHeight() - circleStartY);
              
              else if(circleStartY==circle.getRadius()){
                   circle.setRadius(circleStartY); 
                }
              else{
                	circle.setRadius(offsetY);
              }
            } else {
                if (event.getY() < 0)
                	circle.setRadius(circleStartY);
                else
                	//ellipse.setCenterY(event.getY());
                circle.setRadius(circleStartY - event.getY());
            }

        }

    }
    public CircleController() {
        this.toolName = "Circle";
	        UILoader uiLoader;
			try {
				uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "plugin" + File.separator + TOOL + File.separator + "circle.fxml"),this);
		        uiLoader.load();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    @Override
    public String getName() {
        return this.toolName;
    }

    @Override
    public void setAction(EventHandler<ActionEvent> value) {
        circleButton.setOnAction(value);
    }

    @Override
    public void triggerState(ActionEvent event) {
        
    }

    @Override
    public void pauseState(ActionEvent event) {
        
    }

    @Override
    public Class<? extends Shape> getToolClass() {
        return CustomCircle.class;
    }

    
    
}
