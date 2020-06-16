package nz.ac.ara.srj0070.model;

public class Empty extends Container {

	public Empty(int x, int y) {
		super(x, y);
	}
	
	public Empty(int x, int y, Worker worker) {
		super(x, y, worker);
	}
	
	public Empty(int x, int y, Crate crate) {
		super(x, y, crate);
	}
	
	public String toString() {
		return objectOnTop != null ? objectOnTop.toString() : ".";
	}


}
