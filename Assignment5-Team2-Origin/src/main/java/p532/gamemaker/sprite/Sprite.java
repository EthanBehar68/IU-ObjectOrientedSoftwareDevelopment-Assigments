package p532.gamemaker.sprite;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import p532.gamemaker.command.MoveCommands;
import p532.gamemaker.controllers.ParentWindow;
import p532.gamemaker.playthegame.KeyEventObserver;
import p532.gamemaker.playthegame.TickObserver;
import p532.gamemaker.sprite.conditions.CollisionCondition;
import p532.gamemaker.sprite.conditions.KeyEventCondition;
import p532.gamemaker.strategies.DoNothingStrategy;
import p532.gamemaker.strategies.GeneralStrategy;
import p532.gamemaker.strategies.collision.CollisionType;
import p532.gamemaker.strategies.collision.DestroySelfStrategy;
import p532.gamemaker.strategies.design.SelectSpriteStrategy;
import p532.gamemaker.strategies.display.SetClockTextStrategy;

import java.util.ArrayList;
import java.util.ListIterator;


public class Sprite implements TickObserver, KeyEventObserver
{
	//How the Sprite is displayed
	//Ex: a GameLabel view makes the Sprite display as text.
	private SpriteView view;

	private String userDefinedType = "";
	private double velocityX = 0;
	private double velocityY = 0;
	private boolean isDestroyed = false; //TODO remove -- it's unused

	//private Sound onHitSomethingSound; TODO
	//private Sound onGetHitSound; TODO

	//Key event handlers (strategies)
	private ArrayList<CollisionCondition> onHitSomethingConditionList = new ArrayList<>();
	private ArrayList<CollisionCondition> onGetHitConditionList = new ArrayList<>();
	private ArrayList<KeyEventCondition> onKeyPressConditionList = new ArrayList<>();

	//Mouse event handlers (strategies)
	private GeneralStrategy mouseClickStrategy = DoNothingStrategy.instance;
	private GeneralStrategy mouseEnterStrategy = DoNothingStrategy.instance;
	private GeneralStrategy mouseExitStrategy = DoNothingStrategy.instance;

	//onTick -- This may vary based on the SpriteView concrete type
	//(example strategy: changing the GameLabel text to reflect the current time)
	private GeneralStrategy onTickStrategy = DoNothingStrategy.instance;


	public Sprite() {
	}


	/**
	 * A "constructor" that creates a new Sprite
	 * with default properties.
	 * @return a new Sprite with a GameCircle view
	 */
	public static Sprite defaultSprite()
	{
		Sprite sprite = new Sprite();
		sprite.view = new GameCircle();
		sprite.view.setSpriteWidth(10);
		sprite.view.setLayoutX(100);
		sprite.view.setLayoutY(100);
		sprite.view.setFill(Color.GREEN);

		//Add listeners
		sprite.setupSpriteForEditor();

		return sprite;
	}


	//////////////// Getters & Setters ////////////////

	public SpriteView getView() {
		return view;
	}

	public void setView(SpriteView view) {
		this.view = view;
	}

	public String getUserDefinedType() {
		return userDefinedType;
	}

	/**
	 * Sets the sprite's type and updates the list of all Sprite types
	 * for the current level.
	 */
	public void setUserDefinedType(String userDefinedType) {
		this.userDefinedType = userDefinedType;
		//Update the list of all sprite types
		//NOT Observer Pattern, but oh well
		ParentWindow.getGameDesignScene().getSpritePresenter().getLevel().updateUserDefinedTypesList();
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean destroyed) {
		isDestroyed = destroyed;
	}

	public ArrayList<CollisionCondition> getOnHitSomethingConditionList() {
		return onHitSomethingConditionList;
	}

	public void setOnHitSomethingConditionList(ArrayList<CollisionCondition> onHitSomethingConditionList) {
		this.onHitSomethingConditionList = onHitSomethingConditionList;
	}

	public ArrayList<CollisionCondition> getOnGetHitConditionList() {
		return onGetHitConditionList;
	}

	public void setOnGetHitConditionList(ArrayList<CollisionCondition> onGetHitConditionList) {
		this.onGetHitConditionList = onGetHitConditionList;
	}

	public ArrayList<KeyEventCondition> getOnKeyPressConditionList() {
		return onKeyPressConditionList;
	}

	public void setOnKeyPressConditionList(ArrayList<KeyEventCondition> onKeyPressConditionList) {
		this.onKeyPressConditionList = onKeyPressConditionList;
	}

	public GeneralStrategy getMouseClickStrategy() {
		return mouseClickStrategy;
	}

	public void setMouseClickStrategy(GeneralStrategy mouseClickStrategy) {
		this.mouseClickStrategy = mouseClickStrategy;
	}

	public GeneralStrategy getMouseEnterStrategy() {
		return mouseEnterStrategy;
	}

	public void setMouseEnterStrategy(GeneralStrategy mouseEnterStrategy) {
		this.mouseEnterStrategy = mouseEnterStrategy;
	}

	public GeneralStrategy getMouseExitStrategy() {
		return mouseExitStrategy;
	}

