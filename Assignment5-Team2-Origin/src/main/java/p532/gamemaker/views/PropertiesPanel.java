package p532.gamemaker.views;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import p532.gamemaker.controllers.PropertiesPanelController;
import p532.gamemaker.sprite.PropertyType;
import p532.gamemaker.sprite.UpdateSpriteAdapter;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.utility.ParseNumberUtility;

import java.io.IOException;
import java.util.List;

/**
 * Adds and removes "property components" (for example, a label and a text view)
 * from the properties scroll area.
 */
public class PropertiesPanel
{
    private GameDesignScene scene;
    private Parent parentPanel;
    private VBox vboxAllProperties;
    public static final String FXML_PATH = "properties-panel.fxml";

    private VBox vboxStandardProperties;
    private VBox vboxOnKeyPressConditions;
    private VBox vboxOnHitSomethingConditions;
    private VBox vboxOnGetHitConditions;
    private VBox vboxMouseEvents;

    ////////////////////////////////////////////////////////////////////////

    public PropertiesPanel(GameDesignScene scene)
    {
        this.scene = scene;
        reset(scene);
    }

    ////////////////////////////////////////////////////////////////////////

    public VBox getVboxAllProperties() {
        return vboxAllProperties;
    }

    public VBox getVboxStandardProperties() {
        return vboxStandardProperties;
    }

    public VBox getVboxOnKeyPressConditions() {
        return vboxOnKeyPressConditions;
    }

    public VBox getVboxOnHitSomethingConditions() {
        return vboxOnHitSomethingConditions;
    }

    public VBox getVboxOnGetHitConditions() {
        return vboxOnGetHitConditions;
    }

    public VBox getVboxMouseEvents() {
        return vboxMouseEvents;
    }

    ////////////////////////////////////////////////////////////////////////

    private void reset(GameDesignScene scene)
    {
        try
        {
            //1. Create the properties panel
            parentPanel = FXMLLoader.load(getClass().getClassLoader().getResource(FXML_PATH));

            //2. Select the node that the properties panel will be placed into
            ScrollPane panePropertiesPanelRoot = (ScrollPane) scene.lookup("#scrollPropertiesRoot");

            //3. Select the VBoxes that the property components will be placed into
            vboxAllProperties = (VBox) parentPanel.lookup("#vboxAllProperties");
            vboxStandardProperties = (VBox) parentPanel.lookup("#vboxStandardProperties");
            vboxOnKeyPressConditions = (VBox) parentPanel.lookup("#vboxOnKeyPressConditions");
            vboxOnHitSomethingConditions = (VBox) parentPanel.lookup("#vboxOnHitSomethingConditions");
            vboxOnGetHitConditions = (VBox) parentPanel.lookup("#vboxOnGetHitConditions");
            vboxMouseEvents = (VBox) parentPanel.lookup("#vboxMouseEvents");

            //4. Add click events to all buttons
            Button btnAddOnKeyPressBehavior = (Button) parentPanel.lookup("#btnAddOnKeyPressBehavior");
            btnAddOnKeyPressBehavior.setOnAction(PropertiesPanelController.addOnKeyPressBehavior);
            Button btnAddOnHitSomethingBehavior = (Button) parentPanel.lookup("#btnAddOnHitSomethingBehavior");
            btnAddOnHitSomethingBehavior.setOnAction(PropertiesPanelController.addOnHitSomethingBehavior);
            Button btnAddOnGetHitBehavior = (Button) parentPanel.lookup("#btnAddOnGetHitBehavior");
            btnAddOnGetHitBehavior.setOnAction(PropertiesPanelController.addOnGetHitBehavior);

            //5. Add the properties panel to the editor scene
            panePropertiesPanelRoot.setContent(parentPanel);
        }
        catch (IOException e) {
            System.out.println("Failed to load in " + FXML_PATH);
            e.printStackTrace();
        }
    }


    /**
     * Given an enum `propertyType`, returns the correct VBox container
     * that components relevant to that property group should be added to.
     * @param propertyType the group of the property
     * @return a specific VBox or, by default, vboxAllProperties.
     */
    private VBox selectPropertyContainer(PropertyType propertyType)
    {
        switch (propertyType)
        {
            case StandardPropertyComponent:
                return vboxStandardProperties;
            case OnKeyPressConditionComponent:
                return vboxOnKeyPressConditions;
            case OnHitSomethingConditionComponent:
                return vboxOnHitSomethingConditions;
            case OnGetHitConditionComponent:
                return vboxOnGetHitConditions;
            case MouseEventComponent:
                return vboxMouseEvents;
            default:
                return vboxAllProperties; //default to parent
        }
    }


