package paint.model;

import java.util.ArrayList;

public class Memento {
	private ArrayList<Shape>ShapesArray;

	public Memento(ArrayList<Shape> arr) {
		ShapesArray=arr;
	}
	public ArrayList<Shape>getSavedArray(){
		return ShapesArray;
	}
	
	
}
