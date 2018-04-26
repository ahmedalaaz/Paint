package plugin.rectangle;

import java.awt.Point;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import paint.model.AnimationAdder;
import paint.model.CommandPane;
import paint.view.Main;
import paint.view.UILoader;


public class RectangleController extends AnchorPane implements CommandPane {
	private String toolName;
	final private String TOOL = "rectangle";
	@FXML
	private JFXButton shapeButton;
	private CustomRectangle rectangle;
	private double rectangleStartX;
	private double rectangleStartY;

	@Override
	public void execute(Object canvas, MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			rectangle = new CustomRectangle(50, 50);
			rectangle.setPosition(new Point((int)event.getX(), (int)event.getY()));
			rectangle.setFillColor(Paint.valueOf("#FFFFFF"));
			rectangle.setColor(Paint.valueOf("#000000"));
        	rectangle.setStrokeWidth(6);
			rectangleStartX = event.getX();
			rectangleStartY = event.getY();
			Main.getController().addShape(rectangle);;
			rectangle.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(rectangle == null) 
				return;
            double offsetX = event.getX() - rectangleStartX;
            double offsetY = event.getY() - rectangleStartY;
            if (offsetX > 0) {
                if (event.getX() > ((Region) rectangle.getParent()).getWidth())
                  rectangle.setWidth(((Region) rectangle.getParent()).getWidth() - rectangleStartX);
               else
                	rectangle.setWidth(offsetX);
            } else {
               if (event.getX() < 0)
                	rectangle.setX(0);
                else
                	rectangle.setX(event.getX());
                rectangle.setWidth(rectangleStartX - rectangle.getX());
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) rectangle.getParent()).getHeight())
                	rectangle.setHeight( ((Region) rectangle.getParent()).getHeight() - rectangleStartY);
                else
                	rectangle.setHeight(offsetY);
            } else {
                if (event.getY() < 0)
                	rectangle.setY(0);
                else
                	rectangle.setY(event.getY());
                rectangle.setHeight(rectangleStartY - rectangle.getY());
            }

        }

		}
	
	public RectangleController() {
	        this.toolName = "Rectangle";
	        UILoader uiLoader;
			try {
				uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "plugin" + File.separator + TOOL + File.separator + "rectangle.fxml"),this);
		        uiLoader.load();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.toolName;
	}

	@Override
	public void setAction(EventHandler<ActionEvent> value) {
		// TODO Auto-generated method stub
		shapeButton.setOnAction(value);
		AnimationAdder animation =  new AnimationAdder();
        animation.addShapeIconAnimation(shapeButton);
	}
	@Override
	public Class<CustomRectangle> getToolClass() {
		// TODO Auto-generated method stub
		return CustomRectangle.class;
	}

	@Override
	public void triggerState(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseState(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerState() {
		// TODO Auto-generated method stub
		
	}

}
