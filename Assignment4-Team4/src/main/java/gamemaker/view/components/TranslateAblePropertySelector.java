package gamemaker.view.components;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;

public class TranslateAblePropertySelector extends MenuButton {
    private static List<String> properties = Arrays.asList("Velocity");

    public TranslateAblePropertySelector(HBox container) {
        super("Choose Property");
        properties.forEach(property -> {
            MenuItem choice = new MenuItem(property);
            choice.setOnAction(e -> {
                container.getChildren().clear();
                this.setText(choice.getText());
                container.getChildren().add(processSelection(property));
            });
            this.getItems().add(choice);
        });
    }

    /**
     * Based on the
     * @param choice
     * @return
     */
    private Node processSelection(String choice) {
            HBox dualInput = new HBox();
            dualInput.setSpacing(5);
            TextField inputX = new TextField();
            inputX.setTooltip(new Tooltip("Enter X"));
            inputX.setPrefWidth(50);
            TextField inputY = new TextField();
            inputY.setPrefWidth(50);
            inputY.setTooltip(new Tooltip("Enter Y"));
            dualInput.getChildren().addAll(inputX,inputY);
            return dualInput;

    }
}
