package paint.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public interface CommandPane {

    void execute(Object canvas, MouseEvent event);
    String getName();
    void setAction(EventHandler<ActionEvent> value);
    void triggerState(ActionEvent event);
    void pauseState(ActionEvent event);
    Class<?> getToolClass();
}
