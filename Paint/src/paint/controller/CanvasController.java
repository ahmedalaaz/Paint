package paint.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import paint.model.CommandPane;
import paint.model.PluginManager;


public class CanvasController implements DrawingEngine ,Initializable{
	private final String FREE_DRAWING = "Free Drawing";
	private final String RUBBER_DRAWING = "Rubber Drawing";
	private ArrayList<Class<? extends Shape>> supportedShapes = new ArrayList();;
	@FXML
	private JFXColorPicker colorPicker;
	@FXML
	private Pane canvas;
	@FXML
	private JFXComboBox<Integer> sizesCB;
	@FXML
	private Label modeLabel;
	@FXML
	private GridPane gridPane;
	private CommandPane selectedTool;

	public void viewCanvasView(Stage primaryStage) {
		primaryStage.show();
	}

	@Override
	public void refresh(Object canvas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void installPluginShape(String jarPath) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	protected void onCanvasPressed(MouseEvent event) {
		//TODO add switch to choose drawing type.
		if(selectedTool!=null) {
			selectedTool.execute(canvas, event);
		}
	}
	@FXML
	protected void onCanvasDragged(MouseEvent event) {
		//TODO add switch to choose drawing type.
		if(selectedTool!=null) {
			selectedTool.execute(canvas, event);
		}
	}
	@FXML
	protected void switchToPenMode() {
		modeLabel.setText(FREE_DRAWING);
	}
	@FXML
	protected void switchToRubberMode() {
		modeLabel.setText(RUBBER_DRAWING);
		sizesCB.setValue(36);
	}
	
	 @SuppressWarnings("unchecked")
	private void initPlugins(){

	        System.out.println("Initializing plugins...\n");
	        PluginManager pluginManager = new PluginManager();
	        ArrayList<CommandPane> nodes = new ArrayList<>();

	        nodes = pluginManager.loadPlugins();
	        
	        int col = 0;
	        int row = 0;

	        for(CommandPane node : nodes) {
	            gridPane.add((Node) node,col,row);
	           supportedShapes.add((Class<? extends Shape>) node.getToolClass());
	            col = (++col)%2;
	            if(col == 0)
	                row = (++row)%9;
	            @SuppressWarnings("rawtypes")
				Class nodeClass = node.getClass();
	            nodeClass.cast(node);
	            node.setAction((event) -> {
	            	modeLabel.setText(node.getName());
	                selectedTool = node;
	            });
	        }
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initPlugins();
		for(int i = 4 ; i <= 72 ; i+=2)sizesCB.getItems().add(i);
		sizesCB.setValue(12);
		modeLabel.setText(FREE_DRAWING);
		
	}

}
