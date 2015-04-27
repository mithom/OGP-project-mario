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


public abstract class GameObject {
	protected Position position;
	protected Sprite[] spriteList;
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
	 * 
	 */
	@Raw @Model
	protected GameObject(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		position = new Position(new double[]{pixelLeftX/100.0d,pixelBottomY/100.0d});
		this.spriteList = sprites;
		this.m = (spriteList.length-8)/2;
		hitPoint = new HitPoint(0,500,100);
		imunityTime=0.0d;
		lastWaterHit =0.2d;
		lastLavaHit=0.0d;
	};
	
	public void loseHp(int amount){
		hitPoint.loseHP(amount);
		if(isDead() && !isTerminated()){
			world.removeGameObject(this);
			terminated = true;
		}
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
	
	public abstract void advanceTime(double dt) throws NullPointerException,IllegalMovementException,IllegalMazubStateException,IllegalTimeException,PositionOutOfBoundsException, IllegalSizeException;
	
	//bot,left,top,right
	public boolean[] overlapsWithWall() {
		boolean[] overlap = new boolean[]{false,false,false,false};
		double [] perimeters = this.getPerimeters();//order from perimeter: left,bottom,right,top
		for(int i=0;i<perimeters.length;i++)perimeters[i]*=100;
		// occupied tiles = eerst X dan Y
		int [][] occupied_tiles = world.getTilePositionsIn((int) (perimeters[0]),(int)(perimeters[1]),(int)(perimeters[2]),(int)(perimeters[3]));
		for (int i=0 ; i < occupied_tiles.length ; i++){
			if (world.getGeologicalFeature(new int[]{occupied_tiles[i][0]*world.getTileLenght(),occupied_tiles[i][1]*world.getTileLenght()})==1){//TODO intern if-else{if-else{...}}, not if if if
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

	public ArrayList<GameObject> getOverlappingGameObjects() {
		ArrayList<GameObject> gameObjects = world.getAllGameObjects();
		ArrayList<GameObject> overlappingObjects = new ArrayList<GameObject>();
		for (int i=0 ; i < gameObjects.size() ; i++){
			if(overlaps(gameObjects.get(i)) && gameObjects.get(i) != this){//TODO does this work altough copy?
				overlappingObjects.add(gameObjects.get(i));
			}
		}
		return overlappingObjects;
	}
	
	private boolean overlaps(GameObject object){
		double [] perimeters = this.getPerimeters();//order: left,bottom,right,top
		double [] perimetersGameObject = object.getPerimeters();//order: left,bottom,right,top
		if((perimeters[0]<perimetersGameObject[2] && perimeters[2]>perimetersGameObject[0])
				&&(perimeters[1]<perimetersGameObject[3] && perimeters[3]>perimetersGameObject[1])){
			return true;
		}
		return false;
	}


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
	
	public boolean isImmune(){
		return imunityTime>0;
	}
	
	public boolean isInWater(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLenght(), tile[1]*world.getTileLenght())==2)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInWater");
			}
		}
		return false;//TODO check if isBottomInWater isn't good enough
	}
	
	public boolean isInLava(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLenght(), tile[1]*world.getTileLenght())==3)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInLava");
			}
		}
		return false;
	}
	
	public boolean isInAir(){
		double[] perimeters = getPerimeters();
		int [][] occupied_tiles = world.getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			try{
				if(world.getGeologicalFeature(tile[0]*world.getTileLenght(), tile[1]*world.getTileLenght())==0)
					return true;
			}catch(InvalidKeyException e){
				System.out.println(tile[0] +","+ tile[1]);
				System.out.println("something went wrong, should never have happened!, error in isInLava");
			}
		}
		return false;
	}
	
	public abstract void EffectOnCollisionWith(GameObject gameObject);
}
