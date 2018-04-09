package plugin.rectangle;

import java.awt.Point;

import com.jfoenix.controls.JFXButton;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import paint.model.CommandPane;
import paint.model.UILoader;

public class RectangleController extends AnchorPane implements CommandPane {
	private String toolName;
	@FXML
	private JFXButton shapeButton;
	private CustomRectangle rectangle;
	/*
	 * @param canvas is the root pane
	 * @param event is the MouseEvent happened
	 * TODO handle the mouseEvent here draw ,resize,move !
	 */
	@Override
	public void execute(Object canvas, MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			rectangle = new CustomRectangle(20, 20);
			rectangle.setPosition(new Point((int)event.getX(), (int)event.getY()));
			rectangle.draw(canvas);
			
		}
	}
	public RectangleController() {
	        this.toolName = "Rectangle";
	        UILoader uiLoader = new UILoader(getClass().getResource("rectangle.fxml"),this);
	        uiLoader.load();
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

	}
	@Override
	public Class<CustomRectangle> getToolClass() {
		// TODO Auto-generated method stub
		return CustomRectangle.class;
	}

}
