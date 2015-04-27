package jumpingalien.model.gameObject;

import java.security.InvalidKeyException;
import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

/**
 * Mazub is a class representing a character/GameObject of the game. 
 * @author Meerten Wouter & Michiels Thomas
 * @version 1.0
 * 
 * @Invar	each gameObject that has a world, must have a valid position
 * 			|Position.isValidCoordinate(getPosition().getPositions())
 * @Invar	each GameObject has valid hitPoints, this means, a hitpoint located between a given minimum and maximum.
 * 			|getNbHitPoints()>minimum && getNbHitPoints()<maximum
 * @Invar	each gameObject has at least 1 sprite
 * 			|spriteList.length()>=1 
 */
public abstract class GameObject {
	protected Position position;
	protected final Sprite[] spriteList;
	protected int currentSpriteNumber=0;
	protected World world;
	protected boolean terminated = false;
	protected HitPoint hitPoint;
	protected int m;
	protected double imunityTime;
	protected double lastWaterHit;
	protected double lastLavaHit;
	/**
	 * 
	 * @param pixelLeftX    |the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that a gameobject will use to rotate trough, to make
	 * 						|animations.
	 * @throws PositionOutOfBoundsException
	 * 			the gameobject has an illegal position
	 * 			| ! hasValidPosition()
	 * @Post the initial position of the Mazub,shark, plant or slime instance instance will be (pixelLeftX,pixelBottomY)
	 * 			|new.getPosition()== (pixelLeftX,pixelBottomY) 
	 * @Post the list of Sprites the mazub, shark, plant or slime instance will use, will be stored in spriteList
	 * 			|new.spriteList == sprites;
	 * @Pre		sprites must at least have 1 sprite
	 * 			|sprites.lenght>=1
	 * 
	 */
	@Raw @Model
	protected GameObject(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		assert sprites.length>=1;
		position = new Position(new double[]{pixelLeftX/100.0d,pixelBottomY/100.0d});
		this.spriteList = sprites;
		this.m = (spriteList.length-8)/2;
		hitPoint = new HitPoint(0,500,100);
		imunityTime=0.0d;
		lastWaterHit =0.2d;
		lastLavaHit=0.0d;
	};
	
	/**
	 * removes the given amount of Hp
	 * @param amount | the amount of Hp the gameobject has to lose
	 * @effect lets the gameobject lose Hp | hitPoint.loseHp(amount)
	 * @effect if the gameobject is dead and not terminated, it will be removed from the world
	 * 			| world.removeGameObject(this)
	 * 			| terminated = true
	 */
	
	public void loseHp(int amount){
		hitPoint.loseHP(amount);
		if(isDead() && !isTerminated()){
			world.removeGameObject(this);
			terminated = true;
		}
	}
	
	/**
	 * 
	 * @param amount | the amount of Hp the gameobject has to gain
	 * @effect the given amount has to be added | hitPoint.addHp(amount)
	 */
	public void addHp(int amount){
		hitPoint.addHP(amount);
	}
	
	/**
	 * checks if the gameobject has maximum number of hitpoints
	 * @return true if it has maximum Hp. Otherwise false
	 * 			| if hitPoint.getCurrent() == hitPoint.getMaximum()
	 * 			| 		then true
	 * 			| else       false
	 */
	
	public boolean hasMaxHp(){
		if(hitPoint.getCurrent() == hitPoint.getMaximum()){
			return true;
		}
		return false;
	}
	
	/**
	 * checks if the gameobject is dead
	 * @return true if gameobject is dead| hitPoint.isDead()
	 */
	
	public boolean isDead(){
		return hitPoint.isDead();
	}
	
	
	/**
	 * returns the number of hitpoints
	 * @return current number of Hp | hitPoint.getCurrent()
	 */
	@Basic
	public int getNbHitPoints(){
		return hitPoint.getCurrent();
	}
	
	/**
	 * returns the current world
	 * @return | this.world
	 */
	
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	public abstract void addToWorld(World world);
	
	/**
	 * checks if the gameobject can have the given world as world
	 * @param world | the world that needs to be checked
	 * @return true if the world isn't terminated, the gameobject isn't terminated and the gameobject hasn't a world assigned yet
	 * 			| if !world.isTerminated() && !this.isTerminated() && this.world==null
	 * 			| 		then true
	 * 			| else       false
	 */
	protected boolean canHaveAsWorld(World world){
		if(!world.isTerminated() && !this.isTerminated() && this.world==null)
			return true;
		return false;
	}
	
	/**
	 * checks if the gameobject is Terminated
	 * @return | terminated
	 */
	public boolean isTerminated(){
		return terminated;
	}
	
	/**
	 * 
	 * @return the perimeters of the gameobject 
	 */
	
