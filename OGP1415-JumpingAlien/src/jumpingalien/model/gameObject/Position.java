package jumpingalien.model.gameObject;

import jumpingalien.exception.PositionOutOfBoundsException;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.World;

public class Position {
	private final double[] position;
	private World world; 
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
	
	public Position(double[] coordinate)throws PositionOutOfBoundsException{
		/*if(isValidCoordinate(coordinate)){
			position = coordinate;
		}else{
			throw new PositionOutOfBoundsException(coordinate[0],coordinate[1]);
		}*/
		position = coordinate;
	}
	
	public Position(World world,double[] coordinate) throws PositionOutOfBoundsException{
		this.world = world;
		if(world != null){
			if(isValidCoordinate(coordinate)){
				position = coordinate;
			}else{
				throw new PositionOutOfBoundsException(coordinate[0],coordinate[1]);
			}
		}else{
			position =coordinate;
		}
	}
	
	@Immutable @Basic
	public double[] getPositions(){
		return position.clone();
	}
	
	public int[] getPixelPosition(){//TODO check if position is used here in pixels or meter (convert maybe everything to 1pixel = 1)
		return new int[]{(int)position[0], (int)position[1] };
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
