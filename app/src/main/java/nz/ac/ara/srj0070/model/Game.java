package nz.ac.ara.srj0070.model;

import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.srj0070.model.interfaces.IGame;

public class Game implements IGame {
	private List<Level> allLevels = new ArrayList<>();
	Level currentLevel;

	public String toString() {
		String stringVersion = "no levels";
		if(currentLevel != null) {	
			//stringVersion = currentLevel.getName() + "\n";
			stringVersion = currentLevel.toString();
		}
		return stringVersion;
	}

	public void addLevel(String name, int h, int w, String levelString) {
		Level level = new Level(name, h, w, levelString);
		allLevels.add(level);
		currentLevel = level;
	}

	public void loadLevel(Level level) {
		currentLevel = level;
	}
	
	public void move(Direction direction) {
		Worker worker = currentLevel.getWorker();
		int newX = worker.x+direction.GetX();
		int newY = worker.y + direction.GetY();
		Container cell = (Container)currentLevel.getPlaceable(worker.x, worker.y);
		Placeable nextCell = currentLevel.getPlaceable(newX, newY);
		if(canMove(direction, worker)) {
			cell.clearOnTopObject();
			if(((Container)nextCell).objectOnTop instanceof Crate) {
				Placeable nextNextCell = (currentLevel.getPlaceable(newX+direction.GetX(), newY+direction.GetY()));
				((Container)nextNextCell).addCrate((Crate)((Container)nextCell).objectOnTop);
			}
			cell = (Container)nextCell;
			cell.addWorker(worker);
			worker.setPosition(newX, newY);
			currentLevel.addMove();
		}
	}
	
	public Boolean canMove(Direction dir, Placeable obj) {
		Boolean result = false;
		int x = obj.x + dir.GetX();
		int y = obj.y + dir.GetY();
		Placeable nextCell = currentLevel.getPlaceable(x,y);
		if(nextCell instanceof Container) {
			if(((Container) nextCell).isEmpty() || canMove(dir, nextCell)){
				result = !(obj instanceof Container) || !(((Container) obj).objectOnTop instanceof Crate) || !(((Container) nextCell).objectOnTop instanceof Crate);
			}
			
		}
		return result;
	}

	public int getLevelCount() {
		return allLevels.size();
	}

	public String getCurrentLevelName() {
		return  currentLevel == null ? "no levels" : currentLevel.getName();
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}

	public List<Level> getLevels() {
		return allLevels;
	}

	public List<String> getLevelNames() {
		List<String> names = new ArrayList<String>();
		for(Level level : allLevels) {
			names.add(level.getName());
		}
		return names;
	}

}
