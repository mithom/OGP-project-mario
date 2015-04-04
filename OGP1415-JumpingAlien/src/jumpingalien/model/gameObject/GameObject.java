package jumpingalien.model.gameObject;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.exception.IllegalSizeException;
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
	
	@Raw
	protected GameObject(int x, int y, Sprite[] sprites) throws PositionOutOfBoundsException{
		//position = new Position(new double[]{x,y});
		
	};
	
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	public void addToWorld(World world){
		if(this.world == null && canHaveAsWorld(world)){
			this.world = world;
		}
	}
	
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
}
