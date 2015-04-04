package jumpingalien.model.gameObject;

import jumpingalien.exception.PositionOutOfBoundsException;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.World;

public class Position {
	private final double[] position;
	private final World world; 
	/*
	public Position(World world,double[] coordinates) throws PositionOutOfBoundsException{
		positions = new ArrayList<double[]>();
		this.world = world;
		for(double[] coordinate:coordinates){
			if(isValidCoordinate(coordinate)){
				positions.add(coordinate);
			}else{
				throw new PositionOutOfBoundsException(coordinate[0],coordinate[1]);
			}
		}
	}*/
	
	public Position(World world,double[] coordinate)throws PositionOutOfBoundsException{
		this.world = world;
		if(isValidCoordinate(coordinate)){
			position = coordinate;
		}else{
			throw new PositionOutOfBoundsException(coordinate[0],coordinate[1]);
		}
	}
	
	@Immutable @Basic
	public double[] getPositions(){
		return position.clone();
	}
	
	public boolean isValidCoordinate(double[] coordinate){
		if(coordinate.length == 2){
			if(coordinate[0] < 0 || coordinate[0]>= world.getWidth()/100.0d ||
					(coordinate[1]<0 || coordinate[1] >= world.getHeight()/100.0d)){
				return false;
			}else
				return true;
		}
		return false;
	}
}
