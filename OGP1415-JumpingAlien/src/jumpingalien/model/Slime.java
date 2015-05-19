package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.state.Direction;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;
/**
 * Slime is a class representing a gameObject of the game. 
 * @author Meerten Wouter & Michiels Thomas (both 2de fase ingenieurswetenschappen)
 * @version 1.0
 * @Invar 	the position of the lower left pixel will always be valid. 
 * 			This means it will never be outside of the boundaries of the game.
 * 			|hasValidPosition()
 * @Invar	the groundState has a valid value at all time in the game.
 * 			|isValidState(this.getGroundState())
 * @Invar 	at all time in the game, the acceleration and velocity must have valid values
 * 			|Double.isNaN(this.getHorizontalVelocity())==false
 * 			|Double.isNaN(this.getVerticalVelocity())==false
 * 			|Double.isNaN(this.getHorizontalAcceleration())==false
 * 			|Double.isNaN(this.getVerticalAcceleration())==false
 * @Invar	the currentSpriteNumber must always be valid
 * 			|isValidSpriteNumber(currentSpriteNumber)
 */
public class Slime extends GameObject{
	private School school;
	private final double horizontalAcceleration=0.7d;
	private final double maxHorizontalVelocity = 2.5d;
	private double actionTime;
	private double actionDuration;
	private double horizontalVelocity;
	private double verticalVelocity;
	private final static double verticalAcceleration = -10.0d;
	private GroundState groundState;
	private Direction direction;
	/**
	 * 
	 * @param x |the most left position that is part from the currently showing Sprite.
	 * @param y |the lowest position that is part of the currently showing Sprite.
	 * @param sprites |a list of Sprites that shark will use to rotate trough
	 * @param school | the school to which the slime belongs
	 * @throws PositionOutOfBoundsException
	 * 			slime has an illegal position
	 * 			| ! hasValidPosition()
	 * @Post The slime will be automatically standing still when the game starts 
	 * 			|new.direction = Direction.STALLED
	 * @Post The slime will be added to the given school
	 * 			|new.getSchool()=school
	 */
	public Slime(int x, int y, Sprite[] sprites,School school) throws PositionOutOfBoundsException{
		super(x,y,sprites,0,500,100);
		setSchool(school);
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
	}
	
