package gla.joose.birdsim.boards;

import java.util.Random;

import gla.joose.birdsim.pieces.Grain;
import gla.joose.birdsim.pieces.Piece;
import gla.joose.birdsim.util.Distance;
import gla.joose.birdsim.util.DistanceMgr;

public class FlyTowards implements Fly {

	Random rand = new Random();
	
	public void fly(Board b, Piece piece) {
		DistanceMgr dmgr = new DistanceMgr();
		int current_row = piece.getRow();
		int current_col = piece.getColumn();
		
		synchronized(b.allPieces){
			for (int i=0;i< b.getAllPieces().size(); i++) {
                Piece p = b.getAllPieces().get(i);
                if(p instanceof Grain){
                	int dist_from_food_row = current_row - p.getRow();
	                int dist_from_food_col = p.getColumn() - current_col;
	                Distance d = new Distance(piece, (Grain)p, dist_from_food_row, dist_from_food_col);	
	                dmgr.addDistance(d);
                }
			}
		}
		
		Distance distances[] = dmgr.getDistances();
		boolean movedone = false;

		for(int i =0; i< distances.length;i++){
			Distance d = distances[i];
			
			if(d.getRowDist() <= d.getColDist()){
				if(d.getRowDist() > 0){
					boolean can_move_down= piece.canMoveTo(current_row-1, current_col);
		    		if(can_move_down){
		    			piece.moveTo(current_row-1, current_col);
						movedone = true;
						break;
					}
				}
				
				else if(d.getRowDist() < 0){
					boolean can_move_up= piece.canMoveTo(current_row+1, current_col);
		    		if(can_move_up){
		    			piece.moveTo(current_row+1, current_col);
						movedone = true;
						break;
					}
				}
				
				else if(d.getRowDist() == 0){
					if(d.getColDist() >0){
						boolean can_move_right = piece.canMoveTo(current_row, current_col+1);
						if(can_move_right){
							piece.moveTo(current_row, current_col+1);
							movedone = true;
							break;
						}
					}
					
					else if(d.getColDist() < 0){
						boolean can_move_left = piece.canMoveTo(current_row, current_col-1);
						if(can_move_left){
							piece.moveTo(current_row, current_col-1);
							movedone = true;
							break;
						}
					}
					
					else if(d.getColDist() == 0){
						foodFound(b, d);
						b.setFly(new FlyRandom());
						b.performFly(b, piece);
						movedone = true;
						break;
					}
				}
			}
			///////
			else if(d.getRowDist() > d.getColDist()){            	
				if(d.getColDist() > 0){
					boolean can_move_right = piece.canMoveTo(current_row, current_col+1);
					if(can_move_right){
						piece.moveTo(current_row, current_col+1);
						movedone = true;
						break;
					}
				}
				
				else if(d.getColDist() < 0){
					boolean can_move_left = piece.canMoveTo(current_row, current_col-1);
					if(can_move_left){
						piece.moveTo(current_row, current_col-1);
						movedone = true;
						break;
					}
				}
				else if(d.getColDist() == 0){
					if(d.getRowDist() >0){
						boolean can_move_up= piece.canMoveTo(current_row-1, current_col);
			    		if(can_move_up){
			    			piece.moveTo(current_row-1, current_col);
							movedone = true;
							break;
						}
					}
					
					else if(d.getRowDist() < 0){
						boolean can_move_down = piece.canMoveTo(current_row+1, current_col);///kkkk
			    		if(can_move_down){
			    			piece.moveTo(current_row+1, current_col);
							movedone = true;
							break;
						} 
					}
					
					else if(d.getRowDist() == 0){
						foodFound(b, d);
						b.setFly(new FlyRandom());
						b.performFly(b, piece);
						movedone = true;
						break;
					}
				}
			}
		}
		if(!movedone){
			b.setFly(new FlyRandom());
			b.performFly(b, piece);
		}
	}
	
	public void foodFound(Board b, Distance d) {	
		Grain grain = (Grain)d.getTargetpiece();
		grain.deplete();
		
		if (b instanceof MovingForageBoard){
			b.setFly(new FlyRandom());
			b.performFly(b, grain);
		}
		
		if(b.starveBirds){
			grain.remove();
			b.updateStockDisplay();
		}
		else if(grain.getRemaining() <=0){
			grain.remove();	
			b.updateStockDisplay();
		} 
	}
}