package nz.ac.ara.srj0070.model;

import java.io.Serializable;

public class Placeable implements Serializable {
	public int x;
	public int y;

	Placeable(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

}
