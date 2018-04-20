package paint.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import paint.controller.ShapesController;
import paint.view.Main;

public class Mover implements CommandPane {
	private final String toolName = "Mover";
	private double startX;
	private double startY;
	@Override
	public void execute(Object canvas, MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			startX = event.getX();
			startY = event.getY();
		}
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
           double offsetX = event.getX() - startX;
           double offsetY = event.getY() - startY;
           ShapesController.getInstance(Main.getController()).moveShapes(offsetX, offsetY);
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.toolName;
	}

	@Override
	public void setAction(EventHandler<ActionEvent> value) {
		// TODO Auto-generated method stub
	}

	

	@Override
	public Class<?> getToolClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void triggerState(ActionEvent event) {
		// TODO Auto-generated method stub
		Scene currentScene = (Scene) ((Node) event.getSource()).getScene();
		currentScene.setCursor(Cursor.MOVE);
	}

	@Override
	public void pauseState(ActionEvent event) {
		// TODO Auto-generated method stub
		Scene currentScene = (Scene) ((Node) event.getSource()).getScene();
		currentScene.setCursor(Cursor.DEFAULT);
		
	}

}
