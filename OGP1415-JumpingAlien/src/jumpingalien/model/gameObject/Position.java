package jumpingalien.model.gameObject;

import jumpingalien.exception.PositionOutOfBoundsException;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.World;

public class Position {//TODO GET RID OF THE STUPID DISTINCTION OF METER AND PIXEL-> everything in pixel except for some inspectors in facade
	private final double[] position;//in meters!, pixel is 0.01m = 1cm
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
			coordinate[0] = Math.min(Math.max(0, coordinate[0]),world.getWidth()/100.0d);
			coordinate[1] = Math.min(Math.max(0, coordinate[1]),world.getHeight()/100.0d);
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
	
	public int[] getPixelPosition(){
		return new int[]{(int)(position[0]*100), (int)(position[1]*100) };
	}
	
	public boolean isValidCoordinate(double[] coordinate){
		if(coordinate.length == 2){
			if(coordinate[0] < 0 || coordinate[0]>= world.getWidth()/100.0d ||
					(coordinate[1]<0 || coordinate[1] >= world.getHeight()/100.0d)){
				System.out.println("width: "+world.getWidth()+"height: "+world.getHeight());
				return false;
			}else
				return true;
		}
		return false;
	}
}
