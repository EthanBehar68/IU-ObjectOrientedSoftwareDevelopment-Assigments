package p532.gamemaker.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import p532.gamemaker.sprite.SpriteView;

/**
 * Re-written and implemented by Ethan Behar
 */
public class DragController {
	private SpriteView target;
	private double anchorX;
	private double anchorY;
	private double initialPositionX;
	private double initialPositionY;
	private BooleanProperty isDraggable;

	public DragController() {
		createDraggableProperty();
		setIsDraggable(false);
	}

	public void setTarget(SpriteView target) {
		this.target = target;
		setIsDraggable(true);
	}

	private void mouseClickAction(MouseEvent event) {
		if (target != null) {
			if (event.isPrimaryButtonDown()) {
				anchorX = event.getSceneX();
				anchorY = event.getSceneY();
				initialPositionX = target.getLayoutX();
				initialPositionY = target.getLayoutY();
			}
		} else {
			target = null;
		}
	}

	private void mouseDragAction(MouseEvent event) {
		if (target != null) {
			target.getNode().setTranslateX(event.getSceneX() - anchorX);
			target.getNode().setTranslateY(event.getSceneY() - anchorY);
		}
	}

	private void mouseReleaseAction(MouseEvent event) {
		if (target != null) {
			target.getNode().setLayoutX(initialPositionX + target.getNode().getTranslateX());
			target.getNode().setLayoutY(initialPositionY + target.getNode().getTranslateY());
			target.getNode().setTranslateX(0);
			target.getNode().setTranslateY(0);
		}
	}

	// This controls adding/remove EventFilters when we set isDraggable to true or
	// false. We do this when App loads and Play/Stop is pressed
	public void createDraggableProperty() {
		isDraggable = new SimpleBooleanProperty();
		isDraggable.addListener(this::setMouseEvents);
	}

	private void setMouseEvents(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (newValue) {
			ParentWindow.getGameDesignScene().setOnMousePressed(this::mouseClickAction);
			ParentWindow.getGameDesignScene().setOnMouseDragged(this::mouseDragAction);
			ParentWindow.getGameDesignScene().setOnMouseReleased(this::mouseReleaseAction);
		} else {
			ParentWindow.getGameDesignScene().setOnMousePressed(null);
			ParentWindow.getGameDesignScene().setOnMouseDragged(null);
			ParentWindow.getGameDesignScene().setOnMouseReleased(null);
		}
	}

	public boolean getIsDraggable() {
		return isDraggable.get();
	}

	public void setIsDraggable(boolean draggable) {
		isDraggable.set(draggable);
	}
}