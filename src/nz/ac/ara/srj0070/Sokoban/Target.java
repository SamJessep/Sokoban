package nz.ac.ara.srj0070.Sokoban;

public class Target extends Placeable {

	public Target(int x, int y) {
		super(x, y);
	}
	
	public String toString() {
		return "+";
	}

	public void addWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	public void addCrate(Crate crate) {
		// TODO Auto-generated method stub
		
	}
	
	public Boolean isCompleted() {
		return false;
	}

}
