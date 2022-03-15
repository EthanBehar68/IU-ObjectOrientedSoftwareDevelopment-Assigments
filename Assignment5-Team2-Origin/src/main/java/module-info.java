module com.example.breakout {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	


    opens p532.gamemaker to javafx.fxml;
    exports p532.gamemaker;
    exports p532.gamemaker.views;
    opens p532.gamemaker.views to javafx.fxml;
    opens  p532.gamemaker.sprite to  javafx.fxml;
    exports p532.gamemaker.sprite;
    exports p532.gamemaker.controllers;
    opens p532.gamemaker.controllers to javafx.fxml;
    exports p532.gamemaker.playthegame;
    opens p532.gamemaker.playthegame to javafx.fxml;
    exports p532.gamemaker.strategies.collision to javafx.fxml;
    opens p532.gamemaker.strategies.collision to javafx.fxml;
    exports p532.gamemaker.sprite.conditions;
    opens p532.gamemaker.sprite.conditions to javafx.fxml;
    exports p532.gamemaker.strategies to javafx.fxml;
    opens p532.gamemaker.strategies to javafx.fxml;
}