    public void addStringPropertyComponent(String labelText, String initialValue, PropertyType propertyType, UpdateSpriteAdapter updateSpriteAdapter)
    {
        try
        {
            //Add a property component for integer input
            VBox textEntryComponent = FXMLLoader.load(getClass().getClassLoader().getResource("textfield-property-component.fxml"));
            VBox destination = selectPropertyContainer(propertyType);
            destination.getChildren().add(textEntryComponent);

            //Set the label text
            Label labelPropertyName = (Label) textEntryComponent.lookup("#lblPropertyName");
            labelPropertyName.textProperty().set(labelText);

            //Set the initial value of the text field
            TextField textField = (TextField) textEntryComponent.lookup("#txtPropertyEntry");
            textField.textProperty().set(initialValue);

            //When the text field changes, update the sprite
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                updateSpriteAdapter.updateStringField(newValue);
            });
        }
        catch (IOException | NullPointerException ex) {
            //Do nothing -- just print a msg
            System.out.println("Failed to load in the XML for textfield-property-component.fxml");
            ex.printStackTrace();
        }
    }


    public void addDoublePropertyComponent(String labelText, double initialValue, PropertyType propertyType, UpdateSpriteAdapter updateSpriteAdapter)
    {
        try
        {
            //Add a property component for integer input
            VBox textEntryComponent = FXMLLoader.load(getClass().getClassLoader().getResource("textfield-property-component.fxml"));
            VBox destination = selectPropertyContainer(propertyType);
            destination.getChildren().add(textEntryComponent);

            //Set the label text
            Label labelPropertyName = (Label) textEntryComponent.lookup("#lblPropertyName");
            labelPropertyName.textProperty().set(labelText);

            //Set the initial value of the text field
            TextField textField = (TextField) textEntryComponent.lookup("#txtPropertyEntry");
            textField.textProperty().set(Double.toString(initialValue));

            //When the text field changes, update the sprite
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                //Parse the string input or do nothing if it is null (there was a parsing error)
                Double parsedValue = ParseNumberUtility.parseDoubleOrReturnNull(newValue);
                if (parsedValue != null)
                    updateSpriteAdapter.updateDoubleField(parsedValue);
            });
        }
        catch (IOException | NullPointerException ex) {
            //Do nothing -- just print a msg
            System.out.println("Failed to load in the XML for textfield-property-component.fxml");
            ex.printStackTrace();
        }
    }


    //This method is unused but still good!
    /*public void addComboBoxPropertyComponent(String labelText, CollisionStrategy initialValue, PropertyType propertyType, UpdateSpriteAdapter updateSpriteAdapter, CollisionStrategy... comboBoxItems)
    {
        try
        {
            //Add a property component for integer input
            VBox comboBoxComponent = FXMLLoader.load(getClass().getClassLoader().getResource("combobox-property-component.fxml"));
            VBox destination = selectPropertyContainer(propertyType);
            destination.getChildren().add(comboBoxComponent);

            //Set the label text
            Label labelPropertyName = (Label) comboBoxComponent.lookup("#lblPropertyName");
            labelPropertyName.textProperty().set(labelText);

            //Find the ComboBox
            ComboBox comboBox = (ComboBox) comboBoxComponent.lookup("#comboPropertyEntry");

            //Place the combo box items passed to this method into the combo box
            comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
            //Set the initial value of the ComboBox
            comboBox.setValue(initialValue);

            //When the user selects a new combo box item, update the sprite
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue instanceof CollisionStrategy)
                    updateSpriteAdapter.updateStrategy((CollisionStrategy) newValue);
                else
                    System.err.println("Error: Selected something that is not a CollisionStrategy from the properties combo box");
            });
        }
        catch (IOException | NullPointerException ex) {
            //Do nothing -- just print a msg
            System.out.println("Failed to load in the XML for combobox-property-component.fxml");
            ex.printStackTrace();
        }
    }*/



    public void addComboBoxPropertyComponent(String labelText, GeneralStrategy initialValue, PropertyType propertyType, UpdateSpriteAdapter updateSpriteAdapter, List<GeneralStrategy> comboBoxItems)
    {
        try
        {
            //Add a property component for integer input
            VBox comboBoxComponent = FXMLLoader.load(getClass().getClassLoader().getResource("combobox-property-component.fxml"));
            VBox destination = selectPropertyContainer(propertyType);
            destination.getChildren().add(comboBoxComponent);

            //Set the label text
            Label labelPropertyName = (Label) comboBoxComponent.lookup("#lblPropertyName");
            labelPropertyName.textProperty().set(labelText);

            //Find the ComboBox
            ComboBox comboBox = (ComboBox) comboBoxComponent.lookup("#comboPropertyEntry");

            //Place the combo box items passed to this method into the combo box
            comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
            //Set the initial value of the ComboBox
            comboBox.setValue(initialValue);

            //When the user selects a new combo box item, update the sprite
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue instanceof GeneralStrategy)
                    updateSpriteAdapter.updateStrategy((GeneralStrategy) newValue);
                else
                    System.err.println("Error: Selected something that is not a GeneralStrategy from a properties combo box");
            });
        }
        catch (IOException | NullPointerException ex) {
            //Do nothing -- just print a msg
            System.out.println("Failed to load in the XML for combobox-property-component.fxml");
            ex.printStackTrace();
        }
    }


    /**
     * Adds a single Node to one of the containers in this panel.
     * @param node any JavaFX element; e.g. a VBox containing other Nodes
     * @param propertyType this affects which container the Node will be placed into
     */
    public void addNode(Node node, PropertyType propertyType)
    {
        //Add the component to the correct VBox
        VBox destination = selectPropertyContainer(propertyType);
        destination.getChildren().add(node);
    }



    /**
     * Removes all dynamically-added property components from the properties view
     * and reverts to the FXML template.
     */
    public void clear()
    {
        //Revert to the template layout
        reset(scene);
    }
}
