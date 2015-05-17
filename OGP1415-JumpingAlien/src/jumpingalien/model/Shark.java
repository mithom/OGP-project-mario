package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.state.Direction;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;

/**
 * Shark is a class representing a gameObject of the game. 
 * @author Meerten Wouter & Michiels Thomas (both 2de fase ingenieurswetenschappen)
 * @version 1.0
 * @Invar 	the position of the lower left pixel will always be valid. 
 * 			This means it will never be outside of the boundaries of the game.
 * 			|hasValidPosition()
 * @Invar	both the groundState and the Orientation have a valid value at
 * 			all time in the game.
 * 			|isValidState(this.getOrientation(),this.getGroundState())
 * @Invar 	at all time in the game, the acceleration and velocity must have valid values
 * 			|Double.isNaN(this.getHorizontalVelocity())==false
 * 			|Double.isNaN(this.getVerticalVelocity())==false
 * 			|Double.isNaN(this.getHorizontalAcceleration())==false
 * 			|Double.isNaN(this.getVerticalAcceleration())==false
 * @Invar	the currentSpriteNumber must always be valid
 * 			|isValidSpriteNumber(currentSpriteNumber)
 */

public class Shark extends GameObject{
	
	private final double horizontalAcceleration=1.5d;
	private final double maxHorizontalVelocity = 4.0d;
	private double actionTime;
	private double actionDuration;
	private double horizontalVelocity;
	private double verticalVelocity;
	private double initVerticalVelocity = 2.0d;
	private double verticalMaxRandAcceleration = 2.0d;
	private GroundState groundState;
	private Direction direction;
	private int actionNb = 0 ;
	private int lastJumpActionNb = -4 ;
	private double randomAcceleration;
	private final static double verticalAcceleration = -10.0d;
	
	/**
	 * 
	 * @param x |the most left position that is part from the currently showing Sprite.
	 * @param y |the lowest position that is part of the currently showing Sprite.
	 * @param sprites |a list of Sprites that shark will use to rotate trough
	 * @throws PositionOutOfBoundsException
	 * 			shark has an illegal position
	 * 			| ! hasValidPosition()
	 * @Post The shark will be automatically standing still when the game starts 
	 * 			| new.direction = Direction.STALLED
	 */
	public Shark(int x, int y, Sprite[] sprites)throws PositionOutOfBoundsException{
		super(x,y,sprites,0,100,100); 
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
		}
	
