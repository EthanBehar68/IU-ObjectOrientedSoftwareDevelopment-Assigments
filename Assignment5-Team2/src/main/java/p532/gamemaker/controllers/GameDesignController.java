package p532.gamemaker.controllers;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Scene;
import p532.gamemkaer.game.Game;

public class GameDesignController {

	private Scene scene;
	private List<Game> gameObjects;

	public GameDesignController(Scene scene) {
		this.scene = scene;
		gameObjects = new LinkedList<>();
	}

	public Scene getScene() {
		return scene;
	}

	public void addGameObjects(Game gameobj) {
		gameObjects.add(gameobj);
	}

	public List<Game> getGameObjects() {
		return this.gameObjects;
	}

	public Game getCurrentGame() {
		return gameObjects.get(gameObjects.size() - 1);
	}
}
