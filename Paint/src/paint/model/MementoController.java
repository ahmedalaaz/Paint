package paint.model;

import java.util.ArrayList;

public class MementoController {

	public static int index = 0;
	public static int undo = 0;

	public void addLastScene(ArrayList<Shape> shapes) throws CloneNotSupportedException {
		ArrayList<Shape> copiedArray = new ArrayList<>();
		Originator or = new Originator();
		Caretaker ct = new Caretaker();
		int size = ct.lastShapes.size();
		int removedIndex = index;

		for (int i = index; i < size; i++)
			ct.removeMemento(removedIndex);

		for (Shape s : shapes)
			copiedArray.add((Shape) s.clone());

		or.set(copiedArray);
		ct.addMemento(or.storedInMemento());
		index = ct.lastShapes.size();

		undo = 0;
	}

	public void addBeforeUndo(ArrayList<Shape> shapes) throws CloneNotSupportedException {
		if (undo == 0) {
			ArrayList<Shape> copiedArray = new ArrayList<>();
			Originator or = new Originator();
			Caretaker ct = new Caretaker();
			for (Shape s : shapes) {
				copiedArray.add((Shape) s.clone());
			}

			or.set(copiedArray);

			ct.addMemento(or.storedInMemento());
			index++;

		}
		undo++;
	}

	public ArrayList<Shape> returnShapes(int n) {
		Originator or = new Originator();
		Caretaker ct = new Caretaker();
		if (n == -1) {
			if (undo <= 1)
				index -= 2;
			else
				index--;

			return or.restoreFromMemento(ct.getMemento(index));
		} else
			return or.restoreFromMemento(ct.getMemento(++index));

	}

	public boolean ifContains(String state) {
		if (state.equals("Undo")) {
			return index - 1 >= 0 ? true : false;
		} else {
			Caretaker ct = new Caretaker();
			return ct.lastShapes.size() > index + 1 ? true : false;
		}
	}

}
