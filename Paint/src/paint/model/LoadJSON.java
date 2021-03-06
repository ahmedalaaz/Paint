package paint.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import paint.controller.ShapesController;
import paint.view.Main;

public class LoadJSON implements LoaderStrategy {

	@Override
	public ArrayList<Shape> load(String path) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		ArrayList<Shape> newShapes = new ArrayList<>();
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray shapes = (JSONArray) jsonObject.get("shapes");
			if (shapes == null) {
				// TODO show error message cannot load file
				System.out.println("error loading!!!");
				return null;
			}
			for (Object iterator : shapes) {
				JSONObject shapeObj = (JSONObject) iterator;
				JSONObject shape = (JSONObject) shapeObj.get("shape");
				String className = (String)shape.get("class");
				className = className.substring(6, className.length());
				if (!ShapesController.getInstance(Main.getController()).isSupportedShape(className)) {
					
				System.err.println("Not Supported shape");
					return null;// TODO show error message cannot load
				}Shape tempShape =    (new ShapesFactory()).instantiate(className, Shape.class);
				if(tempShape == null) {
					System.out.println("Error while loading!");
					return null;//TODO show error message cannot load
				}
				tempShape.loadJSON(shape);
				newShapes.add(tempShape);
				newShapes.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newShapes;
	}
	public ArrayList<Shape> loadRealTimeConnetion(Object obj) {
		// TODO Auto-generated method stub
		ArrayList<Shape> newShapes = new ArrayList<>();
		try {
			JSONObject jsonObject = (JSONObject)obj;
			if(jsonObject == null) {
				return null;
			}
			JSONArray shapes = (JSONArray) jsonObject.get("shapes");
			if (shapes == null) {
				// TODO show error message cannot load file
				return null;
			}
			for (Object iterator : shapes) {
				JSONObject shapeObj = (JSONObject) iterator;
				JSONObject shape = (JSONObject) shapeObj.get("shape");
				String className = (String)shape.get("class");
				className = className.substring(6, className.length());
				if (!ShapesController.getInstance(Main.getController()).isSupportedShape(className))
					return null;// TODO show error message cannot load
				Shape tempShape =    (new ShapesFactory()).instantiate(className, Shape.class);
				if(tempShape == null) {
					System.out.println("Error while loading!!");
					return null;//TODO show error message cannot load
				}
				tempShape.loadJSON(shape);
				System.out.println(tempShape.getClass());
				newShapes.add(tempShape);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newShapes;
	}



}
