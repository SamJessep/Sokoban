package nz.ac.ara.srj0070.model;

public class Placeable {
	public int x;
	public int y;
	
	Placeable (){
		
	}
	
	Placeable(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

}
