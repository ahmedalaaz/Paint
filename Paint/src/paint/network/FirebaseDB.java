package paint.network;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import paint.controller.ShapesController;
import paint.model.LoadJSON;
import paint.model.LoaderStrategy;
import paint.view.Main;

public class FirebaseDB {
	private static FirebaseDB mInstance;
	private DatabaseReference mDatabase;
	public static FirebaseDB getInstance() throws IOException {
		if(mInstance == null)mInstance = new FirebaseDB();
		return mInstance;
	}
	private FirebaseDB() throws IOException {
		String Path = System.getProperty("user.dir") + "/paint_key.json";
		URL dirUrl = null;
		try {
			dirUrl = new URL("file:///" + Path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//FileInputStream serviceAccount = new FileInputStream(getClass().getResource("/paint_key.json").getPath());
		FileInputStream serviceAccount = new FileInputStream(dirUrl.getPath());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://paint-382d0.firebaseio.com/").build();
		FirebaseApp.initializeApp(options);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("canvas");
		
		ref.addValueEventListener(new ValueEventListener() {
		
			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				
				  String jsonString = gson.toJson(snapshot.getValue());
				 // System.out.println("snapshot value ::" + snapshot.getValue());
				  JSONParser parser = new JSONParser();
				try {
					org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(jsonString);
					org.json.simple.JSONObject current =  ShapesController.getInstance(Main.getController()).getCanvasShapesAsJsonSimple();
					String currentString = current.toJSONString();
				//	System.out.println("current :: " +currentString);
				//	System.out.println("new :: " + jsonString);
					String temp1 = currentString;
					String temp2 =  jsonString;
					char[] tempArr =  currentString.toCharArray();
					char[] tempArr2 =  jsonString.toCharArray();
					Arrays.sort(tempArr);
					Arrays.sort(tempArr2);
					temp1 =  new String(tempArr);
					temp2 = new String(tempArr2);
					if(temp1.equals(temp2) || jsonString == null) {
						System.out.println("Cancelled!!");
						return;
					}
					Platform.runLater(()->{
						ShapesController shapesController =  ShapesController.getInstance(Main.getController());
						shapesController.changeNetworkState(true);
						LoaderStrategy loader =  new LoadJSON();
					shapesController.refreshCanvasFromDataBase(obj , loader);
					shapesController.changeNetworkState(false);
					});

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("\nParsing exception::");
					e.printStackTrace();
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	@SuppressWarnings("deprecation")
	public void updateCanvas(JSONObject jsonObj) {
		mDatabase = FirebaseDatabase.getInstance().getReference().child("canvas");
		mDatabase.removeValueAsync();
		 Map<String, Object> shapesValues = jsonObj.toMap();
		 System.out.println(shapesValues.size());
	    mDatabase.updateChildrenAsync(shapesValues);
	}
	
}
