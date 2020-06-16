package nz.ac.ara.srj0070.model;

public class Container extends Placeable{
	public Placeable objectOnTop;
	Container(int x, int y){
		super(x,y);
	}
	
	Container(int x, int y, Worker worker) {
		super(x, y);
		addWorker(worker);
	}
	
	Container(int x, int y, Crate crate) {
		super(x, y);
		addCrate(crate);
	}
	
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
	}
	
	public void addWorker(Worker worker) {
		objectOnTop = worker;
		
	}

	public void addCrate(Crate crate) {
		objectOnTop = crate;
		
	}
	
	public void clearOnTopObject() {
		objectOnTop = null;
	}
	
	public Boolean isEmpty() {
		return objectOnTop == null;
	}
	
}