	public Shark(int x, int y, Sprite[] sprites,Program program)throws PositionOutOfBoundsException{
		super(x,y,sprites,0,100,100,program); 
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
		}
	//TODO Nullpointer?????
	/**
	 * @param dt	|the time passed since the last frame.
	 * @throws PositionOutOfBoundsExeption
	 * 			shark has an illegal position
	 * 				| ! hasValidPosition()
	 * @throws NullpointerException
	 * 			if double dt
	 * @throws IllegalSizeException
	 * 			The size of shark isn't a legal value
	 * @Post 	the position and velocity of shark can be changed depending on the situation. 
	 * 				|Position oldPosition = getPosition()
	 * 				|double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime)
					|new.getPositionY()==moveVertical(smallDt)
					|if ((this.overlapsWithWall()[0]==true || this.placeOverlapsWithGameObject()[1]==true) && getVerticalVelocity()<0.0d){
					|	new.getVerticalVelocity()==0.0d
					|	new.getPositionY()==oldPosition.getPositions()[1]-0.01d
					|	new.groundState = GroundState.GROUNDED;
					|else
					|	if(this.overlapsWithWall()[0]==false &&isInAir())
					|		new.groundState = GroundState.AIR
					|	if(!isInAir() && getVerticalVelocity()<0 && groundState == GroundState.AIR)
					|		new.groundState= GroundState.GROUNDED
					|		new.getVerticalVelocity()==0.0d
					|if( (overlapsWithWall()[2]==true || this.placeOverlapsWithGameObject()[3]==true) && getVerticalVelocity()>0)
					|	new.getVerticalVelocity()==0.0d
					|	new.getPositionY()==oldPosition.getPositions()[1]
					|new.getPositionX()==moveHorizontal(smallDt)
					|if((this.overlapsWithWall()[1]==true || this.placeOverlapsWithGameObject()[0]==true) && getHorizontalVelocity()<0){
					|	new.getHorizontalVelocity()==0.0d
					|	new.getPositionX()==oldPosition.getPositions()[0]
					|if( (overlapsWithWall()[3]==true || this.placeOverlapsWithGameObject()[2]==true) && getHorizontalVelocity()>0){
					|	new.getHorizontalVelocity()==0.0d
					|	new.getPositionX()==oldPosition.getPositions()[0]
	 * @Post 	if shark collides with another object (slime or mazub) the changed movemement will be undone
	 * 			|while(dt>0 && !isTerminated())
	 * 			|	for(GameObject gameObject:getOverlappingGameObjects()){
				|		if(gameObject instanceof Slime || gameObject instanceof Mazub){
				|			new.getPositionX()== oldPosition.getPositions()[0]
				|			new.getPositionY()==oldPosition.getPositions()[1]
	 * @effect	the position,velocity,acceleration and State from mazub will be update according to the physics over a span from dt seconds.
	 * 			|moveHorizontal()
	 * 			|moveVertical()
	 * @effect  If shark collides with a gameObject, it will be checked if there are any consequences
	 * 			|for(GameObject gameObject:getOverlappingGameObjects())
	 * 			|	EffectOnCollisionWith(gameObject);
				|	gameObject.EffectOnCollisionWith(this);
	 * @effect	if the time of the action is the duration of the action, a random action will be needed to be decided
	 * 			|if(actionTime == actionDuration)
	 * 			|	decideAction()
	 * @effect	the shown Sprite is updated according to the changed state of mazub.
	 * 			|double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime)
	 * 			|animate(smallDt)	
	 * @effect  If Mazub is in lava or water, he will lose Hp
	 * 			|if isInLava() 
				| 	then loseHp(50)
				|if isInAir()
				|	then loseHp(2)
	 */

