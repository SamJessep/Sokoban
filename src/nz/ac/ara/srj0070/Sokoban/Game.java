package nz.ac.ara.srj0070.Sokoban;

import java.util.ArrayList;
import java.util.List;

public class Game {
	List<Level> allLevels = new ArrayList<Level>();
	Level currentLevel;

	public String toString() {
		String stringVersion = "no levels";
		if(currentLevel != null) {	
			stringVersion = currentLevel.getName();
		}
		return stringVersion;
	}
	
	public void addLevel(String name, int w, int h, String levelString) {
		Level level = new Level(name, w, h, levelString);
		allLevels.add(level);
		currentLevel = level;
	}

	public void move(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	public int getLevelCount() {
		return allLevels.size();
	}

	public String getCurrentLevelName() {
		return  currentLevel == null ? "no levels" : currentLevel.getName();
	}

	public List<String> getLevelNames() {
		List<String> names = new ArrayList<String>();
		for(Level level : allLevels) {
			names.add(level.getName());
		}
		return names;
	}

}
