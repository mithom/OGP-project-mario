package jumpingalien.model.gameObject;

import jumpingalien.exception.PositionOutOfBoundsException;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.World;

/**
 * Position is a class about coordinates in meter and pixelpositions.
 * @author Meerten Wouter & Michiels Thomas
 * @version 1.0
 */
public class Position {
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
	
	/**
	 * 
	 * @param coordinate | the coordinate of the position
	 * @throws PositionOutOfBoundsException
	 * 			the coordinate has an illegal value
	 * 			|! isValidCoordinate(coordinate)
	 * @Post the position is set to the given coordinate
	 * 			| new.getPositions = coordinate
	 */		
	public Position(double[] coordinate)throws PositionOutOfBoundsException{
		/*if(isValidCoordinate(coordinate)){
			position = coordinate;
		}else{
			throw new PositionOutOfBoundsException(coordinate[0],coordinate[1]);
		}*/
		position = coordinate;
	}
	
	/**
	 * 
	 * @param world | the world in which the coordinate lies
	 * @param coordinate | the coordinate of the position
	 * @throws PositionOutOfBoundsException
	 * 			the coordinate has an illegal value
	 * 			|! isValidCoordinate()
	 * @Post the position is set to the given coordinate if the coordinate is valid
	 * 			| if isValidCoordinate(coordinate)
	 * 					new.getPositions = coordinate 
	 */
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
	
	/**
	 * returns the positions
	 * @return | this.position.clone()
	 */
	@Immutable @Basic
	public double[] getPositions(){
		return position.clone();
	}
	/**
	 * returns the position in pixels
	 * @return | int[]{(int)(position[0]*100), (int)(position[1]*100) }
	 */
	public int[] getPixelPosition(){
		return new int[]{(int)(position[0]*100), (int)(position[1]*100) };
	}
	
	/**
	 * checks if the given coordinate is valid
	 * @param coordinate | the coordinate that needs to be checked
	 * @return false if the length isn't equal to 2. Also false if the coordinate isn't within the
	 * 		   borders of the world
	 * 			| if (coordinate.length !=2) || ( (coordinate[0] < 0) || coordinate[0]>= world.getWidth()/100.0d ||
	 *			|	(coordinate[1]<0 || coordinate[1] >= world.getHeight()/100.0d))
	 *			|		then false
	 *			| else       true
	 */
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
