package paint.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import paint.model.CommandPane;
import paint.model.Mover;
import paint.model.PluginManager;
import paint.model.Shape;
import plugin.rectangle.CustomRectangle;


public class CanvasController implements DrawingEngine ,Initializable{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Class<? extends Shape>> supportedShapes = new ArrayList();;
	private ArrayList<Shape> currentShape = new ArrayList<Shape>();
	@FXML
	private Pane canvas;
	@FXML
	private Label modeLabel;
	@FXML
	private AnchorPane extrasPane;
	@FXML
	private AnchorPane extrasTB;
	@FXML
	private JFXColorPicker fillColorPicker;
	@FXML
	private JFXColorPicker strokeColorPicker;
	@FXML
	private JFXButton applyColorsBtn;
	@FXML
	private JFXComboBox<Integer> strokeWidthCB;
	@FXML
	private JFXButton confirmStrokeBtn;
	@FXML
	private JFXButton moverBtn;
	@FXML
	private JFXButton resizerBtn;
	@FXML
	private Label resizingModeLabel;
	@FXML
	private Label movingModeLabel;
	private boolean movingModeSelected = false;
	private boolean resizingModeSelected = false;
	
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
		currentShape.add(shape);
	}
	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		//remove from children
		currentShape.remove(shape);
	}
	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		currentShape.remove(oldShape);
		currentShape.add(newShape);
	}
	@Override
	public ArrayList<Shape> getShapes() {
		// TODO Auto-generated method stub
		return currentShape;
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
	            	if(selectedTool != null) {
	            		selectedTool.pauseState(event);
	            	}
	                selectedTool = node;
	                selectedTool.triggerState(event);
	                
	            });
	        }
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		extrasPane.setVisible(false);
		extrasTB.setVisible(false);
		movingModeLabel.setStyle("-fx-background-color: #FF4500");
		resizingModeLabel.setStyle("-fx-background-color: #FF4500");
		resizingModeSelected = false;
		movingModeSelected = false;
		for(int i = 4 ; i<= 36 ; i+=2)strokeWidthCB.getItems().add(i);
		applyColorsBtn.setOnAction((event)-> {
			ShapesController.getInstance(CanvasController.this).changeColors(fillColorPicker.getValue().toString()
					, strokeColorPicker.getValue().toString());
		});
		confirmStrokeBtn.setOnAction((event)-> {
			ShapesController.getInstance(CanvasController.this).changeStrokeWidth(strokeWidthCB.getValue());
		});
		moverBtn.setOnAction((event)-> {
			movingModeLabel.setStyle("-fx-background-color: #98FB98");;
			resizingModeLabel.setStyle("-fx-background-color: #FF4500");
			resizingModeSelected = false;
			movingModeSelected = false;
			Scene currentScene = (Scene) ((Node) event.getSource()).getScene();
			currentScene.setCursor(Cursor.MOVE);
		});
		resizerBtn.setOnAction((event)-> { 
			resizingModeLabel.setStyle("-fx-background-color: #98FB98");;
			movingModeLabel.setStyle("-fx-background-color: #FF4500");
			movingModeSelected = false;
			resizingModeSelected = false;
			Scene currentScene = (Scene) ((Node) event.getSource()).getScene();
			currentScene.setCursor(Cursor.DEFAULT);
		});
		initPlugins();
		
		
	}

	public void showExtrasPane() {
		// TODO Auto-generated method stub
		extrasPane.setVisible(true);
		extrasTB.setVisible(true);
	}
	public void removeExtrasPane() {
		// TODO Auto-generated method stub
		extrasPane.setVisible(false);
		extrasTB.setVisible(false);
	}
	public boolean isMovingModeSelected() {
		return movingModeSelected;
		
	}
	public boolean isResizingModeSelected() {
		return resizingModeSelected;
	}
	

}
