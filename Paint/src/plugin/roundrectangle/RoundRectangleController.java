
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
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;
import plugin.rectangle.CustomRectangle;


public class RoundRectangleController extends AnchorPane implements CommandPane {

    private String toolName;
	final private String TOOL = "roundrectangle";
	@FXML
	private JFXButton RoundRectangleButton;
	private CustomRoundRectangle roundrectangle;
	private double roundrectangleStartX;
	private double roundrectangleStartY;
        
    @Override
    public void execute(Object canvas, MouseEvent event) {
     if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			roundrectangle = new CustomRoundRectangle(55, 55);
			roundrectangle.setPosition(new Point((int)event.getX(), (int)event.getY()));
                        roundrectangle.setArcHeight(40);
                        roundrectangle.setArcWidth(40);
			roundrectangle.setFillColor(Paint.valueOf("#FFFFFF"));
			roundrectangle.setColor(Paint.valueOf("#000000"));
			roundrectangleStartX = event.getX();
			roundrectangleStartY = event.getY();
			Main.getController().addShape(roundrectangle);;
			roundrectangle.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(roundrectangle == null) 
				return;
            double offsetX = event.getX() - roundrectangleStartX;
            double offsetY = event.getY() - roundrectangleStartY;
            if (offsetX > 0) {
                if (event.getX() > ((Region) roundrectangle.getParent()).getWidth())
                  roundrectangle.setWidth(((Region) roundrectangle.getParent()).getWidth() - roundrectangleStartX);
               else
                	roundrectangle.setWidth(offsetX);
            } else {
               if (event.getX() < 0)
                	roundrectangle.setX(0);
                else
                	roundrectangle.setX(event.getX());
                roundrectangle.setWidth(roundrectangleStartX - roundrectangle.getX());
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) roundrectangle.getParent()).getHeight())
                	roundrectangle.setHeight( ((Region) roundrectangle.getParent()).getHeight() - roundrectangleStartY);
                else
                	roundrectangle.setHeight(offsetY);
            } else {
                if (event.getY() < 0)
                	roundrectangle.setY(0);
                else
                	roundrectangle.setY(event.getY());
                roundrectangle.setHeight(roundrectangleStartY - roundrectangle.getY());
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
        RoundRectangleButton.setOnAction(value);
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
