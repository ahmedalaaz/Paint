package paint.model;

import java.util.ArrayList;

public interface LoaderStrategy {
	
	public ArrayList<Shape> load(String path);

}

