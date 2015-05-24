package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.Direction;
import jumpingalien.util.Sprite;
import jumpingalien.model.World;
/**
 * Plant is a class representing a GameObject of the world
 * @author Meerten Wouter & Michiels Thomas (both 2de fase ingenieurswetenschappen)
 * @version 1.0
 * @Invar 	the position of a plant will always be valid. 
 * 			This means it will never be outside of the boundaries of the world or overlap with impassable terrain
 * @Invar	the currentSpriteNumber must always be valid
 * 			|isValidSpriteNumber(currentSpriteNumber)
 */
public class Plant extends GameObject{
	private Direction direction;
	private final double horizontalVelocity=0.5d;
	private double actionTimer;
	
	/**
	 * 
	 * @param x |the most left position that is part from the currently showing Sprite.
	 * @param y |the lowest position that is part of the currently showing Sprite.
	 * @param sprites |a list of Sprites that plant will use to rotate trough
	 * @throws PositionOutOfBoundsException
	 * 			plant has an illegal position
	 * 			| ! hasValidPosition()
	 * @Post The plant will be automatically moving to the right when the game starts
	 * 			| direction = Direction.RIGHT
	 */
	
	public Plant(int x, int y, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(x,y,sprites,0,1,1);
		actionTimer=0.0d;
		direction = Direction.RIGHT;
	}
	
	public Plant(int x, int y, Sprite[] sprites,Program program) throws PositionOutOfBoundsException{
		super(x,y,sprites,0,1,1,program);
		actionTimer=0.0d;
		direction = Direction.RIGHT;
	}
	
	/**
	 * 
	 * @throws PositionOutOfBoundsException
	 * 		Plant has an illegal position
	 * @effect Plant will move horizontally
	 * 		| moveHorizontal(smallDt)
	 * @effect If Plant overlaps with an other gameObject, it will check if this has consequences
	 * 		| EffectOnCollsionWith(gameObject)
	 * 		| gameObject.EffectOnCollisionWith(this)
	 * @effect The movement of plant will be animated
	 * 		| animate()
	 */
	
	@Override
	public void advanceTime(double dt)throws PositionOutOfBoundsException{
		while(dt>0 && !isTerminated()){
			double smallDt = Math.min(0.01d/Math.abs(horizontalVelocity),dt);
			dt-= smallDt;
			moveHorizontal(smallDt);
			for(GameObject gameObject:getOverlappingGameObjects()){
				EffectOnCollisionWith(gameObject);
				gameObject.EffectOnCollisionWith(this);
			}
			animate();
		}
	}
	
	/**
	 * animates the movement of plant.
	 * @Post if moving to the right, the spritenumber will be 1, else it will be 0
	 * 			| if direction == Direction.RIGHT
				|	new.currentSpriteNumber=1
				| else
				|   new.currentSpriteNumber=0
	 */
	
	public void animate(){
		if(direction == Direction.RIGHT)
			setCurrentSprite(1);
		else
			setCurrentSprite(0);
	}
	
	/**
	 * @param dt | The period of time that the plant needs to move
	 * @throws PositionOutOfBoundsException 
	 * 		plant has an illegal position
	 * @Post The position of Plant can change if there is nothing that would block his movement. Depending on if actionTimer+dt < 0.5d, the position change will be different
	 * 		| if (actionTimer+dt < 0.5d) && !(this.overlapsWithWall()[1]==true && direction.getMultiplier()<0) && !( overlapsWithWall()[3]==true && direction.getMultiplier()>0)
	 * 		|  		then new.getPositionX()== this.getPositionX() +dt*direction.getMultiplier()*horizontalVelocity
	 * 		| else
	 * 		|  	double oldDirTime = (0.5-actionTimer);
			|	actionTimer = (actionTimer+dt)-0.5d;
			|	double realMovementDt = oldDirTime - actionTimer
	 * 		| if !(actionTimer+dt < 0.5d) && !(this.overlapsWithWall()[1]==true && direction.getMultiplier()*Math.signum(realMovementDt)<0) && !( overlapsWithWall()[3]==true && direction.getMultiplier()*Math.signum(realMovementDt)>0)
	 * 		|		then new.getPositionX()==this.getPositionX() +realMovementDt*direction.getMultiplier()*horizontalVelocity)
	 * @Post When Plant reaches the borders of it's space to move in, he won't move during dt, but will change direction
	 * 		| if actionTimer+dt > 0.5d 
	 * 		|   if direction=Direction.RIGHT
	 * 		|		then new.direction=Direction.LEFT
	 * 		| 	else
	 * 		| 		new.direction=Direction.RIGHT  	 
	 */
	
