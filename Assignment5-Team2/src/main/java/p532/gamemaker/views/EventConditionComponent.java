/**
 * @Author: Ethan Taylor Behar
 * @CreationDate: Oct 14, 2021
 * @Editors:
 * @EditDate:
 **/
package p532.gamemaker.views;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import p532.gamemaker.Constants;
import p532.gamemaker.sprite.conditions.EventCondition;
import p532.gamemaker.strategies.GeneralStrategy;

public class EventConditionComponent {
	protected Parent parent;

	protected ComboBox<GeneralStrategy> comboChangeBehavior;
	protected Button selectSoundButton;

	protected ObservableList<GeneralStrategy> strategyOptions = FXCollections
			.observableArrayList(Constants.getAllGeneralStrategies());

	protected EventCondition condition;

	public Parent getView() {
		return parent;
	}

	public List<GeneralStrategy> getStrategyOptions() {
		return strategyOptions;
	}

	public void setStrategyOptions(ObservableList<GeneralStrategy> strategyOptions) {
		this.strategyOptions = strategyOptions;
	}

	public EventCondition getCondition() {
		return condition;
	}

	public void setCondition(EventCondition condition) {
		if (condition == null)
			throw new NullPointerException("condition cannot be null");
		this.condition = condition;
	}

	public void updateOnChangeBehavior() {
		comboChangeBehavior.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue instanceof GeneralStrategy)
				condition.setStrategy(newValue);
			else
				throw new UnsupportedOperationException("newValue is not of type GeneralStrategy");
		});
	}
}
