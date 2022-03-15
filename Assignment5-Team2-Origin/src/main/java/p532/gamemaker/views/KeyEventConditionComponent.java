package p532.gamemaker.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.StrategyOptions;
import p532.gamemaker.strategies.design.GetKeyInputButtonStrategy;

import java.io.IOException;
import java.util.List;

/**
 * When this component is created, you must call setup().
 */
public class KeyEventConditionComponent
{
    private Parent parent;
    public static final String FXML_PATH = "key-event-component.fxml";

    private Button buttonChangeKeyCode;
    private ComboBox comboChangeBehavior;
    private List<Object> allStrategies;

    private KeyEventCondition condition;
    private ObservableList<Object> strategyOptions = FXCollections.observableArrayList(StrategyOptions.ALL_KEY_EVENT_STRATEGIES);


    /**
     * Required constructor for JavaFXML.
     */
    public KeyEventConditionComponent() {
    }

    ///////////////////////////////////////////////////////////

    public Parent getView() {
        return parent;
    }

    public KeyEventCondition getCondition() {
        return condition;
    }

    /**
     * Changes the active condition and updates the UI accordingly
     */
    public void setCondition(KeyEventCondition condition) {
        if (condition == null)
            throw new NullPointerException("condition cannot be null");
        this.condition = condition;
    }

    public List<Object> getStrategyOptions() {
        return strategyOptions;
    }

    public void setStrategyOptions(ObservableList<Object> strategyOptions) {
        this.strategyOptions = strategyOptions;
    }


    ///////////////////////////////////////////////////////////

    private void reset()
    {
        try
        {
            //1. Create the properties panel
            parent = FXMLLoader.load(getClass().getClassLoader().getResource(FXML_PATH));

            //2. Select the UI elements in this component
            buttonChangeKeyCode = (Button) parent.lookup("#btnChangeKeyCode");
            comboChangeBehavior = (ComboBox) parent.lookup("#comboChangeBehavior");
            assert buttonChangeKeyCode != null;
            assert comboChangeBehavior != null;

            //4. Add onChange events to each ComboBox (NOT MVC)
            comboChangeBehavior.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue instanceof GeneralStrategy)
                    condition.setStrategy((GeneralStrategy) newValue);
                else
                    System.err.println("Error: Selected something that is not a GeneralStrategy from a properties combo box");
            });
        }
        catch (IOException e) {
            System.out.println("Failed to load in " + FXML_PATH);
            e.printStackTrace();
        }
    }

    public void setup(KeyEventCondition condition, ObservableList<Object> strategyOptions)
    {
        reset();

        //Set what data should be displayed in the UI
        setCondition(condition);
        setStrategyOptions(strategyOptions);

        //Update UI...
        //Set button text
        buttonChangeKeyCode.setText(condition.getKeyCode().toString());
        //Set combo box items
        comboChangeBehavior.setItems(strategyOptions);
        comboChangeBehavior.setValue(condition.getStrategy());

        //Add key event listener to the button
        buttonChangeKeyCode.setOnAction(event -> {
            //Capture one key code
            GetKeyInputButtonStrategy.execute(buttonChangeKeyCode, condition);
        });
    }
}
