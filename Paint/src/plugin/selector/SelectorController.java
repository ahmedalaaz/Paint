package plugin.selector;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
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
	final private String TOOL = "selector";
	@FXML
	private JFXButton toolButton;
	public SelectorController() {
		this.toolName = "Selector";
		UILoader uiLoader;
		try {
			uiLoader = new UILoader(new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "plugin" + File.separator + TOOL + File.separator + "selector.fxml"), this);
			uiLoader.load();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public Class<? extends Shape> getToolClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void triggerState(ActionEvent event) {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = Main.getController().getShapes();
		this.turnOnShapeListeners(shapes);
	}

	@Override
	public void pauseState(ActionEvent event) {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = Main.getController().getShapes();
		this.turnOffShapeListeners(shapes);	
	}

}
