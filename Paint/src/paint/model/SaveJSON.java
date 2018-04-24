package paint.model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.JSONObject;


public class SaveJSON implements SaverStrategy{

	@SuppressWarnings("unchecked")
	@Override
	public String save(ArrayList<Shape > shapes) {
		// TODO Auto-generated method stub
		JSONArray shapesArray = new JSONArray();
		org.json.simple.JSONObject returnObject = new org.json.simple.JSONObject();
		for(Shape shape : shapes) {
			JSONObject obj = new JSONObject();
			obj.put("shape", new JSONObject(shape.getJSONString()));
			shapesArray.add(obj);
		}
	returnObject.put("shapes", shapesArray);
	return returnObject.toJSONString();
	}
	
}