	@Override
	public void advanceTime(double dt) throws PositionOutOfBoundsException, NullPointerException, IllegalSizeException{
		while(!isTerminated() && dt >0){
			if(actionTime == actionDuration){
				decideAction();
			}
			
			double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime);
			actionTime+=smallDt;
			dt-= smallDt;
			imunityTime = Math.max(0, imunityTime - smallDt);
			Position oldPosition = getPosition();
			setPositionY(moveVertical(smallDt));
			//bot
			if ((this.overlapsWithWall()[0]==true || this.placeOverlapsWithGameObject()[1]==true) && getVerticalVelocity()<0.0d){
				this.setVerticalVelocity(0.0d);
				setPositionY(oldPosition.getPositions()[1]-0.01d);
				groundState = GroundState.GROUNDED;
			}else{
				if(this.overlapsWithWall()[0]==false && isInAir()){
					groundState = GroundState.AIR;
				}
				if(!isInAir() && getVerticalVelocity()<0 && groundState == GroundState.AIR){
					groundState= GroundState.GROUNDED;
					setVerticalVelocity(0.0d);
				}
			}
			//top
			if( (overlapsWithWall()[2]==true || this.placeOverlapsWithGameObject()[3]==true) && getVerticalVelocity()>0){//TODO if elif ipv if if if
				this.setVerticalVelocity(0.0d);
				setPositionY(oldPosition.getPositions()[1]);
			}
			setPositionX(moveHorizontal(smallDt));
			//left
			if((this.overlapsWithWall()[1]==true || this.placeOverlapsWithGameObject()[0]==true) && getHorizontalVelocity()<0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			//right
			if( (overlapsWithWall()[3]==true || this.placeOverlapsWithGameObject()[2]==true) && getHorizontalVelocity()>0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			animate(smallDt);
			for(GameObject gameObject:getOverlappingGameObjects()){
				if(gameObject instanceof Slime || gameObject instanceof Mazub|| gameObject instanceof Shark){
					setPositionX(oldPosition.getPositions()[0]);
					setPositionY(oldPosition.getPositions()[1]);
				}//don't bounce with plants
				EffectOnCollisionWith(gameObject);
				gameObject.EffectOnCollisionWith(this);
			}
			if(isInLava()){
				lastLavaHit -= smallDt;
				if(lastLavaHit <= 0){
					loseHp(50);
					lastLavaHit = 0.2d;
				}
			}else
				lastLavaHit=0.0d;
			if(isInAir()){
				lastWaterHit -= smallDt;
				if(lastWaterHit <= 0){
					loseHp(2);
					lastWaterHit = 0.2d;
				}
			}else{
				lastWaterHit =0.2d;
			}
		}
	}
	

	/**
	 * 
	 * decides which random movement shark will be executing
	 * @effect if the randomacceleration is zero, shark will end jumping
	 * 		   | if randomAcceleration==0
	 * 		   |	then endJump()
	 * @effect shark will stop moving
	 * 		   |endMove()
	 * @effect depending on which random number is decided, shark will be executing a random movement. The choices are: moving without jump to the right and to the left and
	 * 		 	moving with a jump to the right and to the left
	 * 			|int nextAction;
				|	if (actionNb > (lastJumpActionNb+4) && (isBottomInWater()==true || this.overlapsWithWall()[0]==true)){
			 	|		then nextAction = rand.nextInt(4)
				|	else
				|		nextAction = rand.nextInt(2);
	 * 		   	|switch(nextAction){
			    |case 0:
				|	startMove(Direction.RIGHT, true);
				|	break;
				|case 1:
				|	startMove(Direction.LEFT, true);
				|	break;
				|case 2:
				|	startMove(Direction.RIGHT, false);
				|	startJump();
				|	break;
				|case 3:
				|	startMove(Direction.LEFT, false);
				|	startJump();
				|	break;}
	 */
	private void decideAction(){
		if(randomAcceleration==0){
			endJump();
		}
		endMove();
		Random rand = new Random();
		actionDuration = rand.nextDouble()*3.0d+1.0d;
		actionTime = 0.0d;
		int nextAction;
		if (actionNb > (lastJumpActionNb+4) && (isBottomInWater()==true || this.overlapsWithWall()[0]==true)){
			 nextAction = rand.nextInt(4);
		}
		else
			nextAction = rand.nextInt(2);
		
		switch(nextAction){
		case 0:
			startMove(Direction.RIGHT, true);
			break;
		case 1:
			startMove(Direction.LEFT, true);
			break;
		case 2:
			startMove(Direction.RIGHT, false);
			startJump();
			break;
		case 3:
			startMove(Direction.LEFT, false);
			startJump();
			break;
		default:
			System.err.println("unsupported action");
			break;
		}
		actionNb += 1 ;
	}
	
	/**
	 * shark will start moving
	 * @param dir |the direction to which shark needs to move
	 * @param withRandomAcc | whether or not there needs to be a random vertical acceleration (so true or false)
	 * @Post  the direction of shark will be changed to the given dir and in some cases there will be a random acceleration
	 * 			|new.direction == dir
	 * 			|if (isBottomInWater() && withRandomAcc)
	 * 			|	then Random rand = new Random();
				|	     randomAcceleration =  2*verticalMaxRandAcceleration*rand.nextDouble()-verticalMaxRandAcceleration
			    |else    randomAcceleration = 0.0d
	 */
	public void startMove(Direction dir,boolean withRandomAcc){
		direction = dir;
		if (!isInAir() && withRandomAcc){
			Random rand = new Random();
			randomAcceleration =  2*verticalMaxRandAcceleration*rand.nextDouble()-verticalMaxRandAcceleration;
		}else
			randomAcceleration = 0.0d;
	}
	
	/**
	 * shark will end with his movements
	 * @post the random acceleration and horizontal movement will be set to 0
	 * 			|new.randomAcceleration == 0.0d
	 * 			|new.getHorizontalVelocity() == 0.0d
	 */
	
	public void endMove(){
		randomAcceleration = 0.0d;
		setHorizontalVelocity(0.0d);
	}
	
	/**
	 * 
	 * @return returns true if the bottom of the shark is in water. If it's not, it will return false.
	 * 
	 */
	
	public boolean isBottomInWater() {
		double [] perimeters = this.getPerimeters();//order: left,bottom,right,top
		for(int i=0;i<perimeters.length;i++)perimeters[i]*=100;
		int [][] occupied_tiles = getWorld().getTilePositionsIn((int) (perimeters[0]),(int)(perimeters[1]),(int)(perimeters[2]),(int)(perimeters[3]));
		for (int i=0 ; i < occupied_tiles.length ; i++){

			if (getWorld().getGeologicalFeature(new int[]{occupied_tiles[i][0]*getWorld().getTileLength(),occupied_tiles[i][1]*getWorld().getTileLength()})==2){
				//check if tile is beneath character
				if (getWorld().getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] <= perimeters[1])
					 return true;
			}
		}
		return false;
	}
	
