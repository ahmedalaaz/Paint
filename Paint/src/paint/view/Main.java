package paint.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import paint.controller.CanvasController;

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
		primaryStage.setTitle("Paint");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public  Scene getCanvasScene()throws Exception {
		FXMLLoader  loader =  new FXMLLoader(getClass().getResource("/resources/views/canvas_view.fxml"));
		myLoader = loader;
		Parent mainViewRoot = loader.load();
		Scene scene = new Scene(mainViewRoot);
		return scene;
	}
	public static CanvasController getController() {
		return myLoader.getController();
	}
}
