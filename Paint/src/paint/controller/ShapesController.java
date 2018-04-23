package paint.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import paint.model.LoadJSON;
import paint.model.LoaderStrategy;
import paint.model.MultipleResizeState;
import paint.model.SaverStrategy;
import paint.model.Shape;
import paint.view.Main;

public class ShapesController {
private CanvasController canvasController;
private static ShapesController myInstance;
private SaverStrategy saver;
private ShapesController(CanvasController canvasController){
	this.canvasController = canvasController;
}
public static ShapesController getInstance(CanvasController canvasController) {
	return myInstance == null ? myInstance =  new ShapesController(canvasController) : myInstance;
}
public static ShapesController getInstance() {
	return myInstance == null ? null : myInstance;
}

public void changeColors(String fillColor , String strokeColor){
	
	ArrayList<Shape> shapes =  canvasController.getShapes();
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setColor(Paint.valueOf(strokeColor));
			shape.setFillColor(Paint.valueOf(fillColor));
		}
	}
}
public void changeStrokeWidth(Integer value) {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes =  canvasController.getShapes();
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setStrokeWidth(value);			
		}
	}
}
public void deleteSelectedShapes() {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes =  canvasController.getShapes();
	ArrayList<Shape> shapesToRemove = new ArrayList<Shape>();
	for( Shape shape : shapes) {
		if(shape.isSelected()) {
			shapesToRemove.add(shape);
		}
	}
	for(Shape shape : shapesToRemove) {
		Main.getController().removeShape(shape);
	}
}
public void saveCurrentScene(String absolutePath,SaverStrategy saver) {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes = canvasController.getShapes();
	this.saver =saver;
	String outputToSave = this.save(shapes);
	try (FileWriter file = new FileWriter(absolutePath)) {
		file.write(outputToSave);
		System.out.println("Successfully Copied JSON Object to File...");
		System.out.println("\nJSON Object: " + outputToSave);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	//System.out.println(this.save(shapes));
}

public String save(ArrayList<Shape> shapes) {
	try {
		return saver.save(shapes);
	}catch(Exception exception) {
		exception.printStackTrace();
	}
	return null;
}
public void changeSavingStrategy(SaverStrategy saver) {
	this.saver = saver;
}
public boolean isSupportedShape(String c) {
	ArrayList<Class<? extends Shape>> supportedShapes = (ArrayList<Class<? extends Shape>>) canvasController.getSupportedShapes();
	for(Class<? extends Shape> t : supportedShapes) {
		if(t== null)continue;
		if(t.getName().equals(c))return true;
	}
	return false;
}
public void loadSavedScene(String absolutePath, LoaderStrategy loadJSON) {
	ArrayList<Shape> newShapes = loadJSON.load(absolutePath);
	Pane canvas = new Pane();
	canvasController.refresh(canvas);
	for(Shape shape : newShapes) {
		canvasController.addShape(shape);
	}
}
public void resizeAllSelected(MultipleResizeState resizeMoveState, double deltaX, double deltaY) {
	ArrayList<Shape> shapes = canvasController.getShapes();
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			resizeMoveState.trigger(shape, deltaX, deltaY);
		}
	}
}	


}
