package jumpingalien.model.gameObject;

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
}