	/**
	 * Lets the shark jump
	 * @Post sets the vertical velocity of shark to the initialjumpVelocity and changes the lastJumpingActionNb to actionNb
	 * 			| new.getVerticalVelocity() == this.initVerticalVelocity
	 * 			| new.lastJumpActionNb = actionNb
	 */
	public void startJump(){
		this.setVerticalVelocity(this.initVerticalVelocity);
		lastJumpActionNb = actionNb;
		return;
	}
	/**
	 * Makes an end to a jump of the shark if he is still jumping
	 * @Post if shark has an positive vertical velocity, his vertical velocity will be set to 0
	 * 			|if this.getVerticalVelocity()>0
	 * 			| 	then new.getVerticalVelocity() == 0.0d
	 */
	public void endJump(){
		if(this.getVerticalVelocity()>0){
			this.setVerticalVelocity(0.0d);
		}
		return;
	}
	
	/**
	 * animates the movement of shark.
	 * @Post if moving to the right, the spritenumber will be 1, else it will be 0
	 * 			| if direction == Direction.RIGHT
				|	new.currentSpriteNumber=1
				| else
				|   new.currentSpriteNumber=0
	 */

	public void animate(double dt){
		if(direction == Direction.RIGHT)
			setCurrentSprite(1);
		else
			setCurrentSprite(0);
	}
	
