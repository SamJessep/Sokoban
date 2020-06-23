package nz.ac.ara.srj0070.model;

import java.io.Serializable;

public enum Direction implements Serializable {
	LEFT(-1, 0),
	RIGHT(1, 0),
	UP(0, -1),
	DOWN(0, 1)
	;
	
	private final int x;
	private final int y;
	
	Direction(int dirX, int dirY){
		x = dirX;
		y = dirY;
	}
	
	public int GetX() {
		return x;
	}
	
	public int GetY() {
		return y;
	}
}
