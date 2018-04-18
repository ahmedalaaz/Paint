package plugin.selector;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;

public class SelectorController extends AnchorPane implements CommandPane {
	private String toolName;
	@FXML
	private JFXButton toolButton;

	public SelectorController() {
		this.toolName = "Selector";
		UILoader uiLoader = new UILoader(getClass().getResource("selector.fxml"), this);
		uiLoader.load();
	}

	@Override
	public void execute(Object canvas, MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void turnOnShapeListeners(ArrayList<Shape> shapes) {
		for (Shape shape : shapes) {
			shape.turnOnSelectListener();
		}
	}

	public void turnOffShapeListeners(ArrayList<Shape> shapes) {
		for (Shape shape : shapes) {
			shape.turnOffSelectListener();
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
		toolButton.setOnAction(value);

	}

	@Override
	public Class<?> getToolClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void triggerState() {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = Main.getController().getShapes();
		Main.getController().showExtrasPane();
		this.turnOnShapeListeners(shapes);
	}

	@Override
	public void pauseState() {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = Main.getController().getShapes();
		Main.getController().removeExtrasPane();
		this.turnOffShapeListeners(shapes);	
	}

}
