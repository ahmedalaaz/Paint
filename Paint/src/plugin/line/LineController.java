
package plugin.line;

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
import paint.model.AnimationAdder;
import paint.model.CommandPane;
import paint.model.Shape;
import paint.view.Main;
import paint.view.UILoader;



public class LineController extends AnchorPane implements CommandPane  {

        private String toolName;
	final private String TOOL = "line";
	@FXML
	private JFXButton lineButton;
	private CustomLine line;
    
    @Override
    public void execute(Object canvas, MouseEvent event) {
        if(event.getEventType() == (MouseEvent.MOUSE_PRESSED)) {
			line = new CustomLine(event.getX(), event.getY(),event.getX()+20,event.getY()+20);
			line.setFillColor(Paint.valueOf("#000000"));
			line.setColor(Paint.valueOf("#000000"));
			line.setStrokeWidth(6);
			Main.getController().addShape(line);;
			line.draw(canvas);
		}
        if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
	
             if(event.getX() <= 3 || event.getX() >= line.getParent().getBoundsInLocal().getWidth()-3)return;
             if(event.getY() <= 3 || event.getY() >= line.getParent().getBoundsInLocal().getHeight()-3)return;
            line.setEndX(event.getX());
            line.setEndY(event.getY());
        } 
    }

    public LineController() {
        this.toolName = "Line";
	        UILoader uiLoader;
			try {
				uiLoader = new UILoader( new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "plugin" + File.separator + TOOL + File.separator + "line.fxml"),this);
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
         lineButton.setOnAction(value);
         AnimationAdder animation =  new AnimationAdder();
         animation.addShapeIconAnimation(lineButton);
    }

    @Override
    public void triggerState(ActionEvent event) {
        
    }

    @Override
    public void pauseState(ActionEvent event) {
    }

    @Override
    public Class<? extends Shape> getToolClass() {
        return CustomLine.class;
    }

	@Override
	public void triggerState() {
		// TODO Auto-generated method stub
		
	}

    
    
}
