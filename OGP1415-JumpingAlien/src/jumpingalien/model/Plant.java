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
	private double actionTime;
	private double actionDuration=0.5d;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
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
		actionTime=0.0d;
		direction = Direction.RIGHT;
	}
	
	/**
	 * 
	 * @param x |the most left position that is part from the currently showing Sprite.
	 * @param y |the lowest position that is part of the currently showing Sprite.
	 * @param sprites |a list of Sprites that plant will use to rotate trough
	 * @param program |the program that plant should be running
	 * @throws PositionOutOfBoundsException
	 * 			plant has an illegal position
	 * 			| ! hasValidPosition()
	 * @Post The plant will be automatically moving to the right when the game starts
	 * 			| direction = Direction.RIGHT
	 */
	public Plant(int x, int y, Sprite[] sprites,Program program) throws PositionOutOfBoundsException{
		super(x,y,sprites,0,1,1,program);
		actionTime=0.0d;
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
	 * @effect an action will be picked | decideAction()
	 */
	
	@Override
	public void advanceTime(double dt)throws PositionOutOfBoundsException{
		while(dt>0 && !isTerminated()){
			decideAction();
			double smallDt;
			if(getProgram()==null)
				smallDt = Math.min(Math.min(0.01d/Math.abs(horizontalVelocity),dt),actionDuration-actionTime);
			else
				smallDt= Math.min(0.01d/Math.abs(horizontalVelocity),dt);
			actionTime += smallDt;
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
	 * 
	 * @effect | if(getProgram() == null && (actionTime>=actionDuration) ) 
	 * 		       		then endMove(direction.LEFT) || endMove(direction.RIGHT)
	 * 						 startMove(direction.RIGHT) || startMove(direction.LEFT)
	 */
	public void decideAction(){
		if(getProgram() != null){
			if(actionTime>0){
				actionTime = getProgram().executeTime(actionTime);
			}
		}else{
			if(actionTime>=actionDuration){
				actionTime=0.0d;
				if(direction == Direction.LEFT){
					endMove(Direction.LEFT);
					startMove(Direction.RIGHT);
				}else{
					endMove(Direction.RIGHT);
					startMove(Direction.LEFT);
				}
			}
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
		//if(actionTime+dt < 0.5d){
			actionTime += dt;
			double oldPositionX = getPositionX();
			double newPositionX = oldPositionX +dt*direction.getMultiplier()*horizontalVelocity;
			setPositionX(newPositionX);
			if(this.overlapsWithWall()[1]==true && direction.getMultiplier()<0){
				setPositionX(oldPositionX);
			}
			if( overlapsWithWall()[3]==true && direction.getMultiplier()>0){
				setPositionX(oldPositionX);
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
	public void consume(GameObject alien){
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
		else{
			if(!(gameObject instanceof Plant || gameObject instanceof Shark || gameObject instanceof Slime))
					gameObject.EffectOnCollisionWithReversed(this);
		}
	}

	@Override
	public void startJump() {}

	@Override
	public void endJump() {}

	@Override
	public void startDuck() {}

	@Override
	public void endDuck() {}

	/**
	 * 
	 * start the movement in a given direction
	 * 
	 */
	
	@Override
	public void startMove(Direction direction) {
		this.direction=direction;
		
	}

	/**
	 * 
	 * ends the movement in a given direction
	 * 
	 */
	
	@Override
	public void endMove(Direction direction) {
		assert direction != null && direction != Direction.STALLED;
		if(direction==Direction.RIGHT){
			movingRight = false;
			if(movingLeft){
				this.direction=Direction.LEFT;
			}else{
				this.direction = Direction.STALLED;
			}
		}else{
			movingLeft=false;
			if(movingRight){
				this.direction=Direction.RIGHT;
			}else{
				this.direction = Direction.STALLED;
			}
		}
		this.direction=Direction.STALLED;
		
	}
	
	/**
	 * checks if Plant is moving in the given direction
	 * @param direction
	 * @return  Math.signum(getVerticalVelocity())==direction.getSign() || return Math.signum(getHorizontalVelocity())==direction.getSign()
	 *			|| false
	 */
	
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

	/**
	 * checks the consequences of a collision between this object and the given object. This method is only used when 
	 * the class doesn't recognise this gameobject because it is added before Mazub.
	 * Since Plant is a character that every class knows, nothing needs to be done here.
	 */
	
	@Override
	public void EffectOnCollisionWithReversed(GameObject gameObject) {
		System.out.println("unknown type of gameobject");
		return ;
	}
	
	@Override
	public String toString(){
		return "using program: "+(getProgram() != null);
	}
}