	public void setMouseExitStrategy(GeneralStrategy mouseExitStrategy) {
		this.mouseExitStrategy = mouseExitStrategy;
	}

	public GeneralStrategy getOnTickStrategy() {
		return onTickStrategy;
	}

	public void setOnTickStrategy(GeneralStrategy onTickStrategy) {
		this.onTickStrategy = onTickStrategy;
	}

	//////////////// Convenience Methods from the view ////////////////

	public double getX()
	{
		return this.view.getLayoutX();
	}

	public void setX(double value)
	{
		this.view.setLayoutX(value);
	}

	public double getY()
	{
		return this.view.getLayoutY();
	}

	public void setY(double value)
	{
		this.view.setLayoutY(value);
	}

	////////////////////////////////////////////////////////////////








	/**
	 * Adds mouse listeners so that the Sprite can be selected
	 * while editor mode.
	 */
	public void setupSpriteForEditor()
	{
		//Add click listener so that the Sprite can be selected
		view.getNode().setOnMouseClicked(e -> {
			handleMouseClickInEditor();
		});

		//Add drag-and-drop behavior to the Sprite's Node
		//TODO this needs to be fixed
		//DragController dragController = new DragController(this.getView().getNode(), true);
	}

	/**
	 * Adds mouse listeners to the Sprite that
	 * only trigger in-game.
	 */
	public void setupSpriteForGamePlay()
	{
		//Add click listener to the Sprite
		view.getNode().setOnMouseClicked(e -> {
			handleMouseClickInGame();
		});
		//Add mouse enter listener to the Sprite
		view.getNode().setOnMouseEntered(e -> {
			handleMouseEnterInGame();
		});
		//Add mouse exit listener to the Sprite
		view.getNode().setOnMouseExited(e -> {
			handleMouseExitInGame();
		});
	}



	//////////////// Event Handlers ////////////////

	@Override
	public void onTick()
	{
		//Adjust position based on velocity (TEMP: should probably be moved)
		view.setLayoutX(view.getLayoutX() + velocityX);
		view.setLayoutY(view.getLayoutY() + velocityY);

		//execute all commands from the move list
		//movementBacklog.execute();

		//Perform user-defined behavior
		onTickStrategy.execute(this);
	}

	/**
	 * Activates this Sprite's onHitSomethingBehavior when it collides with
	 * another Sprite.
	 * @param impactee the Sprite that was hit by this Sprite.
	 * @param collisionType where the collider (this) hit the impactee;
	 *                         e.g. on the impactee's top edge
	 */
	public void onHitSomething(Sprite impactee, CollisionType collisionType)
	{
		//Cycle through each condition in the list.
		ListIterator<CollisionCondition> iterator = onHitSomethingConditionList.listIterator();
		while (iterator.hasNext())
		{
			//If the condition is satisfied, perform its strategy
			CollisionCondition condition = iterator.next();
			condition.attemptCollisionBehavior(this, impactee, collisionType);
		}
	}

	/**
	 * Activates this Sprite's onGetHitBehavior when another Sprite
	 * collides with it.
	 * @param collider the Sprite that hit this Sprite.
	 * @param collisionType where the collidee hit the impactee (this);
	 * 	 *                         e.g. on the impactee's top edge
	 */
	public void onGetHit(Sprite collider, CollisionType collisionType)
	{
		//Cycle through each condition in the list.
		ListIterator<CollisionCondition> iterator = onGetHitConditionList.listIterator();
		while (iterator.hasNext())
		{
			//If the condition is satisfied, perform its strategy
			CollisionCondition condition = iterator.next();
			condition.attemptCollisionBehavior(this, collider, collisionType);
		}
	}

	/**
	 * Activates the Sprite's onDestroyBehavior, triggering
	 * some other action. This does not rely on sprite.isDestroyed().
	 */
	public void onDestroy()
	{
		//TODO this is optional to implement
	}

	/**
	 * When any key event happens, this decides if and how the Sprite
	 * will respond. Each of the user-defined KeyEventConditions in this Sprite
	 * is checked. If the condition's keyCode matches the input keyCode, the
	 * strategy inside the condition is fired.
	 * @param keyCode
	 */
	@Override
	public void notifyOfKeyPressed(KeyCode keyCode)
	{
		//Cycle through each condition in the list.
		for (KeyEventCondition condition : onKeyPressConditionList)
		{
			//If the condition is satisfied, perform its strategy
			condition.attemptBehavior(this, keyCode);
		}
	}


	/**
	 * Performs a certain action when the Sprite is clicked
	 * on while in the editor. Example: all the Sprite's
	 * properties are rendered in a list.
	 */
	private void handleMouseClickInEditor()
	{
		SelectSpriteStrategy.execute(this); //this isn't really the strategy pattern, but we can change it if needed
	}

	private void handleMouseClickInGame() {
		mouseClickStrategy.execute(this);
	}

	private void handleMouseEnterInGame() {
		mouseEnterStrategy.execute(this);
	}

	private void handleMouseExitInGame() {
		mouseExitStrategy.execute(this);
	}
}