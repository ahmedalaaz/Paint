package paint.model;

import java.util.ArrayList;

public class Originator {
	private ArrayList<Shape>arr;
	public void set(ArrayList<Shape>arr) {
		this.arr=arr;
	}
public Memento storedInMemento() {
	return new Memento(arr);
}
public ArrayList<Shape> restoreFromMemento(Memento m) {
	arr = m.getSavedArray();
	return arr;
}
}
