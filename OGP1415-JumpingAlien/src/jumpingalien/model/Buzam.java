package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.state.Direction;
import jumpingalien.state.DuckState;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;

/**
 * Buzam is a class representing a character/GameObject of the game. 
 * @author Meerten Wouter & Michiels Thomas (both 2de fase ingenieurswetenschappen)
 * @version 1.0
 * @Invar 	the position of the lower left pixel will always be valid. 
 * 			This means it will never be outside of the boundaries of the game.
 * 			|hasValidPosition()
 * @Invar	both the groundState, the Orientation and the duckState have a valid value at
 * 			all time in the game.
 * 			|isValidState(this.getOrientation(),this.getGroundState(),this.getDuckState())
 * @Invar 	at all time in the game, the acceleration and velocity must have valid values
 * 			|Double.isNaN(this.getHorizontalVelocity())==false
 * 			|Double.isNaN(this.getVerticalVelocity())==false
 * 			|Double.isNaN(this.getHorizontalAcceleration())==false
 * 			|Double.isNaN(this.getVerticalAcceleration())==false
 * @Invar	the currentSpriteNumber must always be valid
 * 			|isValidSpriteNumber(currentSpriteNumber)
 */

public class Buzam extends GameObject{
	private double horizontalVelocity;
	private final static double horizontalAcceleration = 0.9d;
	private final static double verticalAcceleration = -10d;
	private double verticalVelocity;
	private double maxHorizontalVelocity;
	private final double initialHorizontalVelocity;
	private final double initialVerticalVelocity =8d;
	private Direction direction ;
	private GroundState groundState;
	private DuckState duckState;
	private double timeSinceLastAnimation;
	private double timeSinceLastMovement;
	private Direction lastMovingDirection = Direction.STALLED;
	private double maxDuckingVelocity = 1.0d;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private double actionTime = 0.0d;
	

	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that buzam will use to rotate trough, to make
	 * 						|animations.
	 * @throws PositionOutOfBoundsExeption 
	 * 			buzam has an illegal position (error can be in X and/or Y position)
	 * 			| !hasValidPosition()
	 * @Post	if the Buzam instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 * @Post  Buzam will be automatically standing still and not ducking when the game starts 
	 * 			| new.direction == Direction.STALLED
	 * 			| new.duckstate == DuckState.STRAIGHT
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites,0,500,500);
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
		horizontalVelocity=0.0d;
		verticalVelocity = 0.0d;
	}
	
	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that mazub will use to rotate trough, to make
	 * 						|animations. 
	 * @param program		|the program that the Buzam istance will run
	 * @Post	if the Buzam instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 * @Post  Buzam will be automatically standing still and not ducking when the game starts 
	 * 			| new.direction == Direction.STALLED
	 * 			| new.duckstate == DuckState.STRAIGHT
	 */

	
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites,Program program) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites,0,500,500,program);
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
		horizontalVelocity=0.0d;
		verticalVelocity = 0.0d;
	}
	
	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that buzam will use to rotate trough, to make
	 * 						|animations. 
	 * @param program 		|the program that the Buzam istance will run
	 * @param minHp			|the minimum number of Hp	
	 * @param maxHp			|the maximmum number of Hp
	 * @param currentHp		|the current number of Hp
	 * @Post	if the Buzam instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 * @Post  Buzam will be automatically standing still and not ducking when the game starts 
	 * 			| new.direction == Direction.STALLED
	 * 			| new.duckstate == DuckState.STRAIGHT
	 */
	
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites,Program program,int minHp,int currentHp,int maxHp) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites,minHp,currentHp,maxHp,program);
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
		horizontalVelocity=0.0d;
		verticalVelocity = 0.0d;
	}
	
	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that buzam will use to rotate trough, to make
	 * 						|animations. 
	 * @param minHp			|the minimum number of Hp	
	 * @param maxHp			|the maximmum number of Hp
	 * @param currentHp		|the current number of Hp
	 * @Post	if the buzam instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 * @Post  Buzam will be automatically standing still and not ducking when the game starts 
	 * 			| new.direction == Direction.STALLED
	 * 			| new.duckstate == DuckState.STRAIGHT
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites,int minHp,int maxHp,int currentHp) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites,minHp,maxHp,currentHp);
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
		horizontalVelocity=0.0d;
		verticalVelocity = 0.0d;
	}
	

	/**
	 * 
	 * @param dt	|the time passed since the last frame.
	 * @throws IllegalTimeException
	 * 			the time passed since the last frame is illegal.
	 * 			|dt <0 || dt > 0.2 || Double.NaN == dt
	 * @throws IllegalMazubStateException
	 * 			the current State of buzam is null.
	 * 			|getGroundState()==null
	 * 			|getOrientation()==null
	 * 			|getDuckState()==null
	 * @throws IllegalMovementException
	 * 			one of the parameters has had an overflow
	 * 			|overflowException()
	 * @throws IllegalMovementException
	 * 			trying to divide by 0
	 * 			|newSpeed*dirSign > this.getMaxHorizontalVelocity() && this.getHorizontalAcceleration()==0
	 * @throws PositionOutOfBoundsException
	 * 			buzam has an illegal position
	 * 			| ! isValidPosition()
	 * @Post	if buzam isn't standing still and the horizontal velocity is less than the initial velocity, his velocity will be set to the initial velocity
	 * 			| while(dt>0 && !isTerminated())
	 * 			|	if(getOriëntation()!= Direction.STALLED && getHorizontalVelocity()*getOriëntation().getMultiplier()<initialHorizontalVelocity){
	 *			|			then new.getHorizontalVelocity()==initialHorizontalVelocity*direction.getMultiplier()
	 * @Post 	if buzam isn't overlapping with a wall, it's y-position will be changed and buzam will be in the air. If it is overlapping, his position won't change 
	 * 			and his vertical velocity will be set to 0. Depending on if buzam is overlapping with a wall beneath itself, the groundstate will change to grounded 
	 * 			(if it is beneath) or in the air (if not).
	 * 			|while(dt>0 && !isTerminated())
	 * 			|	double correctDt=this.calculateCorrectDt(dt)
	 * 			|	double new_position_y = this.moveVertical(correctDt)
				|	Position oldPosition = this.getPosition()
				|	new.getPositionY() == new_position_y
				|	if (this.overlapsWithWall()[0]==true && getVerticalVelocity()<0.0d)
				|		then new.getVerticalVelocity()==0.0d
				|			 new.groundState == GroundState.GROUNDED;
				|			 new.getPositionY()== oldPosition.getPositions()[1]-0.01d
				| 	else  
				|		if (this.overlapsWithWall()[0]==false)
				|			new.groundState = GroundState.AIR
				|	if(overlapsWithWall()[2]== true && getVerticalVelocity()>0.0d)
				|		new.getVerticalVelocity()==0.0d
				|		new.getPositionY()==oldPosition.getPositions()[1]
	 * @Post 	if buzam isn't overlapping with a wall, it's x-position will be changed. If it is overlapping with a wall, 
	 * 			it's horizontal velocity will be set to 0 and it's position will remain the same		
	 * 			|while(dt>0 && !isTerminated())
	 * 			|	double correctDt=this.calculateCorrectDt(dt)
	 * 			|	double new_position_x = this.moveHorizontal(correctDt)
	 * 			|	Position oldPosition = this.getPosition()
	 * 			|	this.setPositionX(new_position_x);
				|		if(this.overlapsWithWall()[1]==true && getHorizontalVelocity()<0)
				|			new.getHorizontalVelocity()==0.0d
				|			new.getPositionX()==oldPosition.getPositions()[0]
				|		if( overlapsWithWall()[3]==true && getHorizontalVelocity()>0){
				|			new.getHorizontalVelocity()==0.0d
				|			new.getPositionX() == oldPosition.getPositions()[0]
	 * @Post 	if Buzam collides with a Shark or Slime or Mazub, his movement will be blocked (the change of position will be undone) and his velocity set to 0
	 * 			|while(dt>0 && !isTerminated())
	 * 			|	for(GameObject gameObject:getOverlappingGameObjects()){
				|		if(gameObject instanceof Slime || gameObject instanceof Shark || gameObject instanceof Mazub){
				|			new.getPositionX()== oldPosition.getPositions()[0]
				|			new.getPositionY()==oldPosition.getPositions()[1]
				|			boolean[] sides = sideOverlappingBetween(gameObject);
				|			if(sides[1]){
				|				new.getVerticalVelocity()==0.0d
	 *			|				new.groundState=GroundState.GROUNDED
	 *
	 * @effect	the position,velocity,acceleration and State from buzam will be updated according to the physics over a span from dt seconds.
	 * 			|moveHorizontal()
	 * 			|moveVertical()
	 * @effect	the shown Sprite is updated according to the changed state of Buzam.
	 * 			|animate(0.0d)
	 * 			|animate(dt)
	 * @effect  Buzam may be willing to end ducking
	 * 			|executeEndDuck()
	 * @effect  If Buzam collides with a gameObject, it will be checked if there are any consequences
	 * 			|for(GameObject gameObject:getOverlappingGameObjects())
	 * 			|	EffectOnCollisionWith(gameObject)
				|	gameObject.EffectOnCollisionWith(this)
	 * @effect  If Buzam is in lava or water, he will lose Hp
	 * 			|if isInLava() 
				| 	then loseHp(50)
				|if isInWater
				|	then loseHp(2)
	 */			
	public void advanceTime(double dt)throws PositionOutOfBoundsException, IllegalMazubStateException, IllegalTimeException,IllegalMovementException{
		double dt2 = dt;
		while(dt>0 && !isTerminated()){
			if(getProgram() != null){
				if(actionTime>0){
					actionTime = getProgram().executeTime(actionTime);
					//actionTime = getProgram().executeTime(0.002d);
				}
			}
			double correctDt=this.calculateCorrectDt(dt);
			actionTime+=correctDt;
			dt -= correctDt;
			imunityTime = Math.max(0, imunityTime - correctDt);
			animate(0.0d);//make the ducking animations etc all right, advancing in walking etc only in end, (otherwise, there's inaccuracy with the timers)
			//the animation is only shown in the end. Size for eg ducking, is already changed
			if(getOriëntation()!= Direction.STALLED && getHorizontalVelocity()*getOriëntation().getMultiplier()<initialHorizontalVelocity){
				setHorizontalVelocity(initialHorizontalVelocity*direction.getMultiplier());
			}
			double new_position_x = this.moveHorizontal(correctDt);// return new Position(x,y) ipv void
			double new_position_y = this.moveVertical(correctDt);
			Position oldPosition = getPosition();
			this.setPositionY(new_position_y);
			 
			// check if character overlaps with a wall above or beneath it 
			if (this.overlapsWithWall()[0]==true && getVerticalVelocity()<0.0d){
				this.setVerticalVelocity(0.0d);
				groundState = GroundState.GROUNDED;
				setPositionY(oldPosition.getPositions()[1]-0.01d);
			}else{
				if(this.overlapsWithWall()[0]==false){
					groundState = GroundState.AIR;
				}
			}
			if(overlapsWithWall()[2]== true && getVerticalVelocity()>0.0d){
				setVerticalVelocity(0.0d);
				setPositionY(oldPosition.getPositions()[1]);
			}
			
			this.setPositionX(new_position_x);
			if(this.overlapsWithWall()[1]==true && getHorizontalVelocity()<0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			if( overlapsWithWall()[3]==true && getHorizontalVelocity()>0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			executeEndDuck();
			for(GameObject gameObject:getOverlappingGameObjects()){
				if(gameObject instanceof Slime || gameObject instanceof Shark || gameObject instanceof Mazub){
					setPositionX(oldPosition.getPositions()[0]);
					setPositionY(oldPosition.getPositions()[1]);
					boolean[] sides = sideOverlappingBetween(gameObject);
					if(sides[1]){
						setVerticalVelocity(0.0d);
						groundState=GroundState.GROUNDED;
					}
				}//don't bounce with plants
				EffectOnCollisionWith(gameObject);
				gameObject.EffectOnCollisionWith(this);
			}
			if(isInLava()){
				lastLavaHit -= correctDt;
				if(lastLavaHit <= 0){
					loseHp(50);
					lastLavaHit = 0.2d;
				}
			}else
				lastLavaHit=0.0d;
			if(isInWater()){
				lastWaterHit -= correctDt;
				if(lastWaterHit <= 0){
					loseHp(2);
					lastWaterHit = 0.2d;
				}
			}else{
				lastWaterHit =0.2d;
			}
		}
		this.animate(dt2);
	}
	
	
	/**
	 * 
	 * @param dt | The period of time from which, if necessary, needs to be taken a small part
	 * @return the correct dt. This is the dt that makes sure that if Buzam is moving, he will have moved 1 pixel after this dt.
	 * 			|Math.min(Math.min(Math.min(Math.min(0.01d/Math.abs(getHorizontalVelocity()),0.01d/Math.abs(getVerticalVelocity()))
	 * 			 , Math.abs((-getHorizontalVelocity() + Math.sqrt(Math.pow(getHorizontalVelocity(), 2)-2*getHorizontalAcceleration()/100))/getHorizontalAcceleration()))
	 * 			 , Math.abs((-getVerticalVelocity() + Math.sqrt(Math.pow(getVerticalVelocity(), 2)-2*getVerticalAcceleration()/100))/getVerticalAcceleration()))
	 * 			 , dt)
	 */
	public double calculateCorrectDt(double dt) {
		double min1;double min2;double min3;double min4; // de 4 mogelijke situaties
		if (getVerticalVelocity()==0 && getHorizontalVelocity()==0
				&& getVerticalAcceleration()==0 && getHorizontalAcceleration()==0)
			return dt;
		else{
			if(getHorizontalVelocity()!=0.0d)//mogelijkheid 1
				min1 = 0.01d/Math.abs(getHorizontalVelocity());
			else
				min1 = Float.POSITIVE_INFINITY;
			
			if (getVerticalVelocity()!=0.0d)
				min2 = 0.01d/Math.abs(getVerticalVelocity());
			else 
				min2=Float.POSITIVE_INFINITY;
			
			if (this.getHorizontalAcceleration()!=0.0d){//mogelijkheid 3
				min3 = Math.abs((-getHorizontalVelocity() + Math.sqrt(Math.pow(getHorizontalVelocity(), 2)-2*getHorizontalAcceleration()/100))/getHorizontalAcceleration());
			}else
				min3=Float.POSITIVE_INFINITY;
			
			if (this.getVerticalAcceleration()!=0.0d)
				min4 = Math.abs((-getVerticalVelocity() + Math.sqrt(Math.pow(getVerticalVelocity(), 2)-2*getVerticalAcceleration()/100))/getVerticalAcceleration());
			else 
				min4=Float.POSITIVE_INFINITY;
			return Math.min(Math.min(Math.min(Math.min(min1,min2), min3),min4),dt); // mag geen NaN bevatten
		}
	}
	
	/**
	 * 
	 * @param dt | The period of time that the character needs to move
	 * @return The new x-position of the character after he has moved for dt seconds
	 * @throws IllegalMovementException
	 * 			one of the parameters has had an overflow
	 * 			|overflowException()
	 * @throws IllegalMovementException
	 * 			trying to divide by 0
	 * 			|newSpeed*dirSign > this.getMaxHorizontalVelocity() && this.getHorizontalAcceleration()==0
	 * @throws PositionOutOfBoundsException
	 * 			(A part of) the character isn't located within the boundaries of the world
	 * 			| ! hasValidPosition()
	 * @Post the horizontal velocity of buzam will be set to the correct new velocity if this isn't more than the maximum allowed velocity. Else it will be set to the maximum velocity
	 * 			| if ! (newSpeed*dirSign > this.getMaxHorizontalVelocity())
	 * 			| 	then new.getHorizontalVelocity()==this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt
	 * 			| else
	 * 			| 	new.getHorizontalVelocity()==this.getMaxHorizontalVelocity()
	 */
	public double moveHorizontal(double dt) throws IllegalMovementException,PositionOutOfBoundsException{
		int dirSign =this.direction.getMultiplier(); 
		double newSpeed = this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt;
		double s;
		//dirsign is used in here to compensate for the current direction of the buzam.
		if(newSpeed*dirSign > this.getMaxHorizontalVelocity()){//overgangsverschijnsel (1keer bij berijken max speed)
			if(getHorizontalAcceleration()==0)throw new IllegalMovementException("impossible to divide by zero");
			double accDt = Math.max(0,(this.getMaxHorizontalVelocity()- this.getHorizontalVelocity()*dirSign)/(getHorizontalAcceleration()*dirSign));
			s= travelledHorizontalDistance(accDt)+getMaxHorizontalVelocity()*(dt-accDt)*dirSign;
			this.setHorizontalVelocity(this.getMaxHorizontalVelocity()*dirSign);
		}
		else{
			s= travelledHorizontalDistance(dt);
			this.setHorizontalVelocity(newSpeed);
		}
		if(((getPositionX()+s <=0d || s<0)&& dirSign>0 )|| (s>0 && dirSign<0)){
			//System.out.println(s +","+ dirSign+","+getPositionX());
			//System.out.println(dt+","+newSpeed);
			throw new IllegalMovementException("positionX overflowed");
		}
		//correct position if out of window
		if(getPositionX()+s <0){
			return 0.0d;
			//setPositionX(0); //ELKE SITUATIE VERANDEREN HE
		}
		if(getPositionX()+s>(getWorld().getWidth()-1)/100d)
			return (getWorld().getWidth()-1)/100.0d;
		return getPositionX()+s;
	} 
	
	/**
	 * 
	 * @param dt | The period of time that the character needs to move
	 * @return The new y-position of the character after he has moved for dt seconds
	 * @throws PositionOutOfBoundsException
	 * 			(A part of) the character isn't located within the boundaries of the world
	 * 			| ! hasValidPosition()
	 * @Post the vertical velocity of buzam will be set to the correct new velocity if buzam isn't standing on the bottom perimeter of the world. 
	 * 		 Else it will be set to 0 and it's groundstate will be set to grounded
	 * 			| if ! (newPositiony<0)
	 * 			| 	then new.getHorizontalVelocity()==this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt
	 * 			| else
	 * 			| 	new.getVerticalVelocity()==0
	 * 			|	new.groundState==Groundstate.GROUNDED
	 */
	
	public double moveVertical(double dt)throws PositionOutOfBoundsException{
		//update position and speed (still need to compensate for velocity over max first time)
		int stateSign =this.groundState.getMultiplier(); 
		double newSpeed = this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign;
		
		double newPositiony = getPositionY() + travelledVerticalDistance(dt,stateSign);
		//doSomething(dt, stateSign);
		if(newPositiony < 0){
			if(getVerticalVelocity()<=0.0d){
					this.groundState = GroundState.GROUNDED;
					setVerticalVelocity(0.0d);
				}
			return 0.0d;
		}else{
			if(newPositiony>(getWorld().getHeight()-1)/100.0d){
				this.setVerticalVelocity(newSpeed);
				return ((getWorld().getHeight()-1)/100.0d);
			}//else{
				//throw new PositionOutOfBoundsException(getPositionX(), getPositionY());
			//}
		}
		this.setVerticalVelocity(newSpeed);
		return newPositiony;
	}
	
	/**
	 * calculates the movement over a given period of time according to the horizontal axis.
	 * @return this.getHorizontalVelocity()*dt + this.getHorizontalAcceleration()*Math.pow(dt, 2)/2
	 */
	private double travelledHorizontalDistance(double dt){
		return this.getHorizontalVelocity()*dt +
				this.getHorizontalAcceleration()*Math.pow(dt, 2)/2;
	}
	/**
	 * calculates the movement over a given period of time according to the vertical axis.
	 * @return this.getVerticalVelocity()*dt*stateSign + this.getVerticalAcceleration()*Math.pow(dt, 2)/2;
	 */
	private double travelledVerticalDistance(double dt, int stateSign){
		return this.getVerticalVelocity()*dt*stateSign +
				this.getVerticalAcceleration()*Math.pow(dt, 2)/2;
	}
	
	/**
	 * checks the current state from the buzam and changes the shown sprite accordingly.
	 * @param dt | the period of time that elapses 
	*/
	public void animate(double dt){
		timeSinceLastAnimation += dt;
		if(getOriëntation() == Direction.STALLED){
			timeSinceLastMovement += dt;
		}else{
			timeSinceLastMovement = 0;
		}
		if(getOriëntation() == Direction.STALLED){
			if(timeSinceLastMovement>=1){
				if(duckState == DuckState.STRAIGHT){
					setCurrentSprite(0);
				}else{
					setCurrentSprite(1);
				}
			}else{
				if(duckState == DuckState.STRAIGHT){
					setCurrentSprite(2- (lastMovingDirection.getMultiplier()-1)/2);
				}else{
					setCurrentSprite(6 - (lastMovingDirection.getMultiplier()-1)/2);
				}
			}
		}else{
			if(groundState == GroundState.AIR && duckState == DuckState.STRAIGHT){
				setCurrentSprite(4- (getOriëntation().getMultiplier()-1)/2);
			}else{
				if(duckState == DuckState.DUCKED || duckState == DuckState.TRY_STRAIGHT){
					setCurrentSprite(6- (getOriëntation().getMultiplier()-1)/2);
				}else{
					if((getCurrentSpriteNumber()<8) || (getOriëntation() == Direction.LEFT && getCurrentSpriteNumber() <8+m) || (getOriëntation()==Direction.RIGHT && getCurrentSpriteNumber() >= 8+m)){
						timeSinceLastAnimation =0;
						setCurrentSprite(8 - (getOriëntation().getMultiplier()-1)*m/2);
					}else{
						if(timeSinceLastAnimation >= 0.075){
							timeSinceLastAnimation =0;
							setCurrentSprite((getCurrentSpriteNumber()-(8 - (getOriëntation().getMultiplier()-1)*m/2) + 1)%m+8 - (getOriëntation().getMultiplier()-1)*m/2);
						}
					}
				}
			}
		}
		return;
	}
	
	/**
	 *
	 * @return	the horizontal velocity.
	 * 			|this.horizontalVelocity
	 */
	@Basic
	public double getHorizontalVelocity(){
		return horizontalVelocity;
	}
	
	/**
	 * @param velocity	|the new velocity in the horizontal direction (if not greater then getMaximumHorizontalVelocity())
	 * @post the new velocity of the character is equal to the given velocity (or to the maximum velocity)
	 * 		 |if(Math.abs(velocity)>getMaxHorizontalVelocity())
	 * 		 |  then new.getHorizontalVelocity() = this.getMaxHorizontalvelocity()*Math.signum(horizontalVelocity)
	 * 		 |else 
	 * 		 |  new.getHorizontalVelocity() = velocity
	 * 		
	 * 
	 */
	private void setHorizontalVelocity(double velocity){
		if(Math.abs(velocity)>getMaxHorizontalVelocity()){
			this.horizontalVelocity=getMaxHorizontalVelocity()*Math.signum(horizontalVelocity);
		}
		else{
			this.horizontalVelocity = velocity;
		}
		
	}
	
	/**
	 * returns the current maximum horizontal velocity
	 * @return	|if duckState=DuckState.STRAIGHT
	 * 			|	then this.maxHorizontalVelocity
	 * 			|else this.maxDuckingVelocity
	 */
	
	public double getMaxHorizontalVelocity(){
		if(duckState == DuckState.STRAIGHT){
			return this.maxHorizontalVelocity;
		}else{
			return this.maxDuckingVelocity;
		}
		
	}
	

	/**
	 * returns the current acceleration of buzam according to the vertical direction
	 * @return |if getHorizontalVelocity()*getOrientation().getMultiplier()==getMaxHorizontalVelocity() || getHorizontalVelocity == 0.0d
	 * 		   |	then  0
	 * 		   |else horizontalAcceleration* getOrientation().getMultiplier()
	 * 				
	 */
	@Basic
	public double getHorizontalAcceleration(){
		if(getHorizontalVelocity()*getOriëntation().getMultiplier()==getMaxHorizontalVelocity() || getHorizontalVelocity()==0.0d)
			return 0;
		return Buzam.horizontalAcceleration*getOriëntation().getMultiplier();
	}
	
	/**
	 * returns the current acceleration of buzam according to the vertical direction
	 * @return this.verticalAcceleration*groundState.getMultiplier()
	 */
	@Basic
	public double getVerticalAcceleration(){
		return Buzam.verticalAcceleration* groundState.getMultiplier();
	}
	
	/**
	 * returns the current velocity of buzam according to the horizontal direction
	 * @return	|this.verticalVelocity
	 */
	@Basic
	public double getVerticalVelocity(){
		return this.verticalVelocity;
	}
	
	/**
	 * @param velocity	|the new velocity of buzam according to the y-axis
	 * @post	buzam will have the vertical velocity that is passed on to this function
	 * 			|new.getVerticalVelocity() == velocity
	 */
	private void setVerticalVelocity(double velocity){
		this.verticalVelocity = velocity;
	}
	
	/**
	 * returns the direction buzam is facing. If he is facing up front, the returned value will be Direction.STALLED.
	 * @return this.direction
	 */
	@Basic
	public Direction getOriëntation(){
		return this.direction;
	}
	
	/**
	 * 
	 * @param   dir	|the direction buzam is currently facing
	 * @Pre		the given Direction dir cannot be empty (null), neither can it be Direction.STALLED.
	 * 			|(dir != null)&& (dir != Direction.STALLED)
	 * @Post	buzam is ready to move in the given direction when time advances.
	 * 			|new.getHorizontalVelocity()== this.initialHorizontalVelocity*dir.getSign()
	 * 			|new.getOrientation() == dir
	 */
	public void startMove(Direction dir){
		assert dir != null && dir != Direction.STALLED;
		assert this.initialHorizontalVelocity>=0;
		if(dir == Direction.RIGHT)
			movingRight=true;
		else
			movingLeft=true;
		this.direction = dir;
		this.lastMovingDirection = dir;
		this.setHorizontalVelocity(this.initialHorizontalVelocity*dir.getMultiplier());
		return;
	}
	
	/**
	 * 
	 * @param dir	|the direction in which you want to stop moving
	 * @Pre		the given Direction dir cannot be empty (null), neither can it be Direction.STALLED.
	 * 			|(dir != null)&& (dir != Direction.STALLED)
	 * @Post	if the given direction is the direction in which buzam is moving, then buzam will not move when time advances.
	 * 			|if(this.getOrientation()==dir):
	 * 			|	then	new.getHorizontalVelocity() == 0
	 * 			|			new.getOrientation == Direction.STALLED
	 */
	public void endMove(Direction dir){
		assert dir != null && dir != Direction.STALLED;
		if(dir==Direction.RIGHT){
			movingRight = false;
			if(movingLeft){
				this.setHorizontalVelocity(this.initialHorizontalVelocity*Direction.LEFT.getMultiplier());
				this.direction=Direction.LEFT;
			}else{
				this.setHorizontalVelocity(0.0d);
				this.direction = Direction.STALLED;
			}
		}else{
			movingLeft=false;
			if(movingRight){
				this.setHorizontalVelocity(this.initialHorizontalVelocity*Direction.RIGHT.getMultiplier());
				this.direction=Direction.RIGHT;
			}else{
				this.setHorizontalVelocity(0.0d);
				this.direction = Direction.STALLED;
			}
		}
	}
	
	/**
	 * @throws IllegalMazubStateException
	 * 			the grounState of buzam is the illegal value null
	 * 			|this.getGroundState()==null
	 * @Post	if buzam is on the ground, he'll get an upward velocity
	 * 			|if(this.getGroundState() == GroundState.GROUNDED
	 * 			| then 	new.getVerticalVelocity() == this.initialVerticalVelocity
	 * @Post	buzam will be noted as "being in the air". 
	 * 			(if he isn't in the air, he jumps into it, if he is already in the air, hes state is already "in the air")
	 * 			|new.getGroundState() == GroundState.AIR
	 */
	public void startJump()throws IllegalMazubStateException{
		if(getGroundState() == null) throw new IllegalMazubStateException(this);
		if(this.getGroundState()==GroundState.GROUNDED ){
			this.setVerticalVelocity(this.initialVerticalVelocity);
			this.groundState = GroundState.AIR;
		}
		return;
	}
	
	/**
	 * @Post	if buzam has an upward velocity, this will be set 0.
	 * 			|if(this.getVerticalVelocity() > 0
	 * 			|	then	new.getVerticalVelocity() == 0
	 */
	public void endJump(){
		if(this.getVerticalVelocity()>0){
			this.setVerticalVelocity(0.0d);
		}
		return;
	}
	
	/**
	 * lets buzam duck
	 * @Post	buzam will duck when time advances
	 * 			|new.getDuckState() = DuckState.DUCKED
	 */
	public void startDuck(){
		this.duckState = DuckState.DUCKED;
		return;
	}
	
	/**
	 * lets buzam end with ducking
	 * @Post	buzam will try to stand up (or stay straight if he was already staying straight)
	 * 			|new.getDuckState() == DuckState.TRY_STRAIGHT
	 * @effect  buzam will try to stand up
	 * 			|executeEndDuck()
	 */
	public void endDuck(){
		//dit moet blijven
		duckState = DuckState.TRY_STRAIGHT;
		executeEndDuck();
	}
	/**
	 * @Post	if buzam wants to stand up, he will if possible. Otherwise he will stay ducked
	 * 			 |if(duckState == DuckState.TRY_STRAIGHT)
	 * 			 |	if OverlapsWithWall()[2] = false
	 * 			 | 		 then new.getduckState= DuckState.STRAIGHT
	 */
	public void executeEndDuck(){
		if(duckState == DuckState.TRY_STRAIGHT){
			int oldSprite = getCurrentSpriteNumber();
			setCurrentSprite(0);
			if(overlapsWithWall()[2]==false)
				duckState = DuckState.STRAIGHT;
			setCurrentSprite(oldSprite);
		}
	}
	
	/**
	 * returns the index of the most left pixel used by buzam. Each pixel represents 0.01m
	 * @throws PositionOutOfBoundsException
	 * 			buzam has an illegal position (error can also be in Y position)
	 * 			| !hasValidPosition()
	 * @return	|(int)this.positionX*100
	 */
	
	public int getPixel_x()throws PositionOutOfBoundsException{
		if(!hasValidPosition()) throw new PositionOutOfBoundsException(getPositionX(),getPositionY(),getWorld());
		return (int)(this.getPositionX()*100);//
	}
	
	/**
	 * 
	 * @return Checks if the position of the character is within the allowed boundaries of the world
	 * 			| ! ((getPositionX()<0 || getPositionX() >= (world.getWidth())/100.0d) ||
				|   (getPositionY()<0 || getPositionY() >= (world.getHeight())/100.0d))
	 */
	
	public boolean hasValidPosition(){
		return ! (getPositionX()<0 || getPositionX() >= (getWorld().getWidth())/100.0d) ||
				(getPositionY()<0 || getPositionY() >= getWorld().getHeight()/100.0d);
	}
	/**
	 * returns the index of the lowest pixel used by buzam. Each pixel represents 0.01m
	 * @throws PositionOutOfBoundsException
	 * 			buzam has an illegal position (error can also be in X position)
	 * 			| !hasValidPosition()
	 * @return	the y pixel |(int)this.positionY*100
	 */
	
	public int getPixel_y() throws PositionOutOfBoundsException{
		if(! hasValidPosition()) throw new PositionOutOfBoundsException(getPositionX(),getPositionY(),getWorld());
		return (int)(getPositionY()*100);
	}
	
	/**
	 * checks if Buzam has a valid state of direction, groundstate en duckstate
	 * @return (direction != null && groundState != null && duckState != null)
	 */
	private boolean isValidState(Direction direction,GroundState groundState,DuckState duckState){
		return direction != null && groundState != null && duckState != null;
	}
	
	/**
	 * returns the current GroundState of buzam. This can be GROUNDED or AIR.
	 * @return	|this.groundState
	 */
	@Basic
	public GroundState getGroundState(){
		return this.groundState;
	}
	
	/**
	 * returns the current DuckState of buzam. this can be STRAIGHT or DUCKED
	 * @return	|this.DuckState()
	 */
	@Basic
	public DuckState getDuckState(){
		return this.duckState;
	}
	
	/**
	 * checks if the collision with a given gameobject has an effect
	 * @effect if buzam isn't immune to the gameobject and if it doesn't overlap on top of it, it will lose Hp and it will have an imunityTime of 0.6 seconds
	 * 		   if it collides with an unknown object (e.g. an object that is added later on) the effect of the collision will be checked in the class of the unknown object
	 * 			|if !Immune() && if(getPerimeters()[1]<gameObject.getPerimeters()[3])
	 * 			|  then loseHp(50)
	 * 			|		new.imunityTime = 0.6d
	 * 			|else
	 * 			|  then gameObject.EffectOnCollisionWithReversed(this)
	 * 			
	 */
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Shark || gameObject instanceof Slime || gameObject instanceof Mazub){
			if(!isImmune()){
				if(getPerimeters()[1]<gameObject.getPerimeters()[3]){
					//indien bazum niet boven zijn vijand staat (na de botsing) zal hij damage nemen. 
					this.loseHp(50);
					this.imunityTime = 0.6d;
				}
			}
		}
		else{
			if(!(gameObject instanceof Plant))
				gameObject.EffectOnCollisionWithReversed(this);
		}
	}
	
	/**
	 * checks if buzam is ducking
	 * @return true if buzam is ducking, false otherwise 
	 * 			| duckState == DuckState.DUCKED || duckState == DuckState.TRY_STRAIGHT
	 */
	@Override
	public boolean isDucking(){
		return duckState == DuckState.DUCKED || duckState == DuckState.TRY_STRAIGHT;
	}
	
	/**
	 * checks if buzam is jumping
	 * @return true if buzam is jumping, false otherwise 
	 * 			| (groundState != GroundState.GROUNDED && getVerticalVelocity()>0)
	 */
	
	@Override
	public boolean isJumping(){
		return groundState != GroundState.GROUNDED && getVerticalVelocity()>0;
	}
	
	/**
	 * 
	 * @param groundstate | the groundstate to which the groundstate of buzam needs to be set
	 * @Post the groundstate of buzam will be changed to the given groudstate | new.groundState == groundstate
	 */
	
	protected void setGroundState(GroundState groundstate){
		this.groundState = groundstate;
	}
	
	/**
	 * checks if buzam is moving in the given direction
	 * @return true if ... , false otherwise
	 * 			|
	 */
	public boolean isMoving(Program.Direction direction){
		switch(direction){
		case UP:
		case DOWN:
			return Math.signum(getVerticalVelocity())==direction.getSign();
		case LEFT:
		case RIGHT:
			return Math.signum(getHorizontalVelocity())==direction.getSign();
		}
		return false;
	}

	/**
	 * checks the consequences of a collision between this object and the given object. This method is only used when 
	 * the class doesn't recognise this gameobject because it is added before Buzam.
	 * @param gameObject | the gameobject with which Buzam seems to collide
	 * @effect if the gameobject is a shark or slime, the gameobject will lose 50 hp and its immuntiTime will be set to 0.6d
	 * 		   if the gameobject is a mazub and if mazub doesn't collide above Buzam, the same will be done
	 * 			|if (gameObject instanceof Shark || gameObject instanceof Slime)
				|		then gameObject.loseHp(50)
				|			 gameObject.imunityTime = 0.6d
				|else {
				|if (gameObject instanceof Mazub) && (gameObject.getPerimeters()[1]<this.getPerimeters()[3]))
				|		then gameObject.loseHp(50)
				|			 gameObject.imunityTime = 0.6d
	 */
	
	@Override
	public void EffectOnCollisionWithReversed(GameObject gameObject) {
		if (gameObject instanceof Plant){
			((Plant) gameObject).consume(this);
		}
		else{
			if(! (gameObject.isImmune()) ){
				if (gameObject instanceof Shark || gameObject instanceof Slime){
					gameObject.loseHp(50);
					gameObject.imunityTime = 0.6d;
				}
				else {
					if (gameObject instanceof Mazub){
						//It needs to be checked if mazub collides above Buzam, in this case he won't lose HP.
						if(gameObject.getPerimeters()[1]<this.getPerimeters()[3]){
							gameObject.loseHp(50);
							gameObject.imunityTime = 0.6d;
						}
					}else{
						System.out.println("unknown type of gameobject");
						System.out.println(gameObject.getClass());
					}
				}
			}
		}
	}
	
	/**
	 * Adds the character Buzam to the given world if this is a valid world for it
	 * @Post if the given world is valid, a buzam will be added
	 * 		  | if this.world == null && canHaveAsWorld(world)
	 * 		  |   then new.world = world
	 *        |        world.addBuzam(this)
	 * @Post if the character overlaps with a wall at the bottom, his groundstate will be set to GROUNDED, otherwise he is in the air
	 * 		  |if (overlapsWithWall()[0])
	 * 		  | 	then new.getGroundState()==GroundState.GROUNDED
	 * 	      |else
	 * 		  |		then new.getGroundState() == GroundState.AIR
	 */
	
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			setWorld(world);
			world.addBuzam(this);
			if(overlapsWithWall()[0]){
				setGroundState(GroundState.GROUNDED);
			}else{
				setGroundState(GroundState.AIR);
			}
		}
	}
	



}