	public Slime(int x, int y, Sprite[] sprites,School school,Program program) throws PositionOutOfBoundsException{
		super(x,y,sprites,0,500,100,program);
		setSchool(school);
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
	}
	/**
	 * @param dt	|the time passed since the last frame.
	 * @throws PositionOutOfBoundsExeption
	 * 			slime has an illegal position
	 * 				| ! hasValidPosition()
	 * @Post    imunitytime is changed
	 * 			|double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime);
	 * 			|imunityTime= Math.max(0,imunityTime-smallDt);
	 * @Post	depending on the situation, the groundstate and speed of slime can be changed
	 * 			|Position oldPosition = getPosition();
				|new.getPositionY()==moveVertical(smallDt)
				|new.getPositionX()==moveHorizontal(smallDt)
				|if (this.overlapsWithWall()[0]==true && getVerticalVelocity()<0.0d)
				|	new.getVerticalVelocity(0.0d)
				|	new.groundState = GroundState.GROUNDED
				|	new.getPositionY(oldPosition.getPositions()[1]-0.01d)
				|else
				|	if(this.overlapsWithWall()[0]==false)
				|		new.groundState = GroundState.AIR
				|if(this.overlapsWithWall()[1]==true && getHorizontalVelocity()<0)
				|	new.getHorizontalVelocity()==0.0d
				|	new.getPositionX()==oldPosition.getPositions()[0]
				|if( overlapsWithWall()[3]==true && getHorizontalVelocity()>0)
				|	new.getHorizontalVelocity()==0.0d
				|	new.getPositionX()==oldPosition.getPositions()[0]
	 * @Post if slime collides with another object (shark or mazub) the changed movemement will be undone
	 * 			|while(dt>0 && !isTerminated())
	 * 			|	for(GameObject gameObject:getOverlappingGameObjects()){
				|		if(gameObject instanceof Shark || gameObject instanceof Mazub)
				|			new.getPositionX()== oldPosition.getPositions()[0]
				|			new.getPositionY()==oldPosition.getPositions()[1]
	 * @effect 	the action that slime will execute needs to be determined
	 * 			|decideAction()
	 * @effect	the position,velocity,acceleration and State from mazub will be update according to the physics over a span from dt seconds.
	 * 			|moveHorizontal()
	 * 			|moveVertical()
	 * @effect  If slime collides with a gameObject, it will be checked if there are any consequences
	 * 			|for(GameObject gameObject:getOverlappingGameObjects())
	 * 			|	EffectOnCollisionWith(gameObject);
				|	gameObject.EffectOnCollisionWith(this);
	 * @effect	the shown Sprite is updated according to the changed state of mazub.
	 * 			|double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime)
	 * 			|animate(smallDt)	 
	 * @effect  If Mazub is in lava or water, he will lose Hp
	 * 			|if isInLava() 
				| 	then loseHp(50)
				|if isInWater
				|	then loseHp(2)
	 */
	@Override
	public void advanceTime(double dt)throws PositionOutOfBoundsException{
		while(!isTerminated() && dt >0){
			decideAction();
			double smallDt;
			if(getProgram()==null)
				smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime);
			else
				smallDt= Math.min(calculateCorrectDt(dt), 0.001d);
			actionTime+=smallDt;
			dt-= smallDt;
			imunityTime= Math.max(0,imunityTime-smallDt);
			Position oldPosition = getPosition();
			setPositionY(moveVertical(smallDt));
			setPositionX(moveHorizontal(smallDt));
			if (this.overlapsWithWall()[0]==true && getVerticalVelocity()<0.0d){
				this.setVerticalVelocity(0.0d);
				groundState = GroundState.GROUNDED;
				setPositionY(oldPosition.getPositions()[1]-0.01d);
			}else{
				if(this.overlapsWithWall()[0]==false){
					groundState = GroundState.AIR;
				}
			}
			if(this.overlapsWithWall()[1]==true && getHorizontalVelocity()<0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			if( overlapsWithWall()[3]==true && getHorizontalVelocity()>0){
				this.setHorizontalVelocity(0.0d);
				setPositionX(oldPosition.getPositions()[0]);
			}
			animate(smallDt);
			for(GameObject gameObject:getOverlappingGameObjects()){
				if(gameObject instanceof Slime || gameObject instanceof Mazub || gameObject instanceof Shark){
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
			if(isInWater()){
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
	 * @Post the horizontal velocity will be set to 0
	 * 			|new.getHorizontalVelocity = 0.0d
	 * @Post the direction of slime will be Right or Left, depending on the randomNum that will be randomly picked
	 * 			|if randomNum==0
	 * 			|	then new.direction = Direction.RIGHT
	 * 			|else new.direction= Direction.LEFT
	 * 
	 */
	public void decideAction(){
		if(getProgram() != null){
			if(actionTime>0){
				//actionTime = getProgram().executeTime(((double)((int)(actionTime*1000)))/1000.0d);
				getProgram().executeTime(0.01d);
			}
		}else{
			if(actionTime == actionDuration){
				endMove();
				Random rand = new Random();
				actionDuration = rand.nextDouble()*4.0d+2.0d;
				actionTime = 0.0d;
			    int randomNum = rand.nextInt(2);
			    switch(randomNum){
			    case 0:
			    	startMove(Direction.RIGHT);
			    	break;
			    case 1:
			    	startMove(Direction.LEFT);
			    	break;
			    }
			}
		}
	}
	
	/**
	 * 
	 * @param dir |the direction in which slime needs to move
	 * @Post the direction of slime is changed
	 * 			| new.direction = dir
	 */
	public void startMove(Direction dir){
		direction = dir;
		//Extendible in case slimes should get initial velocity or something else on movement.
	}
	
	/**
	 * stops the movement of slime
	 * @Post the horizontal velocity is set to 0
	 * 			|new.getHorizontalVelocity() == 0.0d
	 */
	public void endMove(){
		setHorizontalVelocity(0.0d);
	}
	/**
	 * animates the movement of slime.
	 * @Post if moving to the right, the spritenumber will be 1, else it will be 0
	 * 			| if direction == Direction.RIGHT
	 *			|	currentSpriteNumber=1
	 *			| else
	 *			|   currentSpriteNumber=0
	 */
	private void animate(double dt){
		if(direction == Direction.RIGHT)
			setCurrentSprite(1);
		else
			setCurrentSprite(0);
	}
	
	/**
	 * @param dt | The period of time that the character needs to move
	 * @return The new y-position of the character after he has moved for dt seconds
	 * @throws PositionOutOfBoundsException
	 * 			(A part of) the sliem isn't located within the boundaries of the world
	 * 			| ! hasValidPosition()
	 * @Post  if slime wants to go out of the lower borders of the world, it's vertical speed will be set to 0 and be grounded
	 * 			|if(newPositiony < 0){
					if(getVerticalVelocity()<=0.0d)
						then new.groundState = GroundState.GROUNDED
							 new.getVerticalVelocity()==0.0d
				|else	
					new.getVerticalVelocity()==this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign
	*/					

	private double moveVertical(double dt)throws PositionOutOfBoundsException{
		//update position and speed (still need to compensate for velocity over max first time)
		int stateSign =this.groundState.getMultiplier(); 
		double newSpeed = this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign;
		
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
	 * 			(A part of) the slime isn't located within the boundaries of the world
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
	
	private double moveHorizontal(double dt) throws IllegalMovementException,PositionOutOfBoundsException{
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
	
	public double calculateCorrectDt(double dt){
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
	 * @param velocity	|the new velocity of slime according to the y-axis
	 * @post	slime will have the vertical velocity that is passed on to this function
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
	 * returns the current velocity of slime according to the horizontal direction
	 * @return	|this.verticalVelocity
	 */
	@Basic
	public double getVerticalVelocity(){
		return this.verticalVelocity;
	}
	/**
	 * returns the current acceleration of slime according to the vertical direction
	 * @return | Slime.verticalAcceleration* groundState.getMultiplier()
	 * 				
	 */
	@Basic
	public double getVerticalAcceleration(){
		return Slime.verticalAcceleration* groundState.getMultiplier();
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
	 * returns the current acceleration of slime according to the vertical direction
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
	 * 
	 * @param school | the school to which the slime needs to be set 
	 * 
	 * @post if the given school is null, slime will have null as school
	 * 			|new.school = null
	 * @effect if slime leaves a school, it will be removed from the school and this slime will give 1 Hp to each of the slimes in the school he is leaving. In returns he 
	 * 			recieves 1 Hp from every slime in the school he is joining. He also joins this school
	 * 
	 */
	public void setSchool(School school){
		if(school == null)
			this.school = null;
		else{
			if(getSchool() != null){
				int difference = 0;
				this.school.removeSlime(this);
				for(Slime slime:this.school.getSlimes()){
					if(!slime.hasMaxHp()){
						slime.addHp(1);
						difference-=1;
					}
				}
				for(Slime slime:school.getSlimes()){
					if(difference<(getMaxNbHitPoints()-getNbHitPoints())){
						slime.loseHp(1);
						difference+=1;
					}
				}
				this.addHp(difference);
			}
			this.school = school;
			school.addSlime(this);
		}
	}
	/**
	 * returns the school of the slime
	 * @return |school
	 */
	@Basic
	public School getSchool(){
		return school;
	}
	
	/**
	 * Adds the object slime to the given world if this is a valid world for it
	 * @Post if the given world is valid, a slime and it's school will be added 
	 * 		  | if canHaveAsWorld(world)
	 * 		  |   then this.world=world
	 * 		  |		   world.addSchool(getSchool())
	 *        |        world.addSlime(this)
	 * @Post slime will have the groundstate Grounded or Air depending on the spawning place
	 * 		  |if overlapsWithWall()[0]
	 * 		  | 	then groudState= Groundstate.GROUNDED
	 * 		  |else groundState=Groundstate.AIR
	 */
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			setWorld(world);
			world.addSchool(getSchool());
			world.addSlime(this);
			if(overlapsWithWall()[0]){
				groundState = GroundState.GROUNDED;
			}else{
				groundState = GroundState.AIR;
			}
		}
	}
	
	/*
	 * 
	 * @return | if !world.isTerminated() && !this.isTerminated() && this.world==null
	 * 		   | 		then true
	 * 		   | else        false
	 *//*
	@Override
	protected boolean canHaveAsWorld(World world){
		if(!world.isTerminated() && !this.isTerminated() && this.getWorld()==null
				//&& world.getSchools().contains(school)
				)
			return true;
		return false;
	}*/
	
	public String toString(){
		return "slime of( " + getSchool() + ") with hp: " + getNbHitPoints() + " ,using program: "+ (getProgram() != null);
	}
	
	/**
	 * @effect if a slime loses Hp, all slimes in the school will lose 1 Hp
	 * 			|  for(Slime slime:school.getSlimes())
	 *					if(slime != this)
	 *						then slime.loseHp(1)
	 */
	public void schoolHpLoss(){
		for(Slime slime:school.getSlimes()){
			if(slime != this)
				slime.loseHp(1);
		}
	}
	
	/**
	 * checks if the collision with a given gameobject has an effect
	 * @Post if Slime collides with another slime and the size of school of the other slime is larger than the school
	 * 		 of this slime, the slime will join the other school. Otherwise, it will remain in it's current school 
	 * 		 and the school of slime will be changed
	 * 			|if gameObject instanceof Slime
	 * 			|	then if (slime.getSchool().getSize()> getSchool().getSize())
	 * 			|			then new.getSchool = slime.getSchool()
	 * 			|		 else
	 * 			|			if slime.getSchool().getSize()< getSchool().getSize()
	 * 							then new.slime.getSchool() = this.getSchool()
	 * @effect if Slime isn't immune to the gameobject it will lose Hp and it will have an imunityTime of 0.6 seconds
	 * 		   The other slimes in the school will also lose 1 Hp each
	 * 			|if !Immune()
	 * 			|  then this.loseHp(50)
	 * 			|		new.imunityTime = 0.6d
	 * 			|		schoolHpLoss()
	 */
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Slime){
			Slime slime = (Slime)gameObject;
			if(slime.getSchool().getSize()> getSchool().getSize()){
				setSchool(slime.getSchool());
			}else{
				if(slime.getSchool().getSize()< getSchool().getSize()){
					slime.setSchool(getSchool());
				}
			}
		}
		if(gameObject instanceof Mazub || gameObject instanceof Shark){
			if(!isImmune()){
				this.schoolHpLoss();
				this.loseHp(50);
				this.imunityTime = 0.6d;
			}
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
	public void endMove(Direction direction) {
		// TODO Auto-generated method stub
		
	};
}
