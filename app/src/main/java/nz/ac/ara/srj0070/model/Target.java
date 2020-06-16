package nz.ac.ara.srj0070.model;

public class Target extends Container {

	public Target(int x, int y) {
		super(x, y);
	}
	
	public Target(int x, int y, Worker worker) {
		super(x, y, worker);
	}

	public Target(int x, int y, Crate crate) {
		super(x, y, crate);
	}
	

	public String toString() {
		return objectOnTop != null ? objectOnTop.toString().toUpperCase() : "+";
	}


	public Boolean isCompleted() {
		return objectOnTop instanceof Crate;
	}

}
