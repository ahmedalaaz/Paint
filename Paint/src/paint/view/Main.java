package paint.view;

import com.google.firebase.FirebaseApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paint.controller.CanvasController;
import paint.network.FirebaseDB;

public class Main extends Application {
	private static FXMLLoader myLoader;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setScene(getCanvasScene());
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Paint");
		primaryStage.show();
	}

	public  Scene getCanvasScene()throws Exception {
		/*FXMLLoader  loader =  new FXMLLoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
				+ "resources" + File.separator + "views" + File.separator + "canvas_view.fxml"));*/
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("/views/canvas_view.fxml"));
		myLoader = loader;
		Parent mainViewRoot = loader.load();
		Scene scene = new Scene(mainViewRoot);
		return scene;
	}
	public static CanvasController getController() {
		return myLoader.getController();
	}
}