	public double[] getPerimeters(){
		//order: left,bottom,right,top
		double[] perimeters = new double[4];
		perimeters[0] = position.getPositions()[0];
		perimeters[1]=  position.getPositions()[1];
		perimeters[2] = position.getPositions()[0]+ (getCurrentSprite().getWidth()-1)/100.0d;
		perimeters[3] = position.getPositions()[1]+ (getCurrentSprite().getHeight()-1)/100.0d;
		return perimeters;
	}
	
	/**
	 * 
	 * @return the position of the gameobject | position
	 */
	@Basic
	public Position getPosition(){
		return position;
	}
	
	/**
	 * 
	 * @return the Y-component of the position | position.getPositions()[1]
	 */
	@Basic
	public double getPositionY(){
		return position.getPositions()[1];
	}
	
	/**
	 * 
	 * @return the X-component of the position | position.getPositions()[0]
	 */
	
	@Basic
	public double getPositionX(){
		return position.getPositions()[0];
	}
	
	/**
	 * 
	 * @param x | the coordinate according to the horizontal axis
	 * @throws PositionOutOfBoundsException
	 * 			the position isn't legal
	 * @Post the x-position of the gameobject will be set to the given x
	 * 			| new.getPositionX() = x
	 * 			
	 */
	public void setPositionX(double x) throws PositionOutOfBoundsException{
		position = new Position(world,new double[]{x,getPositionY()}); 
	}
	
	/**
	 * 
	 * @param y | the coordinate according to the vertical axis
	 * @throws PositionOutOfBoundsException
	 * 			the position isn't legal
	 * @Post the y-position of the gameobject will be set to the given y
	 * 			| new.getPositionY() = y
	 * 			
	 */
	
	public void setPositionY(double y) throws PositionOutOfBoundsException{
		position = new Position(world,new double[]{getPositionX(),y}); 
	}

	/**
	 * 
	 * @return	returns the Sprite that is currently showing.
	 * 			|spriteList[currentSpriteNumber]
	 */
	
	@Basic
	public Sprite getCurrentSprite() {
		return spriteList[currentSpriteNumber];
	}
	
	/*
	 * @Param	number| the number that has to be set as currentSpriteNumber
	 * @Pre		this.currentSpriteNumber must be a valid index from the spriteList
	 * 			| isValidSpriteNumber(currentSpriteNumber)
	 */
	public void setCurrentSprite(int number){
		assert isValidSpriteNumber(number);
		currentSpriteNumber = number;
		
	}

	/**
	 * returns the size of the game at the moment time has last advanced.
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
	
	/**
	 * checks if the given spriteNumber is valid
	 * @param currentSpriteNb | the number of the currently showing sprite
	 * @return the number of the sprite is a valid number, true or false
	 * 			| currentSpriteNb >= 0 && currentSpriteNb < spriteList.length
	 */

	protected boolean isValidSpriteNumber(int currentSpriteNb) {
		return currentSpriteNb >= 0 && currentSpriteNb < spriteList.length;
	}
	

	public abstract void advanceTime(double dt) throws NullPointerException,IllegalMovementException,IllegalMazubStateException,IllegalTimeException,PositionOutOfBoundsException, IllegalSizeException;
	
	
	/**
	 * checks if the gameobject overlaps with a wall and where it overlaps
	 * @return an array with 4 booleans. First slot describes if gameobject overlaps to the left, the second at the bottom, third to the right and last at the top.
	 */
	//bot,left,top,right
	public boolean[] overlapsWithWall() {
		boolean[] overlap = new boolean[]{false,false,false,false};
		double [] perimeters = this.getPerimeters();//order from perimeter: left,bottom,right,top
		for(int i=0;i<perimeters.length;i++)perimeters[i]*=100;
		// occupied tiles = eerst X dan Y
		int [][] occupied_tiles = world.getTilePositionsIn((int) (perimeters[0]),(int)(perimeters[1]),(int)(perimeters[2]),(int)(perimeters[3]));
		for (int i=0 ; i < occupied_tiles.length ; i++){
			if (world.getGeologicalFeature(new int[]{occupied_tiles[i][0]*world.getTileLength(),occupied_tiles[i][1]*world.getTileLength()})==1){
				//check if tile is beneath character
				if (world.getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] < perimeters[1])
					overlap[0]=true;
				//check if tile is left of character
				if (world.getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[0] < perimeters[0] &&
						world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] > perimeters[1]+1d){
					overlap[1]=true;
				}
				//check if tile is right of character
				if (world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[0] > perimeters[2]&&
						world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] > perimeters[1]+1d)
					overlap[3]=true;
				//check if tile is above character
				if (world.getTopRightPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] > perimeters[3]){					
					overlap[2]=true;}
			}
		}
		//bot,left,top,right
		return overlap;
	}
	
