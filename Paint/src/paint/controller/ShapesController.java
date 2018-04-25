package paint.controller;

import java.util.ArrayList;
import javafx.scene.paint.Paint;
import paint.model.MultipleResizeState;
import paint.model.Shape;
import paint.view.Main;

public class ShapesController {
private CanvasController canvasController;
private static ShapesController myInstance;
private ShapesController(CanvasController canvasController){
	this.canvasController = canvasController;
}
public static ShapesController getInstance(CanvasController canvasController) {
	return myInstance == null ? myInstance =  new ShapesController(canvasController) : myInstance;
}
public static ShapesController getInstance() {
	return myInstance == null ? null : myInstance;
}

public void changeStrokeColor(String strokeColor){
	
	ArrayList<Shape> shapes =  canvasController.getShapes();
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
			shape.setColor(Paint.valueOf(strokeColor));
		}
	}
}
public void changeFillColor(String fillColor){
	
	ArrayList<Shape> shapes =  canvasController.getShapes();
	for(Shape shape : shapes) {
		if(shape.isSelected()) {
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

public boolean isSupportedShape(String c) {
	ArrayList<Class<? extends Shape>> supportedShapes = (ArrayList<Class<? extends Shape>>) canvasController.getSupportedShapes();
	for(Class<? extends Shape> t : supportedShapes) {
		if(t== null)continue;
		if(t.getName().equals(c))return true;
	}
	return false;
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
