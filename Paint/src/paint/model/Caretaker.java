package paint.model;

import java.util.ArrayList;

public class Caretaker {
	ArrayList<Memento> lastShapes = new ArrayList<>();
	public void addMemento(Memento memento) {
		if(lastShapes.size()==20)
			lastShapes.remove(0);
		lastShapes.add(memento);
	}
	public Memento getMemento(int index) { 
		return lastShapes.get(index);
	}

}
