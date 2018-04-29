package paint.model;

import com.jfoenix.controls.JFXButton;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

public class AnimationAdder {
	
	public AnimationAdder() {
		
	}
	public void setRotateTransition(JFXButton button) {
		RotateTransition rotateTransitionDelete = new RotateTransition(Duration.millis(500), button);
		rotateTransitionDelete.setByAngle(90f);
		rotateTransitionDelete.setCycleCount(3);
		rotateTransitionDelete.setRate(3);
		rotateTransitionDelete.setAutoReverse(true);

		SequentialTransition sequentialTransitionDelete = new SequentialTransition();
		sequentialTransitionDelete.getChildren().addAll(rotateTransitionDelete);
		sequentialTransitionDelete.setCycleCount(Timeline.INDEFINITE);
		sequentialTransitionDelete.setAutoReverse(true);
		button.setOnMouseEntered(e -> sequentialTransitionDelete.play());
		button.setOnMouseExited(e -> {

			sequentialTransitionDelete.stop();
		});
		sequentialTransitionDelete.statusProperty().addListener(new ChangeListener<Status>() {

			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {

				if (newValue == Status.STOPPED) {

					RotateTransition transition = new RotateTransition(Duration.seconds(2), button);

					transition.setFromAngle(button.getRotate());
					transition.setToAngle(0);
					transition.setCycleCount(1);
					transition.setRate(3);
					transition.setAutoReverse(true);
					transition.play();

				}

			}

		});
	}
	public void addMainIconAnimation(JFXButton button) {

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), button);
		translateTransition.setFromX(0);
		translateTransition.setToX(button.getLayoutX() + 120);
		translateTransition.setCycleCount(2);
		translateTransition.setAutoReverse(true);

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), button);
		rotateTransition.setByAngle(360f);
		rotateTransition.setCycleCount(2);
		rotateTransition.setAutoReverse(true);

		SequentialTransition sequentialTransition = new SequentialTransition();
		sequentialTransition.getChildren().addAll(translateTransition, rotateTransition);
		sequentialTransition.setCycleCount(Timeline.INDEFINITE);
		sequentialTransition.setAutoReverse(true);
		sequentialTransition.play();

		
	}
	public void addShapeIconAnimation(JFXButton button) {
		TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(500), button);
            translateTransition.setFromX(0);
            translateTransition.setToX(button.getLayoutX() + 15);
            translateTransition.setCycleCount(1);
            translateTransition.setRate(1);
            translateTransition.setAutoReverse(true);

    SequentialTransition sequentialTransition = new SequentialTransition();
    sequentialTransition.getChildren().addAll(
            translateTransition);
    sequentialTransition.setCycleCount(Timeline.INDEFINITE);
    sequentialTransition.setAutoReverse(true);
    	
    button.setOnMouseEntered(e -> sequentialTransition.play());
    button.setOnMouseExited(e -> {
			
		sequentialTransition.stop();
		});
    sequentialTransition.statusProperty().addListener(new ChangeListener<Status>() {

		@Override
		public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {

			if (newValue == Status.STOPPED) {

				TranslateTransition transition = new TranslateTransition(Duration.seconds(2), button);

				transition.setFromX(button.getTranslateX());
				transition.setToX(0);
				transition.setCycleCount(1);
				transition.setRate(3);
				transition.setAutoReverse(true);
				transition.play();

			}

		}

	});
    
	}
}
