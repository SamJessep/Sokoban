package nz.ac.ara.srj0070.Sokoban;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.platform.commons.util.StringUtils;

public class Level {

	public Integer targetCount;
	private String name;
	private Integer moveCount;
	private Integer width;
	private Integer height;
	protected Placeable[] [] allPlaceables;
	
	protected Map<Character, Class<? extends Placeable>> PlaceableMap = Map.of(
			'#', Wall.class,
			'.', Empty.class,
			'x', Crate.class,
			'+', Target.class,
			'w', Worker.class
	);

	public Level(String name, int h, int w, String levelString) {
		this.name = name;
		width = w;
		height = h;
		targetCount = countTargets(levelString);
		allPlaceables = toPlaceableArray(levelString);
		moveCount = 0;
	}
	
	private Character getKey(Class<? extends Placeable> c) {
		for(Entry<Character, Class<? extends Placeable>> entry : PlaceableMap.entrySet()) {
			if(entry.getValue().equals(c)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public String toString() {
		String returnString = name + "\n";
		for(Placeable[] row : allPlaceables) {
			for(Placeable cell : row) {
				returnString += getKey(cell.getClass());
			}
			returnString += "\n";
		}
		returnString += "move " + moveCount +"\n";
		returnString += "completed "+getCompletedCount()+" of "+ targetCount+"\n";
		
		return returnString;
	}
	
	private Placeable[][] toPlaceableArray(String levelString) {
		Placeable[] [] pieces = new Placeable[height][width];
		int row = 0;
		int col = 0;
		for(int c = 0; c<levelString.length(); c++) {
			if(c%width == 0 &&  c != 0) {
				col = 0;
				row++;
			}
			pieces[row][col] = convertToClass(levelString.charAt(c),row, col);
			col++;
		}
		
		return pieces;
	}
	
	private Placeable convertToClass(Character c, int x, int y) {
		Object[] objects = {x, y};
		try {
			return PlaceableMap.get(c).getDeclaredConstructor(new Class[] {int.class, int.class}).newInstance(objects);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return null;
	}
	
	private int countTargets(String levelString) {
		return levelString.length() - levelString.replace("x", "").length();
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getMoveCount() {
		return moveCount;
	}

	public String getName() {
		return name;
	}
	

	public Integer getCompletedCount() {
		int count = 0;
		for(Placeable[] row : allPlaceables) {
			for(Placeable cell : row) {
				if(cell.getClass() == Target.class) {
					Target t = (Target)cell;
					t.isCompleted();
				}
			}
		}
		return count;
	}

}
