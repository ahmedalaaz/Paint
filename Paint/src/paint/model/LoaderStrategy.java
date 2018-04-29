package paint.model;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public interface LoaderStrategy {
	
	public ArrayList<Shape> load(String path);
	public ArrayList<Shape> loadRealTimeConnetion(Object obj);
}

