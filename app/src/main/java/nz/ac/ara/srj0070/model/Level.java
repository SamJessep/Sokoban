package nz.ac.ara.srj0070.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {

	public Integer targetCount;
	private String name;
	private Integer moveCount;
	private Integer width;
	private Integer height;
	protected Placeable[] [] allPlaceables;
	private Worker worker;


	public Level(String name, int h, int w, String levelString) {
		this.name = name;
		width = w;
		height = h;
		allPlaceables = toPlaceableArray(levelString);
		targetCount = countTargets();
		moveCount = 0;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String toString() {
		String returnString = name + "\n";
		returnString += boardAsString() + "\n";
		returnString += "move " + moveCount +"\n";
		returnString += "completed "+getCompletedCount()+" of "+ targetCount+"\n";

		return returnString;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String boardAsString() {
		StringBuilder rowString = new StringBuilder();
		List<String> rows = new ArrayList<>();

		for(Placeable[] row : allPlaceables) {
			for(Placeable cell : row) {
				rowString.append(cell.toString());
			}
			rows.add(rowString.toString());
			rowString = new StringBuilder();
		}
		return String.join("\n", rows);
	}
	
	private Placeable[][] toPlaceableArray(String levelString) {
		Placeable[] [] pieces = new Placeable[height][width];
		int a = pieces.length;
		int b = pieces[0].length;
		int c = levelString.length();
		int row = 0;
		int col = 0;
		for(int index = 0; index<levelString.length(); index++) {
			if(index%width == 0 &&  index != 0) {
				col = 0;
				row++;
			}
			pieces[row][col] = makePlaceable(levelString.charAt(index),col, row);
			col++;
		}
		
		return pieces;
	}
	
	private Placeable makePlaceable(Character key, int x, int y) {
		Placeable placeable = null;
		switch(key) {
		
			case '.':
				placeable = new Empty(x,y);
				break;
			case '+':
				placeable = new Target(x,y);
				break;
			case '#':
				placeable = new Wall(x,y);
				break;
			case 'w':
				worker = new Worker(x,y);
				placeable = new Empty(x,y,worker);
				break;
			case 'W':
				worker = new Worker(x,y);
				placeable = new Target(x,y,worker);
				break;
			case 'x':
				placeable = new Empty(x,y,new Crate(x,y));
				break;
			case 'X':
				placeable = new Target(x,y,new Crate(x,y));
				break;
		}
		return placeable;
	}


	void addMove() {
		moveCount++;
	}
	
	private int countTargets() {
		int count = 0;
		for(Placeable[] row : allPlaceables) {
			for(Placeable placeable : row) {
				if(placeable instanceof Target) {
					count++;
				}
			}
		}
		return count;
	}

	Placeable getPlaceable(int x, int y) {
		return allPlaceables[y][x];
	}

	public Placeable[][] getAllPlaceables(){ return allPlaceables; }

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

	Worker getWorker() {
		return worker;
	}
	

	public Integer getCompletedCount() {
		int count = 0;
		for(Placeable[] row : allPlaceables) {
			for(Placeable cell : row) {
				if(cell instanceof Target && ((Target) cell).isCompleted()) {
					count ++;
				}
			}
		}
		return count;
	}

}