/**
 * returns an arraylist with all overlapping gameobjects
 * @return array with overlapping gameobjects
 * 				|ArrayList<GameObject> gameObjects = world.getAllGameObjects();
				|ArrayList<GameObject> overlappingObjects = new ArrayList<GameObject>();
				|	for (int i=0 ; i < gameObjects.size() ; i++){
				|		if(overlaps(gameObjects.get(i)) && gameObjects.get(i) != this)
				|			overlappingObjects.add(gameObjects.get(i));
				|return overlappingObjects;
 */
	public ArrayList<GameObject> getOverlappingGameObjects() {
		ArrayList<GameObject> gameObjects = world.getAllGameObjects();
		ArrayList<GameObject> overlappingObjects = new ArrayList<GameObject>();
		for (int i=0 ; i < gameObjects.size() ; i++){
			if(overlaps(gameObjects.get(i)) && gameObjects.get(i) != this){
				overlappingObjects.add(gameObjects.get(i));
			}
		}
		return overlappingObjects;
	}
	/**
	 * checks if the gameobject overlaps with the given object
	 * @param object | the given object that can overlap with the gameobject
	 * @return true if the gameobject overlaps with the given object, false if it doesn't
	 * 			
	 */			
	public boolean overlaps(GameObject object){
		double [] perimeters = this.getPerimeters();//order: left,bottom,right,top
		double [] perimetersGameObject = object.getPerimeters();//order: left,bottom,right,top
		if((perimeters[0]<perimetersGameObject[2] && perimeters[2]>perimetersGameObject[0])
				&&(perimeters[1]<perimetersGameObject[3] && perimeters[3]>perimetersGameObject[1])){
			return true;
		}
		return false;
	}

	/**
	 * checks where a gameobject overlaps with another gameobject.
	 * @return an array with 4 booleans.  First slot describes if gameobject overlaps to the left, the second at the bottom, third to the right and last at the top.
	 * @throws NullPointerException
	 * 			trying to take the size of the value null as if it was an array
	 * @throws IllegalSizeException
	 * 			the array has an illegal size
	 */
	public boolean[] placeOverlapsWithGameObject() throws NullPointerException, IllegalSizeException{
		boolean[] overlap = new boolean[]{false,false,false,false};//left,bot,right,top
		ArrayList<GameObject> overlappingObjects = getOverlappingGameObjects();
		for (int  i=0 ; i < overlappingObjects.size() ; i++){
			if (overlappingObjects.get(i).getPositionX() < this.getPositionX()){
				overlap[0]=true;
			}
			if (overlappingObjects.get(i).getPositionY() < this.getPositionY()){
				overlap[1]=true;
			}
			if (overlappingObjects.get(i).getPositionX()+overlappingObjects.get(i).getSize()[0] > this.getPositionX()+this.getSize()[0]){
				overlap[2]=true;
			}
			if (overlappingObjects.get(i).getPositionY()+overlappingObjects.get(i).getSize()[1] > this.getPositionY()+this.getSize()[1]){
				overlap[3]=true;
			}
		}
		return overlap;
	}
	
	/**
	 * check whether the gameobject is immune or not
	 * @return true if it is immune, otherwise false
	 * 			|immunityTime>0
	 */
	public boolean isImmune(){
		return imunityTime>0;
	}
	
	/**
	 * checks if gameobject is in water
	 * @return true if the gameobject is in a waterTile, otherwise false
	 */
	public boolean isInWater(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLength(), tile[1]*world.getTileLength())==2)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInWater");
			}
		}
		return false;
	}
	/**
	 * checks if gameobject is in water
	 * @return true if the gameobject is in a lavaTile, otherwise false
	 */
	public boolean isInLava(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLength(), tile[1]*world.getTileLength())==3)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInLava");
			}
		}
		return false;
	}
	
	/**
	 * checks if gameobject is in the air
	 * @return true if the gameobject is in an airTile, otherwise false
	 */
	
	public boolean isInAir(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLength(), tile[1]*world.getTileLength())==0)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInAir");
			}
		}
		return false;
	}
	
	public abstract void EffectOnCollisionWith(GameObject gameObject);
	
	//left,bottom,right,top, need to be checked after position has been resetted
	public boolean[] sideOverlappingBetween(GameObject bounceAgainst){
		double[] own = getPerimeters();
		double[] other = bounceAgainst.getPerimeters();
		boolean[] overlap=new boolean[4];
		if(own[0] > other[2])
			overlap[0]=true;
		if(own[1] > other[3])
			overlap[1]=true;
		if(own[2]<other[0])
			overlap[2]=true;
		if(own[3]<other[1])
			overlap[3]=true;
		return overlap;
	}
}
