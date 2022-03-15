/**
 * @Author: Maazin Jawad
 * @CreationDate: 10/1/2021
 * @Editors:
 * @EditedDate:
 **/
package gamemaker.view.components;

import javafx.scene.layout.HBox;

public class KeyInputBehaviorConfigObject {


    public KeyInputBehaviorConfigObject(String keyInput, String action, String property, HBox input, String path) {
        this.keyInput = keyInput;
        this.action = action;
        this.property = property;
        this.input = input;
        this.path = path;
    }

    String keyInput, action, property;
    HBox input;
    String path;

    public String getKeyInput() {
        return keyInput;
    }

    public String getAction() {
        return action;
    }

    public String getProperty() {
        return property;
    }

    public HBox getInput() {
        return input;
    }
}
