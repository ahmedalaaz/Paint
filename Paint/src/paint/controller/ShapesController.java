package paint.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import paint.model.CustomClassLoader;
import paint.model.LoaderStrategy;
import paint.model.MementoController;
import paint.model.MultipleResizeState;
import paint.model.Shape;
import paint.network.FirebaseDB;
import paint.view.Main;

public class ShapesController {
private CanvasController canvasController;
private static ShapesController myInstance;
private boolean initial = true;
private boolean network = false;
private ArrayList<Shape> clipBoardArray;
private ArrayList<CustomClassLoader> loaders =  new ArrayList<>();
private ShapesController(CanvasController canvasController){
	this.canvasController = canvasController;
}
public static ShapesController getInstance(CanvasController canvasController) {
	return myInstance == null ? myInstance =  new ShapesController(canvasController) : myInstance;
}
public static ShapesController getInstance() {
	return myInstance == null ? null : myInstance;
}
public void addLoader(CustomClassLoader loader) {
	this.loaders.add(loader);
}
public CustomClassLoader getLoader(String className) {
	for(CustomClassLoader loader : loaders) {
		if(loader.getmClass() == null)continue;
		//System.out.println(className + " --- > " +loader.getmClass().getName());
		if(loader.getmClass().getName().equals(className)) {
			return loader;
		}
	}
	return null;
}
public void changeStrokeColor(String strokeColor) throws CloneNotSupportedException{
	
	ArrayList<Shape> shapes =  canvasController.getShapes();
	MementoController m = new MementoController();
	m.addLastScene(shapes);
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setColor(Paint.valueOf(strokeColor));
		}
	}
	FirebaseDB firebase;
	try {
		firebase = FirebaseDB.getInstance();
		firebase.updateCanvas(this.getCanvasShapesAsJson());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public void changeFillColor(String fillColor) throws CloneNotSupportedException{
	
	ArrayList<Shape> shapes =  canvasController.getShapes();
	MementoController m = new MementoController();
	m.addLastScene(shapes);
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setFillColor(Paint.valueOf(fillColor));
		}
	}
	FirebaseDB firebase;
	try {
		firebase = FirebaseDB.getInstance();
		firebase.updateCanvas(this.getCanvasShapesAsJson());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void changeStrokeWidth(Integer value) throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes =  canvasController.getShapes();
	MementoController m = new MementoController();
	m.addLastScene(shapes);
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setStrokeWidth(value);			
		}
	}
	FirebaseDB firebase;
	try {
		firebase = FirebaseDB.getInstance();
		firebase.updateCanvas(this.getCanvasShapesAsJson());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void deleteSelectedShapes() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes =  canvasController.getShapes();

	MementoController m = new MementoController();
	m.addLastScene(shapes);
	ArrayList<Shape> shapesToRemove = new ArrayList<Shape>();
	for( Shape shape : shapes) {
		if(shape.isSelected()) {
			shapesToRemove.add(shape);
		}
	}
	for(Shape shape : shapesToRemove) {
		Main.getController().removeShape(shape);
	}
	FirebaseDB firebase;
	try {
		firebase = FirebaseDB.getInstance();
		firebase.updateCanvas(this.getCanvasShapesAsJson());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public boolean isSupportedShape(String c) {
	ArrayList<Class<? extends Shape>> supportedShapes = (ArrayList<Class<? extends Shape>>) canvasController.getSupportedShapes();
	for(Class<? extends Shape> t : supportedShapes) {
		if(t== null)continue;
		if(t.getName().equals(c))return true;
	}
	return false;
}

public void resizeAllSelected(MultipleResizeState resizeMoveState, double deltaX, double deltaY) throws CloneNotSupportedException {

	ArrayList<Shape> shapes = canvasController.getShapes();
	if(initial) {
	MementoController m = new MementoController();
	m.addLastScene(shapes);
	initial = false;
	}
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			resizeMoveState.trigger(shape, deltaX, deltaY);
		}
	}
}
public void changeInitial(boolean value) {
	initial = value;
}
@SuppressWarnings("unchecked")
public org.json.JSONObject getCanvasShapesAsJson() {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes = canvasController.getShapes();
	JSONArray shapesArray = new JSONArray();
	org.json.JSONObject returnObject = new org.json.JSONObject();
	for(Shape shape : shapes) {
		JSONObject obj = new JSONObject();
		obj.put("shape", new JSONObject(shape.getJSONString()));
		shapesArray.add(obj);
	}
returnObject.put("shapes", shapesArray);
	return returnObject;
}
@SuppressWarnings("unchecked")
public org.json.simple.JSONObject getCanvasShapesAsJsonSimple() {
	// TODO Auto-generated method stub
	ArrayList<Shape> shapes = canvasController.getShapes();
	JSONArray shapesArray = new JSONArray();
	org.json.simple.JSONObject returnObject = new org.json.simple.JSONObject();
	for(Shape shape : shapes) {
		org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
		obj.put("shape", new JSONObject(shape.getJSONString()));
		shapesArray.add(obj);
	}
returnObject.put("shapes", shapesArray);
	return returnObject;
}

public void refreshCanvasFromDataBase(org.json.simple.JSONObject  obj , LoaderStrategy loader) {
	// TODO Auto-generated method stub
	ArrayList<Shape> newShapes = loader.loadRealTimeConnetion(obj);
	if (newShapes == null) {
		return;
		// TODO show error message
	}
	Pane canvas = new Pane();
	System.out.println("Refreshing");
	canvasController.refresh(canvas);
	//System.out.println(newShapes.size());
	for (Shape shape : newShapes) {
		//System.out.println(shape.getClass());
		canvasController.addShapeForRealTimeConnection(shape);
	}

}
public boolean isFromDB() {
	return network;
}
public void changeNetworkState(boolean state) {
	this.network = state;
}
public void addSelectedShapesToClipBoard() {
	// TODO Auto-generated method stub
	clipBoardArray = new ArrayList<>();
	ArrayList<Shape> shapes = canvasController.getShapes();
	for(Shape shape : shapes ) {
		if(shape.isSelected()) {
			try {
				clipBoardArray.add((Shape)shape.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
public void pasteClipBoard() {
	if(clipBoardArray.isEmpty())return;
ArrayList<Shape> shapes =  canvasController.getShapes();
for(Shape shape : shapes) {
	if(shape.isSelected())shape.removeResizableRectangle();
}
for(Shape shape : clipBoardArray) {
	this.network = true;
	canvasController.addShape(shape);
	this.network = false;
	shape.bindToResizableRectangle();
}
clipBoardArray = new ArrayList<>();
}

}
