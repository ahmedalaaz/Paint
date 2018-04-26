
package plugin.triangle;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;

public class TriangleController extends AnchorPane implements CommandPane {

	private String toolName;
	final private String TOOL = "triangle";
	@FXML
	private JFXButton triangleButton;
	private CustomTriangle triangle;
	private double triangleStartX;
	private double triangleStartY;

	@Override
	public void execute(Object canvas, MouseEvent event) {
		if (event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			triangle = new CustomTriangle(event.getX(), event.getY());
			triangle.setX2Right(event.getX() + 30);
			triangle.setY2Right(event.getY() + 30);
			triangle.setX3Left(event.getX() - 30);
			triangle.setY3Left(event.getY() + 30);
			triangle.setFillColor(Paint.valueOf("#FFFFFF"));
			triangle.setColor(Paint.valueOf("#000000"));
			triangleStartX = event.getX();
			triangleStartY = event.getY();
			Main.getController().addShape(triangle);
			;
			triangle.draw(canvas);
		}
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			double topx = (event.getX() - triangleStartX) / 2;
			if (event.getX() <= 3 || event.getX() >= triangle.getParent().getBoundsInLocal().getWidth() - 3)
				return;
			if (triangleStartX <= 3 || triangleStartX >= triangle.getParent().getBoundsInLocal().getWidth() - 3)
				return;
			if (triangleStartY <= 3 || triangleStartY >= triangle.getParent().getBoundsInLocal().getHeight() - 3)
				return;
			if (event.getY() <= 3 || event.getY() >= triangle.getParent().getBoundsInLocal().getHeight() - 3)
				return;

			triangle.RemoveAllPoints();
			triangle.setX1Top(event.getX() - topx);
			triangle.setY1Top(triangleStartY);
			triangle.setX2Right(event.getX());
			triangle.setY2Right(event.getY());
			triangle.setX3Left(triangleStartX);
			triangle.setY3Left(event.getY());

		}
	}

	public TriangleController() {
		this.toolName = "Triangle";
		UILoader uiLoader;
		try {
			uiLoader = new UILoader(new URL("file:///" + System.getProperty("user.dir") + File.separator + "src"
					+ File.separator + "plugin" + File.separator + TOOL + File.separator + "triangle.fxml"), this);
			uiLoader.load();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return this.toolName;
	}

	@Override
	public void setAction(EventHandler<ActionEvent> value) {
		triangleButton.setOnAction(value);

	}

	@Override
	public void triggerState(ActionEvent event) {

	}

	@Override
	public void pauseState(ActionEvent event) {
	}

	@Override
	public Class<? extends Shape> getToolClass() {
		return CustomTriangle.class;
	}

	@Override
	public void triggerState() {
		// TODO Auto-generated method stub

	}

}
