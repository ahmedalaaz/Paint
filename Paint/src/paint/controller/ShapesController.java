package paint.controller;

import java.util.ArrayList;
import javafx.scene.paint.Paint;
import paint.model.CustomClassLoader;
import paint.model.LoaderStrategy;
import paint.model.MementoController;
import paint.model.MultipleResizeState;
import paint.model.Shape;
import paint.view.Main;

public class ShapesController {
private CanvasController canvasController;
private static ShapesController myInstance;
private boolean initial = true;
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
		System.out.println(className + " " +loader.getmClass().getName());
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


}
