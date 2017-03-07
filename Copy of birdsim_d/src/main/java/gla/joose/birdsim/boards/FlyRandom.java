package gla.joose.birdsim.boards;

import java.util.Random;
import gla.joose.birdsim.pieces.Piece;

public class FlyRandom implements Fly {

	Random rand = new Random();
	
	public void fly(Board b, Piece piece) {
		int randRow = rand.nextInt((b.getRows() - 3) + 1) + 0;
    	int randCol = rand.nextInt((b.getColumns() - 3) + 1) + 0; 
    	piece.moveTo(randRow, randCol);
		piece.setSpeed(piece.getSpeed());
	}

}