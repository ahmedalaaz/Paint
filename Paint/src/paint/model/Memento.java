package paint.model;

import java.util.ArrayList;

public class Memento {
	private ArrayList<Shape>ShapesArray;

	public Memento(ArrayList<Shape> arr) {
		ShapesArray=arr;
	}
	public ArrayList<Shape>getSavedArray() throws CloneNotSupportedException{
		ArrayList<Shape> returnedArray = new ArrayList<>();
		for(Shape s : ShapesArray)
			returnedArray.add((Shape) s.clone());
		return returnedArray;
	}
	
	
}
