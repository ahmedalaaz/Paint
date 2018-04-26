
package plugin.roundrectangle;

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
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import paint.model.AnimationAdder;
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;


public class RoundRectangleController extends AnchorPane implements CommandPane {

    private String toolName;
	final private String TOOL = "roundrectangle";
	@FXML
	private JFXButton roundRectangleButton;
	private CustomRoundRectangle roundRectangle;
	private double roundRectangleStartX;
	private double roundRectangleStartY;
        
    @Override
    public void execute(Object canvas, MouseEvent event) {
     if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			roundRectangle = new CustomRoundRectangle(55, 55);
			roundRectangle.setPosition(new Point((int)event.getX(), (int)event.getY()));
                        roundRectangle.setArcHeight(40);
                        roundRectangle.setArcWidth(40);
			roundRectangle.setFillColor(Paint.valueOf("#FFFFFF"));
			roundRectangle.setColor(Paint.valueOf("#000000"));
        	roundRectangle.setStrokeWidth(6);
			roundRectangleStartX = event.getX();
			roundRectangleStartY = event.getY();
			Main.getController().addShape(roundRectangle);;
			roundRectangle.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(roundRectangle == null) 
				return;
            double offsetX = event.getX() - roundRectangleStartX;
            double offsetY = event.getY() - roundRectangleStartY;
            if (offsetX > 0) {
                if (event.getX() > ((Region) roundRectangle.getParent()).getWidth())
                  roundRectangle.setWidth(((Region) roundRectangle.getParent()).getWidth() - roundRectangleStartX);
               else
                	roundRectangle.setWidth(offsetX);
            } else {
               if (event.getX() < 0)
                	roundRectangle.setX(0);
                else
                	roundRectangle.setX(event.getX());
                roundRectangle.setWidth(roundRectangleStartX - roundRectangle.getX());
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) roundRectangle.getParent()).getHeight())
                	roundRectangle.setHeight( ((Region) roundRectangle.getParent()).getHeight() - roundRectangleStartY);
                else
                	roundRectangle.setHeight(offsetY);
            } else {
                if (event.getY() < 0)
                	roundRectangle.setY(0);
                else
                	roundRectangle.setY(event.getY());
                roundRectangle.setHeight(roundRectangleStartY - roundRectangle.getY());
            }

        }

    }
    
        public RoundRectangleController() {
	        this.toolName = "RoundRectangle";
	        UILoader uiLoader;
			try {
				uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "plugin" + File.separator + TOOL + File.separator + "roundrectangle.fxml"),this);
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
        roundRectangleButton.setOnAction(value);
        AnimationAdder animation =  new AnimationAdder();
        animation.addShapeIconAnimation(roundRectangleButton);
    }

    @Override
    public void triggerState(ActionEvent event) {
    }

    @Override
    public void pauseState(ActionEvent event) {
    }

    @Override
    public Class<? extends Shape> getToolClass() {
        return CustomRoundRectangle.class;
    }

    @Override
    public void triggerState() {
    }
}
