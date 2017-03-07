package gla.joose.birdsim.boards;

import gla.joose.birdsim.pieces.Bird;
/**
 * A BirdSim board with where birds simultaneously fly and perch on  static grains.
 */
public class StaticForageBoard extends BoardBehaviour{
		
	public StaticForageBoard(int rows, int columns) {
		super(rows, columns);
	}

	public void fly(){
		
		Bird bird = new Bird();
		
		int randRow = rand.nextInt((getRows() - 3) + 1) + 0;
    	int randCol = rand.nextInt((getColumns() - 3) + 1) + 0;
    	
		place(bird,randRow, randCol);
		bird.setDraggable(false);
		bird.setSpeed(20);
			
		updateStockDisplay();
		
		while(!scareBirds){
			setFly(new FlyTowards());
			performFly(this, bird); 
		}
		
		bird.remove();
		updateStockDisplay();
       
	}
}