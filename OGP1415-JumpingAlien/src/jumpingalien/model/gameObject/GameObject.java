package jumpingalien.model.gameObject;

import java.util.ArrayList;
import java.util.Arrays;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public abstract class GameObject {
	protected Position position;
	protected Sprite[] spriteList;
	protected int currentSpriteNumber=0;
	protected World world;
	protected boolean terminated = false;
	protected HitPoint hitPoint;
	protected int m;
	
	@Raw
	protected GameObject(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		position = new Position(new double[]{pixelLeftX/100.0d,pixelBottomY/100.0d});
		this.spriteList = sprites;
		this.m = (spriteList.length-8)/2;
		hitPoint = new HitPoint(0,500,100);
	};
	
	public void loseHp(int amount){
		hitPoint.loseHP(amount);
	}
	
	public void addHp(int amount){
		hitPoint.addHP(amount);
	}
	
	public boolean hasMaxHp(){
		if(hitPoint.getCurrent() == hitPoint.getMaximum()){
			return true;
		}
		return false;
	}
	
	public boolean isDead(){
		return hitPoint.isDead();
	}
	
	@Basic
	public int getNbHitPoints(){
		return hitPoint.getCurrent();
	}
	
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	public abstract void addToWorld(World world);
	
	protected boolean canHaveAsWorld(World world){
		if(!world.isTerminated() && !this.isTerminated() && this.world==null)
			return true;
		return false;
	}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public double[] getPerimeters(){
		//order: left,bottom,right,top
		double[] perimeters = new double[4];
		perimeters[0] = position.getPositions()[0];
		perimeters[1]=  position.getPositions()[1];
		perimeters[2] = position.getPositions()[0]+ (getCurrentSprite().getWidth()-1)/100.0d;
		perimeters[3] = position.getPositions()[1]+ (getCurrentSprite().getHeight()-1)/100.0d;
		return perimeters;
	}
	
	@Basic
	public Position getPosition(){
		return position;
	}
	
	@Basic
	public double getPositionY(){
		return position.getPositions()[1];
	}
	
	@Basic
	public double getPositionX(){
		return position.getPositions()[0];
	}
	
	public void setPositionX(double x) throws PositionOutOfBoundsException{
		position = new Position(world,new double[]{x,getPositionY()}); 
	}
	
	public void setPositionY(double y) throws PositionOutOfBoundsException{
		position = new Position(world,new double[]{getPositionX(),y}); 
	}

	/**
	 * 
	 * @return	returns the Sprite that is currently showing.
	 * 			|spriteList[currentSpriteNumber]
	 * @Pre		this.currentSpriteNumber must be a valid index from the spriteList
	 * 			| isValidSpriteNumber(currentSpriteNumber)
	 */
	@Basic
	public Sprite getCurrentSprite() {
		assert isValidSpriteNumber(currentSpriteNumber);
		return spriteList[currentSpriteNumber];
	}

	/**
	 * returns the size of mazub at the moment time has last advanced.
	 * @throws NullPointerException
	 * 			the current Sprite points toward null
	 * 			|currentSprite == null
	 * @throws ModelException
	 * 			the current Sprite has an invallid Width or Height
	 * 			|currentSprite.getWidth() <= 0 || currentSprite.getHeight() <= 0		
	 * @return 	|[getCurrentSprite().getWidth(),getCurrentSprite().getHeight()]
	 */
	@Basic
	public int[] getSize() throws NullPointerException, IllegalSizeException {
		Sprite currentSprite = getCurrentSprite();
		if(currentSprite == null) throw new NullPointerException("currentSprite isn't pointing toward any Sprite");
		if(currentSprite.getWidth() <= 0 || currentSprite.getHeight() <= 0) throw new IllegalSizeException(currentSprite.getWidth(),currentSprite.getHeight());
		return new int[]{getCurrentSprite().getWidth(),getCurrentSprite().getHeight()};
	}

	protected boolean isValidSpriteNumber(int currentSpriteNb) {
		return currentSpriteNb >= 0 && currentSpriteNb < spriteList.length;
	}
	
	public abstract void advanceTime(double dt) throws IllegalMovementException,IllegalMazubStateException,IllegalTimeException,PositionOutOfBoundsException;

	public boolean[] overlapsWithWall() {
		boolean[] overlap = new boolean[]{false,false,false,false};
		double [] perimeters = this.getPerimeters();//order: left,bottom,right,top
		for(int i=0;i<perimeters.length;i++)perimeters[i]*=100;
		int [][] occupied_tiles = world.getTilePositionsIn((int) (perimeters[0]),(int)(perimeters[1]),(int)(perimeters[2]),(int)(perimeters[3]));
		for (int i=0 ; i < occupied_tiles.length ; i++){
			if (world.getGeologicalFeature(new int[]{occupied_tiles[i][0]*world.getTileLenght(),occupied_tiles[i][1]*world.getTileLenght()})==1){//TODO intern if-else{if-else{...}}, not if if if
				//check if tile is beneath character
				if (world.getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] < perimeters[1])
					overlap[0]=true;
				//check if tile is left of character
				if (world.getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[0] < perimeters[0])
					overlap[1]=true;
				//check if tile is right of character
				if (world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[0] > perimeters[2]){
					overlap[3]=true;
					System.out.println(world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[0]+","+perimeters[2]);}
				//check if tile is above character
				if (world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] > perimeters[3]){
					System.out.println(Arrays.toString(occupied_tiles[i])+","+perimeters[3]+","+Arrays.toString(world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])));
					overlap[2]=true;}
			}
		}
		//bot,left,top,right
		return overlap;
	}

	public boolean[] overlapsWithGameObject() {
		double [] perimeters = this.getPerimeters();
		ArrayList<GameObject> gameObjects = world.getAllGameObjects();
		for (int i=0 ; i < gameObjects.size() ; i++){
			double [] perimetersGameObject = gameObjects.get(i).getPerimeters();
			//TODO check voor overlap!
		}
		//bot,left,top,right
		return new boolean[]{false,false,false,false};
	}
}