	/**
	 * @param dt | The period of time that the character needs to move
	 * @return The new y-position of the character after he has moved for dt seconds
	 * @throws PositionOutOfBoundsException
	 * 			(A part of) the shark isn't located within the boundaries of the world
	 * 			| ! hasValidPosition()
	 * @throws NullPointerException
	 * @Post depending on the situation the vertical velocity of shark will be set to the correct value
	 * 			|if  overlapsWithWall()[0]==true
	 * 			|  then this.setVerticalVelocity(this.getVerticalVelocity() + (this.getVerticalAcceleration()+Math.max(0, randomAcceleration))*dt)
	 * 			|else
	 * 			|		this.setVerticalVelocity(this.getVerticalVelocity() + (this.getVerticalAcceleration()+ randomAcceleration)*dt)
	 * @Post  if shark wants to go out of the upper or lower borders of the world, it's vertical speed will be set to 0. If he wants to go beneath the world, he will also be grounded
	 * 			|if(newPositiony < 0){
					if(getVerticalVelocity()<=0.0d)
						then new.groundState = GroundState.GROUNDED
							 new.getVerticalVelocity()==0.0d
				|else	
					if(newPositiony>(world.getHeight()-1)/100.0d){
						then new.getVerticalVelocity()==this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign
	 */
	public double moveVertical(double dt)throws PositionOutOfBoundsException,NullPointerException{
		//update position and speed (still need to compensate for velocity over max first time)
		//int stateSign =this.groundState.getSign();
		double newSpeed;
		if(overlapsWithWall()[0]==true)
			newSpeed = this.getVerticalVelocity() + (this.getVerticalAcceleration()+Math.max(0, randomAcceleration))*dt;
		else
			newSpeed = this.getVerticalVelocity() + (this.getVerticalAcceleration()+ randomAcceleration)*dt;
		double newPositiony = getPositionY() + travelledVerticalDistance(dt);
		
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
			}
		}
		this.setVerticalVelocity(newSpeed);
		return newPositiony;
	}
	
	/**
	 * @param dt | The period of time that the character needs to move
	 * @return The new y-position of the character after he has moved for dt seconds
	 * @throws PositionOutOfBoundsException
	 * 			(A part of) the shark isn't located within the boundaries of the world
	 * 			| ! hasValidPosition()

	 * @throws IllegalMovementException
	 * 			one of the parameters has had an overflow
	 * 			|overflowException()
	 * @throws IllegalMovementException
	 * 			trying to divide by 0
	 * 			|newSpeed*dirSign > this.getMaxHorizontalVelocity() && this.getHorizontalAcceleration()==0
	 * @Post the horizontal velocity of shark will be set to the correct new velocity if this isn't more than the maximum allowed velocity. Else it will be set to the maximum velocity
	 * 			| if ! (newSpeed*dirSign > this.getMaxHorizontalVelocity())
	 * 			| 	then new.getHorizontalVelocity()== this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt
	 * 			| else
	 * 			| 	new.getHorizontalVelocity()== this.getMaxHorizontalVelocity()
	 */
	
	public double moveHorizontal(double dt) throws IllegalMovementException,PositionOutOfBoundsException{
		int dirSign =this.direction.getMultiplier(); 
		double newSpeed = this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt;
		double s;
		//dirsign is used in here to compensate for the current direction of the mazub.
		if(newSpeed*dirSign > this.getMaxHorizontalVelocity()){//overgangsverschijnsel (1keer bij berijken max speed)
			if(getHorizontalAcceleration()==0)throw new IllegalMovementException("impossible to divide by zero");
			double accDt = (this.getMaxHorizontalVelocity()- this.getHorizontalVelocity()*dirSign)/(getHorizontalAcceleration()*dirSign);
			s= travelledHorizontalDistance(accDt)+getMaxHorizontalVelocity()*(dt-accDt)*dirSign;
			this.setHorizontalVelocity(this.getMaxHorizontalVelocity()*dirSign);
		}
		else{
			s= travelledHorizontalDistance(dt);
			this.setHorizontalVelocity(newSpeed);
		}
		if(((getPositionX()+s <=0d || s<0)&& dirSign>0 )|| (s>0 && dirSign<0)){
			throw new IllegalMovementException("positionX overflowed");
		}
		//correct position if out of window
		if(getPositionX()+s <0){
			return 0.0d;
		}
		if(getPositionX()+s>(getWorld().getWidth()-1)/100d)
			return (getWorld().getWidth()-1)/100.0d;
		return getPositionX()+s;
	}
	
	/**
	 * calculates the movement over a given period of time according to the vertical axis.
	 * @return this.getVerticalVelocity()*dt*stateSign + this.getVerticalAcceleration()*Math.pow(dt, 2)/2;
	 */
	
	private double travelledVerticalDistance(double dt){
		return this.getVerticalVelocity()*dt +
				this.getVerticalAcceleration()*Math.pow(dt, 2)/2;
	}
	
	/**
	 * 
	 * @param dt | The period of time from which, if necessary, needs to be taken a small part
	 * @return the correct dt. This is the dt that makes sure that if mazub is moving, he will have moved 1 pixel after this dt.
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
				min3 = Math.abs((-getHorizontalVelocity() + Math.sqrt(Math.pow(getHorizontalVelocity(), 2)+2*Math.abs(getHorizontalAcceleration())/100))/getHorizontalAcceleration());
			}else
				min3=Float.POSITIVE_INFINITY;
			
			if (this.getVerticalAcceleration()!=0.0d)
				min4 = Math.abs((-getVerticalVelocity() + Math.sqrt(Math.pow(getVerticalVelocity(), 2)+2*Math.abs(getVerticalAcceleration())/100))/getVerticalAcceleration());
			else 
				min4=Float.POSITIVE_INFINITY;
			return Math.min(Math.min(Math.min(Math.min(min1,min2), min3),min4),dt); // mag geen NaN bevatten
		}
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
	 * @param velocity	|the new velocity of shark according to the y-axis
	 * @post	shark will have the vertical velocity that is passed on to this function
	 * 			|new.getVerticalVelocity() = velocity
	 */
	
	private void setVerticalVelocity(double velocity){
		this.verticalVelocity = velocity;
	}
	
	/**
	 * @param velocity	|the new velocity in the horizontal direction (if not greater then getMaximumHorizontalVelocity())
	 * @post the new velocity of the gameobject is equal to the given velocity or to the maximum horizontal velocity 
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
	 * returns the current velocity of shark according to the horizontal direction
	 * @return	|this.verticalVelocity
	 */
	
	@Basic
	public double getVerticalVelocity(){
		return this.verticalVelocity;
	}
	
	/**
	 * returns the current acceleration of shark according to the vertical direction
	 * @return if isBottomInWater()
	 * 				then  0
	 * 		   else verticalAcceleration* groundState.getMultiplier()
	 * 				
	 */
	@Basic
	public double getVerticalAcceleration(){
		if(isBottomInWater()){
			return 0;
		}
		return verticalAcceleration* groundState.getMultiplier();
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
	 * returns the maximum horizontal velocity	
	 * @return | maxHorizontalVelcoity
	 */
	@Basic
	public double getMaxHorizontalVelocity(){
		return maxHorizontalVelocity;
	}
	
	/**
	 * returns the current acceleration of shark according to the vertical direction
	 * @return |if getHorizontalVelocity()*direction.getMultiplier()==getMaxHorizontalVelocity() || direction == Direction.STALLED
	 * 		   |	then  0
	 * 		   |else horizontalAcceleration* direction.getMultiplier()
	 * 				
	 */
	
	@Basic
	public double getHorizontalAcceleration(){
		if(getHorizontalVelocity()*direction.getMultiplier()==getMaxHorizontalVelocity() || direction == Direction.STALLED)
			return 0;
		return horizontalAcceleration*direction.getMultiplier();
	}
	/**
	 * Adds the object shark to the given world if this is a valid world for it
	 * @Post if the given world is valid, a shark will be added
	 * 		  | if canHaveAsWorld(world)
	 * 		  |   then this.world=world
	 *        |        world.addShark(this)
	 * @Post shark will have the groundstate Grounded or Air depending on the spawning place
	 * 		  |if overlapsWithWall()[0]
	 * 		  | 	then groudState= Groundstate.GROUNDED
	 * 		  |else groundState=Groundstate.AIR
	 */
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			setWorld(world);
			world.addShark(this);
			if(overlapsWithWall()[0]){
				groundState = GroundState.GROUNDED;
			}else{
				groundState = GroundState.AIR;
			}
		}
	}
	
	@Override
	public String toString(){
		return "hp: " + getNbHitPoints(); 
	}
	
	/**
	 * checks if the collision with a given gameobject has an effect
	 * @effect if Shark isn't immune to the gameobject it will lose Hp and it will have an imunityTime of 0.6 seconds
	 * 			|if !Immune()
	 * 			|  then loseHp(50)
	 * 			|		new.imunityTime = 0.6d
	 */
	
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Mazub || gameObject instanceof Slime){
			if(!isImmune()){
				this.loseHp(50);
				this.imunityTime = 0.6d;
			}
		}
	}
}
