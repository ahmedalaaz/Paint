package paint.controller;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import paint.model.Caretaker;
import paint.model.CommandPane;
import paint.model.LoadJSON;
import paint.model.LoadXML;
import paint.model.MementoController;
import paint.model.LoaderStrategy;
import paint.model.PluginManager;
import paint.model.SaveJSON;
import paint.model.SaveXML;
import paint.model.SaverStrategy;
import paint.model.Shape;


public class CanvasController implements DrawingEngine ,Initializable{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Class<? extends Shape>> supportedShapes = new ArrayList();
	private SaverStrategy saver;
	private LoaderStrategy loader;
	private ArrayList<Shape> currentShape = new ArrayList<Shape>();
	@FXML
	private MenuBar mMenuBar;
	@FXML
	private Pane canvas;
	@FXML
	private Label modeLabel;
	@FXML
	private AnchorPane extrasTB;
	@FXML
	private JFXColorPicker fillColorPicker;
	@FXML
	private JFXColorPicker strokeColorPicker;
	@FXML
	private JFXButton applyStrokeBtn;
	@FXML
	private JFXButton applyFillBtn;
	@FXML
	private JFXComboBox<Integer> strokeWidthCB;
	@FXML
	private JFXButton confirmStrokeBtn;
	@FXML
	private JFXButton deleteBtn;
	@FXML
	private GridPane gridPane;
	private CommandPane selectedTool;

	
	public void viewCanvasView(Stage primaryStage) {
		primaryStage.show();
	}

	@Override
	public void refresh(Object canvas) {
		// TODO Auto-generated method stub
		this.canvas.getChildren().removeAll(this.canvas.getChildren());
		this.canvas.getChildren().addAll(((Pane)canvas).getChildren());
		selectedTool.triggerState();
	}
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub
		if(!canvas.getChildren().contains(shape))shape.draw(canvas);{
			ArrayList<Shape> shapes = getShapes();
			MementoController m = new MementoController();
			try {
				m.addLastScene(shapes);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		currentShape.add(shape);}
	}
	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		//remove from children
		shape.removeResizableRectangle();
		shape.removeFromParent();
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
		MementoController m = new MementoController();

		if(m.ifContains("Undo")) {
			try {
				Caretaker ct = new Caretaker();
				if(!ct.lastShapes.contains(getShapes()))
				{
				m.addBeforeUndo(getShapes());}
			}
			catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ArrayList<Shape> undoShapes = new ArrayList<>();
		undoShapes = m.returnShapes(-1);
		
		currentShape=undoShapes;
		refresh(canvas);
		for(Shape s : undoShapes)
			s.draw(canvas);
		}
		selectedTool.triggerState();
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		MementoController m = new MementoController();
		if(m.ifContains("Redo")) {
		ArrayList<Shape> redoShapes = new ArrayList<>();
		redoShapes = m.returnShapes(1);
		
		currentShape=redoShapes;
		refresh(canvas);
		for(Shape s : redoShapes)
			s.draw(canvas);
		}
		selectedTool.triggerState();
	}
	@Override
	public void save(String absolutePath) {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = this.getShapes();
		String outputToSave;
		try {
			outputToSave =  saver.save(shapes);
		}catch(Exception exception) {
			exception.printStackTrace();
			outputToSave = null;
		}
		if(outputToSave == null)return;//TODO show error Dialogue cannot save
		try (FileWriter file = new FileWriter(absolutePath)) {
			file.write(outputToSave);
			System.out.println("Successfully Copied JSON Object to File...");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.out.println(this.save(shapes));
	}
	@Override
	public void load(String path) {
		ArrayList<Shape> newShapes = loader.load(path);
		if(newShapes == null) {
			return;
			//TODO show error message
		}
		Pane canvas = new Pane();
		this.refresh(canvas);
		for(Shape shape : newShapes) {
			this.addShape(shape);
		}
		
	}
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return supportedShapes;
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
	
	
	private void initPlugins(){
	        System.out.println("Initializing plugins...\n");
	        PluginManager pluginManager = new PluginManager();
	        ArrayList<CommandPane> nodes = new ArrayList<>();
	        nodes = pluginManager.loadPlugins();
	        int col = 0;
	        int row = 0;

	        for(CommandPane node : nodes) {
	            gridPane.add((Node) node,col,row);
	           supportedShapes.add(node.getToolClass());
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
	 @FXML
	 protected void saveCurrentCanvas(ActionEvent event) {
		  FileChooser fileChooser = new FileChooser();

          // Set extension filter
          FileChooser.ExtensionFilter extFilter = 
                  new FileChooser.ExtensionFilter("Extensions Allowed ", "*.json","*.xml");
          fileChooser.getExtensionFilters().add(extFilter);

          // Show open file dialog
          File file = fileChooser.showOpenDialog((Stage)mMenuBar.getScene().getWindow());
          if (file != null) {
        	  System.out.println(file.getAbsolutePath());
        	  String extension = "";
        	  String fileName = file.getName();
        	  int i = fileName.lastIndexOf('.');
        	  int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        	  if (i > p) {
        	      extension = fileName.substring(i+1);
        	  }
        	switch(extension){
        	case "json" : 
        			this.saver = new SaveJSON();
        	     this.save(file.getAbsolutePath());
        		 
        		break;
        	case "xml" :
        		this.saver = new SaveXML();
        		 this.save(file.getAbsolutePath());
         		 
        	}
          }
      }
		@FXML
		protected void openSavedFile() {
			 FileChooser fileChooser = new FileChooser();

	          // Set extension filter
	          FileChooser.ExtensionFilter extFilter = 
	                  new FileChooser.ExtensionFilter("Extensions Allowed ", "*.json","*.xml");
	          fileChooser.getExtensionFilters().add(extFilter);

	          // Show open file dialog
	          File file = fileChooser.showOpenDialog((Stage)mMenuBar.getScene().getWindow());
	          if (file != null) {
	        	  System.out.println(file.getAbsolutePath());
	        	  String extension = "";
	        	  String fileName = file.getName();
	        	  int i = fileName.lastIndexOf('.');
	        	  int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

	        	  if (i > p) {
	        	      extension = fileName.substring(i+1);
	        	  }
	        	switch(extension){
	        	case "json" : 
	        		this.loader = new LoadJSON();
	        	     this.load(file.getAbsolutePath());	        		 
	        		break;
	        	case "xml" :
	        		this.loader = new LoadXML();
	        	     this.load(file.getAbsolutePath());	        		 
	        	}
	          }
		}
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		for(int i = 4 ; i<= 36 ; i+=2)strokeWidthCB.getItems().add(i);
		strokeWidthCB.setValue(6);
		applyStrokeBtn.setOnAction((event)-> {
			try {
				ShapesController.getInstance(CanvasController.this).changeStrokeColor(strokeColorPicker.getValue().toString());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		applyFillBtn.setOnAction((event)->{
			try {
				ShapesController.getInstance(CanvasController.this).changeFillColor(fillColorPicker.getValue().toString());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		});
		confirmStrokeBtn.setOnAction((event)-> {
			try {
				ShapesController.getInstance(CanvasController.this).changeStrokeWidth(strokeWidthCB.getValue());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		deleteBtn.setOnAction((event)->{
			try {
				ShapesController.getInstance(CanvasController.this).deleteSelectedShapes();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		initPlugins();	
	}

	
}
