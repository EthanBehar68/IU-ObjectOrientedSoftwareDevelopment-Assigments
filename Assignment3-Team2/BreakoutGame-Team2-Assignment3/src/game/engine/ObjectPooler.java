/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package game.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import breakout.Ball;
import breakout.Paddle;
import breakout.Text;
import breakout.Brick;
import breakout.DigitalTimer;
import composite.pattern.Savable;

public class ObjectPooler implements Savable {

	private ArrayList<DrawObject> allObjects;
	private int currentId = 0;

	public ObjectPooler() {
		allObjects = new ArrayList<DrawObject>();
	}

	public void restartFromLoadFile() {
		dumpLists();
	}

	public void reset() {
		for (DrawObject object : allObjects) {
			object.reset();
		}
	}

	private void dumpLists() {
		allObjects.clear();
		allObjects = null;
		allObjects = new ArrayList<DrawObject>();
	}

	/*
	 * This method should only be used by Json Serailizers! Pass iterators to Gson
	 * doesn't work unfortunately.
	 */
	public ArrayList<DrawObject> getObjectsForSerialization() {
		return allObjects;
	}

	/*
	 * This method should only be used by the Json Deserializers!
	 */
	public void registerDrawObjectFromDeserializer(DrawObject object, int id) {
//		System.out.println("Adding " + object.toString());
		// Check if an object with this ID already exists
		// If so this is a problem so stop operation
		ArrayList<DrawObject> otherObjects = (ArrayList<DrawObject>) allObjects.stream()
				.filter(obj -> obj.getId() == id).collect(Collectors.toList());

		if (otherObjects.size() != 0) {
			throw new UnsupportedOperationException("Cannot add object " + object.toString() + " with id " + id
					+ " to object pool because another object exists with that id.");
		}

		// If not, add it and update id
		allObjects.add(object);
		checkCurrentId(id);
	}

	public Iterator<DrawObject> getDrawObjectIterator() {
		return allObjects.iterator();
	}

	public Iterator<DrawObject> getIteratorForObjectsOfType(Class<?> objectType) {
		return allObjects.stream().filter(p -> p.getClass() == objectType).collect(Collectors.toList()).iterator();
	}

	public DrawObject getObjectById(int id) {
		for (DrawObject object : allObjects) {
			if (object.getId() == id) {
				return object;
			}
		}
		throw new IndexOutOfBoundsException("Object with id " + id + " was not found.");
	}

	public DrawObject createObject(Class<?> objectType) {
		DrawObject createdObject = createObjectWithId(objectType, currentId);
		currentId++;
		return createdObject;
	}

	public DrawObject returnOrCreateObjectWithId(Class<?> objectType, int id) {
		// We have created this object and know it so return it
		for (DrawObject object : allObjects) {
			if (object.getClass() == objectType && object.getId() == id) {
				return object;
			}
		}

		// We have no record of objectType associated with id so let's create it
		DrawObject createdObject = createObjectWithId(objectType, currentId);
		checkCurrentId(id);
		return createdObject;
	}

	public DrawObject createObjectWithId(Class<?> objectType, int id) {
		if (objectType == Ball.class) {
			Ball newBall = new Ball();
			newBall.setId(id);
			allObjects.add(newBall);
			return newBall;
		} else if (objectType == Paddle.class) {
			Paddle newPaddle = new Paddle();
			newPaddle.setId(id);
			allObjects.add(newPaddle);
			return newPaddle;
		} else if (objectType == Brick.class) {
			Brick newBrick = new Brick();
			newBrick.setId(id);
			allObjects.add(newBrick);
			return newBrick;
		} else if (objectType == DigitalTimer.class) {
			DigitalTimer newTimer = new DigitalTimer();
			newTimer.setId(id);
			allObjects.add(newTimer);
			return newTimer;
		} else if (objectType == Text.class) {
			Text newText = new Text();
			newText.setId(id);
			allObjects.add(newText);
			return newText;
		} else {
			throw new UnsupportedOperationException("ObjectPooler cannot create class " + objectType.toString());
		}
	}

	private void checkCurrentId(int id) {
		if (id >= currentId) {
			currentId = id + 1;
		}
	}

	/************************************
	 * Savable Implementations A Composite Savable No need for Add/Remove b/c
	 * children savables are added from elsewhere
	 ************************************/
	@Override
	public String save(boolean encloseMyself) {
		StringBuilder sb = new StringBuilder();

		if (encloseMyself) {
			sb.append("{");
		}

		sb.append("\"objectPooler\":{");
		sb.append("\"allObjects\":[");
		Iterator<DrawObject> objectIterator = allObjects.iterator();
		while (objectIterator.hasNext()) {
			DrawObject next = objectIterator.next();

			sb.append(next.save(true));

			if (objectIterator.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("]}");

		if (encloseMyself) {
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public void load(JsonObject jsonObject) {
		for (JsonElement item : jsonObject.get("allObjects").getAsJsonArray()) {
			// Cast JsonElement to JsonObject - easier to work with.
			JsonObject object = item.getAsJsonObject();

			// Get Class string so we know what we making
			String typeAsString = object.get("class").getAsString();

			// Create DrawObject
			DrawObject drawObject = deserializeDrawable(typeAsString, object);

			// Send em to ObjectPooler
			registerDrawObjectFromDeserializer(drawObject, drawObject.getId());
		}

	}

	public static DrawObject deserializeDrawable(String typeAsString, JsonObject jsonObject) {
		if (typeAsString.compareToIgnoreCase("breakout.Ball") == 0) {
			Ball ball = new Ball();
			ball.load(jsonObject);
			return ball;
		} else if (typeAsString.compareToIgnoreCase("breakout.Brick") == 0) {
			Brick brick = new Brick();
			brick.load(jsonObject);
			return brick;
		} else if (typeAsString.compareToIgnoreCase("breakout.Paddle") == 0) {
			Paddle paddle = new Paddle();
			paddle.load(jsonObject);
			return paddle;
		} else if (typeAsString.compareToIgnoreCase("breakout.DigitalTimer") == 0) {
			DigitalTimer digitalTimer = new DigitalTimer();
			digitalTimer.load(jsonObject);
			return digitalTimer;
		} else if (typeAsString.compareToIgnoreCase("breakout.Text") == 0) {
			Text text = new Text();
			text.load(jsonObject);
			return text;
		} else {
			throw new UnsupportedOperationException("Implement serializer for command: " + typeAsString);
		}
	}

	@Override
	public void addSavable() {
		throw new UnsupportedOperationException("This action is completed by the constructor. Please avoid using it.");
	}

	@Override
	public void removeSavable() {
		throw new UnsupportedOperationException("This action is completed by the constructor. Please avoid using it.");
	}
}
