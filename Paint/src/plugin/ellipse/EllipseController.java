
package plugin.ellipse;

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
import plugin.rectangle.CustomRectangle;

public class EllipseController extends AnchorPane implements CommandPane {
     
    private String toolName;
	final private String TOOL = "ellipse";
	@FXML
	private JFXButton ellipseButton;
	private CustomEllipse ellipse;
	private double ellipseStartX;
	private double ellipseStartY;
    @Override
    public void execute(Object canvas, MouseEvent event) {
        if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
        	Pane parent = (Pane)canvas;
        	if(event.getX() + 20 >= parent.getBoundsInLocal().getWidth() ||
        			event.getX() - 20 <= 0  || event.getY()+20 >= parent.getBoundsInLocal().getHeight() ||
        			event.getY() -20 <= 0)return;
			ellipse = new CustomEllipse(20, 20);
			ellipse.setPosition(new Point((int)event.getX(), (int)event.getY()));
			ellipse.setFillColor(Paint.valueOf("#FFFFFF"));
			ellipse.setColor(Paint.valueOf("#000000"));
			ellipseStartX = event.getX();
			ellipseStartY = event.getY();
			Main.getController().addShape(ellipse);;
			ellipse.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(ellipse == null) 
				return;
            double offsetX = event.getX() - ellipseStartX;
            double offsetY = event.getY() - ellipseStartY;
            if (offsetX > 0) {
              
                if (event.getX() > ((Region) ellipse.getParent()).getWidth())
                  ellipse.setRadiusX(((Region) ellipse.getParent()).getWidth() - ellipseStartX);
                
                else{
                
                    ellipse.setRadiusX(offsetX);
                }
                	
            } else {
               if (event.getX() < 0){
                   
                   ellipse.setRadiusX(ellipseStartX);
               }       
               else{
                
                ellipse.setRadiusX(ellipseStartX - event.getX());
               }
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) ellipse.getParent()).getHeight())
                	ellipse.setRadiusY(((Region) ellipse.getParent()).getHeight() - ellipseStartY);
              
              else if(ellipseStartY==ellipse.getRadiusY()){
                   ellipse.setRadiusY(ellipseStartY); 
                }
              else{
                	ellipse.setRadiusY(offsetY);
              }
            } else {
                if (event.getY() < 0)
                	ellipse.setRadiusY(ellipseStartY);
                else
                	//ellipse.setCenterY(event.getY());
                ellipse.setRadiusY(ellipseStartY - event.getY());
            }

        }

    }
    public EllipseController() {
        this.toolName = "Ellipse";
	        UILoader uiLoader;
			try {
				uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "plugin" + File.separator + TOOL + File.separator + "ellipse.fxml"),this);
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
        ellipseButton.setOnAction(value);
    }

    @Override
    public void triggerState(ActionEvent event) {
        
    }

    @Override
    public void pauseState(ActionEvent event) {
        
    }

    @Override
    public Class<? extends Shape> getToolClass() {
        return CustomEllipse.class;
    }
	@Override
	public void triggerState() {
		// TODO Auto-generated method stub
		
	}

    
    
}
