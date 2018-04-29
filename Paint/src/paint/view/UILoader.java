package paint.view;

import javafx.fxml.FXMLLoader;


import java.io.IOException;
import java.net.URL;

public class UILoader {

	private URL fxmlFileDir;
	private Object obj;

	public UILoader(URL fxmlFileDir, Object obj) {
		this.fxmlFileDir = fxmlFileDir;
		this.obj = obj;
	}

	public void load() {
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlFileDir);
		fxmlLoader.setRoot(obj);
		fxmlLoader.setController(obj);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
