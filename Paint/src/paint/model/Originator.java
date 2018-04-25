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
	try {
		arr = m.getSavedArray();
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return arr;
}
}
