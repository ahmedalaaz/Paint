package paint.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import paint.controller.CanvasController;

public class Main extends Application {

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
		Parent mainViewRoot = FXMLLoader.load(getClass().getResource("/resources/views/canvas_view.fxml"));
		Scene scene = new Scene(mainViewRoot);
		return scene;
	}
}
