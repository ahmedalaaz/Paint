package paint.plugin.square;

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

public class SquareController extends AnchorPane implements CommandPane {
	private String toolName;
	final private String TOOL = "square";
	@FXML
	private JFXButton squareButton;
	private CustomSquare square;
	private double squareStartX;
	private double squareStartY;

	@Override
	public void execute(Object canvas, MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			square = new CustomSquare(50);
			square.setPosition(new Point((int)event.getX(), (int)event.getY()));
			square.setFillColor(Paint.valueOf("#FFFFFF"));
			square.setColor(Paint.valueOf("#000000"));
        	square.setStrokeWidth(6);
			squareStartX = event.getX();
			squareStartY = event.getY();
			Main.getController().addShape(square);;
			square.draw(canvas);
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(square == null) 
				return;
            double offsetX = event.getX() - squareStartX;
            double offsetY = event.getY() - squareStartY;
            if (offsetX > 0) {
                if (event.getX() > ((Region) square.getParent()).getWidth())
                  square.setSide(((Region) square.getParent()).getWidth() - squareStartX);
               else
                	square.setSide(offsetX);
            } else {
               if (event.getX() < 0)
                	square.setX(0);
                else
                	square.setX(event.getX());
                square.setSide(squareStartX - event.getX());
            }

            if (offsetY > 0) {
              if (event.getY() >  ((Region) square.getParent()).getHeight())
                	square.setSide( ((Region) square.getParent()).getHeight() - squareStartY);
                else
                	square.setSide(offsetY);
            } else {
                if (event.getY() < 0)
                	square.setY(0);
                else
                	square.setY(event.getY());
                square.setSide(squareStartY - square.getY());
            }

        }

		}
	
	public SquareController() {
	        this.toolName = "Square";
	        UILoader uiLoader;
			try {
				//uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
			//			+ "paint" + File.separator + "plugin" + File.separator + TOOL + File.separator + "square.fxml"),this);
				uiLoader = new UILoader( getClass().getResource("square.fxml"),this);
		        uiLoader.load();

			} catch (Exception e) {
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
		squareButton.setOnAction(value);
		AnimationAdder animation =  new AnimationAdder();
        animation.addShapeIconAnimation(squareButton);
	}
	@Override
	public Class<CustomSquare> getToolClass() {
		// TODO Auto-generated method stub
		return CustomSquare.class;
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