	public void moveHorizontal(double dt) throws PositionOutOfBoundsException{
		if(actionTimer+dt < 0.5d){
			actionTimer += dt;
			double oldPositionX = getPositionX();
			double newPositionX = oldPositionX +dt*direction.getMultiplier()*horizontalVelocity;
			setPositionX(newPositionX);
			if(this.overlapsWithWall()[1]==true && direction.getMultiplier()<0){
				setPositionX(oldPositionX);
			}
			if( overlapsWithWall()[3]==true && direction.getMultiplier()>0){
				setPositionX(oldPositionX);
			}
		}else{
			double oldDirTime = (0.5-actionTimer);
			actionTimer = (actionTimer+dt)-0.5d;
			double realMovementDt = oldDirTime - actionTimer;//can be negative, this means moving to the new side instead of the old one.
			
			//make the movement
			double oldPositionX = getPositionX();
			setPositionX(oldPositionX +realMovementDt*direction.getMultiplier()*horizontalVelocity);
			if(this.overlapsWithWall()[1]==true && direction.getMultiplier()*Math.signum(realMovementDt)<0){
				setPositionX(oldPositionX);
			}
			if( overlapsWithWall()[3]==true && direction.getMultiplier()*Math.signum(realMovementDt)>0){
				setPositionX(oldPositionX);
			}
			
			//change the direction
			if(direction == Direction.RIGHT){
				direction = Direction.LEFT;
			}
			else{
				direction = Direction.RIGHT;
			}
		}
	}
	/**
	 * 
	 * @param alien a Mazub-character that will consume the plant
	 * @effect if Mazub can consume a plant, he will gain 50Hp and the plant will lose 1Hp
	 * 			|if alien.hasMaxHp()==false && terminated == false
				|	then alien.addHp(50)
				|		 loseHp(1)

	 */
	public void consume(Mazub alien){
		if(alien.hasMaxHp()==false && isTerminated() == false){
			alien.addHp(50);
			loseHp(1);
		}
	}
	
	/**
	 * Adds the object Plant to the given world if this is a valid world for it
	 * @effect if the given world is valid, a plant will be added
	 * 		  | if this.world == null && canHaveAsWorld(world)
	 * 		  |   then world.addPlant(this)
	 * 		  |        new.world=world
	 */
	@Override
	public void addToWorld(World world){
		if(getWorld() == null && canHaveAsWorld(world)){
			setWorld(world);
			world.addPlant(this);
		}
	}
	
	/**
	 * checks if a collision with the given gameobject has an effect
	 * @effect if the given gameobject is a Mazub then plant will be consumed
	 * 			| if(gameObject instanceof Mazub)
				|    then consume((Mazub)gameObject)
	 */
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Mazub){
			consume((Mazub)gameObject);
		}
	}

	@Override
	public void startJump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endJump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDuck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDuck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startMove(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMove(Direction direction) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isMoving(Program.Direction direction){
		switch(direction){
		case UP:
		case DOWN:
			return false;
		case LEFT:
			return this.direction== Direction.LEFT;
		case RIGHT:
			return this.direction ==Direction.RIGHT;
		}
		return false;
	}

	@Override
	public void EffectOnCollisionWithReversed(GameObject gameObject) {
		return ;
	}
}
