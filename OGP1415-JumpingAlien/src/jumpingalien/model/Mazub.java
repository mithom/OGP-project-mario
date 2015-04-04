package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*; 
import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.Direction;
import jumpingalien.state.DuckState;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;
/**
 * Mazub is a class representing a character off the game. 
 * @author Meerten Wouter & Michiels Thomas
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
public class Mazub extends GameObject{
	private double horizontalVelocity;
	private final static double horizontalAcceleration = 0.9d;
	private final static double verticalAcceleration = -10d;
	private double verticalVelocity;
	private double positionX; //voor pixels als intege, dit is echte plaats
	private double positionY;
	private double maxHorizontalVelocity;
	private final static int gameWidth = 1024;
	private final static int gameHeight = 768;
	private final double initialHorizontalVelocity;
	private final double initialVerticalVelocity =8d;
	private Direction direction ;
	private GroundState groundState;
	private DuckState duckState;
	private double timeSinceLastAnimation;
	private double timeSinceLastMovement;
	private Direction lastMovingDirection = Direction.STALLED;
	private int m;
	private double maxDuckingVelocity = 1.0d;

	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that mazub will use to rotate trough, to make
	 * 						|animations. The first eight Sprites are predefined, while the next 2*m
	 * 						|amount of Sprites will be used for the walking animation.
	 * @Post	the initial position of the Mazub instance will be (pixelLeftX,pixelBottomY)
	 * 			|new.getPosition()== (pixelLeftX,pixelBottomY)
	 * @Post	the list of Sprites the mazub instance will use, will be stored in spriteList
	 * 			|new.spriteList == sprites;
	 * @Post	if the Mazub instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites);
		this.spriteList = sprites;
		this.positionX = (double)pixelLeftX/100d;
		this.positionY = (double)pixelBottomY/100d;
		this.m = (spriteList.length-8)/2;
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		if(this.positionY==0){
			groundState =GroundState.GROUNDED;
		}else{
			groundState = GroundState.AIR;
		}
		direction= Direction.STALLED;
	}
	
	/**
	 * 
	 * @param pixelLeftX	|the most left position that is part from the currently showing Sprite.
	 * @param pixelBottomY	|the lowest position that is part of the currently showing Sprite.
	 * @param sprites		|a list of Sprites that mazub will use to rotate trough, to make
	 * 						|animations. The first eight Sprites are predefined, while the next 2*m
	 * 						|amount of Sprites will be used for the walking animation.
	 * @param initHorVel	|the initial velocity at which the Mazub instance will walk when starting to move horizontally.
	 * @param maxHorVel		|the maximum velocity at which the Mazub instance will walk.
	 * @Post	the initial position of the Mazub instance will be (pixelLeftX,pixelBottomY)
	 * 			|new.getPosition()== (pixelLeftX,pixelBottomY)
	 * @Post	the list of Sprites the mazub instance will use, will be stored in spriteList
	 * 			|new.spriteList == sprites;
	 * @Post	if the Mazub instance isn't located on the ground, he will know he is in the air.
	 * 			|if(pixelBottomY>0):
	 * 			|	then new.groundState == GroundState.AIR
	 * 			|	else new.groundState == GroundState.GROUNDED; 
	 * @Post 	right after initiation, the maximum horizontal velocity of the Mazub instance will be equal the the given parameter maxHorVel.
	 * 			|new.getMaximumHorizontalVelocity()== maxHorVel
	 * @Pre		the initial velocity can never be greater then the maximum velocity in horizontal direction.
	 * 			|initHorVel <= maxHorVel
	 */
	public Mazub(int pixelLeftX, int pixelBottomY,double initHorVel,double maxHorVel, Sprite[] sprites)throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites);
		this.spriteList = sprites;
		this.positionX = (double)pixelLeftX/100d;
		this.positionY = (double)pixelBottomY/100d;
		this.m = (spriteList.length-8)/2;
		this.maxHorizontalVelocity = maxHorVel;
		this.initialHorizontalVelocity = initHorVel;
		duckState  = DuckState.STRAIGHT;
		if(this.positionY==0){
			groundState =GroundState.GROUNDED;
		}else{
			groundState = GroundState.AIR;
		}
		direction= Direction.STALLED;
	}
	
	/**
	 * 
	 * @param dt	|the time passed since the last frame.
	 * @throws IllegalTimeException
	 * 			the time passed since the last frame is illegal.
	 * 			|dt <0 || dt > 0.2 || Double.NaN == dt
	 * @throws IllegalMazubStateException
	 * 			the current State of mazub is null.
	 * 			|getGroundState()==null
	 * 			|getOrientation()==null
	 * 			|getDuckState()==null
	 * @throws IllegalMovementException
	 * 			one of the parameters has had an overflow
	 * 			|overflowException()
	 * @throws IllegalMovementException
	 * 			trying to divide by 0
	 * 			|newSpeed*dirSign > this.getMaxHorizontalVelocity() && this.getHorizontalAcceleration()==0
	 * @effect	the position,velocity,acceleration and State from mazub will be update according to the physics over a span from dt seconds.
	 * 			|moveHorizontal()
	 * 			|moveVertical()
	 * @effect	the shown Sprite is updated according to the changed state of mazub.
	 * 			|animate()
	 */
	//moveHor publiek maken of @effect vervangen door doc.
	public void advanceTime(double dt) throws IllegalMovementException,IllegalMazubStateException,IllegalTimeException {
		if(dt <0 || dt > 0.2 || dt == Double.NaN){
			throw new IllegalTimeException(dt);
		}
		if(! isValidState(getOriëntation(),getGroundState(),getDuckState())){
			throw new IllegalMazubStateException(this);
		}
		moveHorizontal(dt);
		moveVertical(dt);
		animate(dt);
		return;
	}
	
	/**
	 * changes the position,acceleration and velocity of the mazub according to the horizontal axis for a given time dt.
	 */
	private void moveHorizontal(double dt) throws IllegalMovementException{
		//update position and speed (still need to compensate for velocity over max first time)
		int dirSign =this.direction.getSign(); 
		double newSpeed = this.getHorizontalVelocity()+this.getHorizontalAcceleration()*dt;
		double s;
		//dirsign is used in here to compensate for the current direction of the mazub.
		if(newSpeed*dirSign > this.getMaxHorizontalVelocity()){//overgangsverschijnsel (1keer bij berijken max speed)
			if(getHorizontalAcceleration()==0)throw new IllegalMovementException("impossible to divide by zero");
			double accDt = (this.getMaxHorizontalVelocity()- this.getHorizontalVelocity()*dirSign)/(getHorizontalAcceleration()*dirSign);
			s= travelledHorizontalDistance(accDt, dirSign)+travelledHorizontalDistance(dt-accDt, 0);
			positionX = positionX + s;
			this.setHorizontalVelocity(this.getMaxHorizontalVelocity()*dirSign);
		}
		else{
			s= travelledHorizontalDistance(dt,dirSign);
			positionX =positionX +s;
			this.setHorizontalVelocity(newSpeed);
		}
		if(((positionX <=0d || s<0)&& dirSign>0 )|| (s>0 && dirSign<0)){
			throw new IllegalMovementException("positionX overflowed");
		}
		//correct position if out of window
		if(positionX<0){
			positionX = 0;
		}
		if(positionX>(gameWidth-1)/100d) positionX = (gameWidth-1)/100d;
		return;
	}
	
	/**
	 * calculates the movement over a given period of time according to the horizontal axis.
	 */
	private double travelledHorizontalDistance(double dt,int dirSign){
		return this.getHorizontalVelocity()*dt +
				this.getHorizontalAcceleration()*Math.pow(dt, 2)/2;
	}
	/**
	 * calculates the movement over a given period of time according to the vertical axis.
	 */
	private double travelledVerticalDistance(double dt, int stateSign){
		return this.getVerticalVelocity()*dt*stateSign +
				this.getVerticalAcceleration()*Math.pow(dt, 2)*stateSign/2;
	}
	
	/**
	 * changes the position,acceleration and velocity of the mazub according to the horizontal axis for a given time dt.
	 */
	private void moveVertical(double dt){
		//update position and speed (still need to compensate for velocity over max first time)
		int stateSign =this.groundState.getSign(); 
		double newSpeed = this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign;
		
		positionY =positionY + travelledVerticalDistance(dt,stateSign);
		this.setVerticalVelocity(newSpeed);

		//correct position if out of window && notice grounded
		if(positionY <= 0){
			positionY = 0;
			if(getVerticalVelocity()<=0){
				this.groundState = GroundState.GROUNDED;
				setVerticalVelocity(0d);
			}
		}
		if(positionY>gameHeight/100d) positionY = gameHeight/100d;
		return;
	}
	
	/**
	 * checks the current state from the mazub and changes the shown sprite accordingly.
	 */
	private void animate(double dt){
		timeSinceLastAnimation += dt;
		if(getOriëntation() == Direction.STALLED){
			timeSinceLastMovement += dt;
		}else{
			timeSinceLastMovement = 0;
			System.out.println(timeSinceLastMovement);
		}
		if(getOriëntation() == Direction.STALLED){
			if(timeSinceLastMovement>=1){
				if(duckState == DuckState.STRAIGHT){
					currentSpriteNumber = 0;
				}else{
					currentSpriteNumber = 1;
				}
			}else{
				if(duckState == DuckState.STRAIGHT){
					currentSpriteNumber = 2- (lastMovingDirection.getSign()-1)/2;
				}else{
					currentSpriteNumber = 6 - (lastMovingDirection.getSign()-1)/2;
				}
			}
		}else{
			if(groundState == GroundState.AIR && duckState == DuckState.STRAIGHT){
				currentSpriteNumber = 4- (getOriëntation().getSign()-1)/2;
			}else{
				if(duckState == DuckState.DUCKED){
					currentSpriteNumber = 6- (getOriëntation().getSign()-1)/2;
				}else{
					if((currentSpriteNumber<8) || (getOriëntation() == Direction.LEFT && currentSpriteNumber <8+m) || (getOriëntation()==Direction.RIGHT && currentSpriteNumber >= 8+m)){
						timeSinceLastAnimation =0;
						//System.out.println("direction has changed");
						currentSpriteNumber = 8 - (getOriëntation().getSign()-1)*m/2;
					}else{
						if(timeSinceLastAnimation >= 0.075){
							timeSinceLastAnimation =0;
							currentSpriteNumber = (currentSpriteNumber-(8 - (getOriëntation().getSign()-1)*m/2) + 1)%m+8 - (getOriëntation().getSign()-1)*m/2;
						}
					}
				}
			}
		}
		System.out.println(currentSpriteNumber);
		return;
	}
	/**
	 * changes the position,acceleration and velocity of the mazub according to the horizontal axis for a given time dt.
	 * @return	the horizontal velocity. This can never be greater then the maximum velocity
	 * 			|this.horizontalVelocity
	 */
	@Basic
	public double getHorizontalVelocity(){
		if(Math.abs(horizontalVelocity)>getMaxHorizontalVelocity()){
			return getMaxHorizontalVelocity()*Math.signum(horizontalVelocity);
		}
		return horizontalVelocity;
	}
	
	/**
	 * @param velocity	|the new velocity in the horizontal direction (if not greater then getMaximumHorizontalVelocity())
	 */
	private void setHorizontalVelocity(double velocity){
		this.horizontalVelocity = velocity;
	}
	
	/**
	 * returns the current maximum horizontal velocity
	 * @return	|this.maxHorizontalVelocity
	 */
	@Basic
	public double getMaxHorizontalVelocity(){
		if(duckState == DuckState.STRAIGHT){
			return this.maxHorizontalVelocity;
		}else{
			return this.maxDuckingVelocity;
		}
		
	}
	
	/**
	 * returns the current acceleration according to the horizontal direction of mazub
	 * @return this.horizontalAcceleration
	 */
	@Basic
	public double getHorizontalAcceleration(){
		if(getHorizontalVelocity()*getOriëntation().getSign()==getMaxHorizontalVelocity())
			return 0;
		return Mazub.horizontalAcceleration*getOriëntation().getSign();
	}
	
	/**
	 * returns the current acceleration of mazub according to the vertical direction
	 * @return this.verticalAcceleration
	 */
	@Basic
	public double getVerticalAcceleration(){
		return Mazub.verticalAcceleration* groundState.getSign();
	}
	
	/**
	 * returns the current velocity of mazub according to the horizontal direction
	 * @return	|this.verticalVelocity
	 */
	@Basic
	public double getVerticalVelocity(){
		return this.verticalVelocity;
	}
	
	/**
	 * @param velocity	|the new velocity from mazub according to the y-axis
	 * @post	mazub will have the vertical velocity that is passed on to this function
	 * 			|new.getVerticalVelocity() == velocity
	 */
	private void setVerticalVelocity(double velocity){
		this.verticalVelocity = velocity;
	}
	
	/**
	 * returns the direction mazub is facing. If he is facing up front, the returned value will be Direction.STALLED.
	 * @return this.direction
	 */
	@Basic
	public Direction getOriëntation(){
		return this.direction;
	}
	
	/**
	 * 
	 * @param dir	|the direction mazub is currently facing
	 * @Pre		the given Direction dir cannot be empty (null), neither can it be Direction.STALLED.
	 * 			|(dir != null)&& (dir != Direction.STALLED)
	 * @Post	mazub is ready to move in the given direction when time advances.
	 * 			|new.getHorizontalVelocity()== this.initialHorizontalVelocity*dir.getSign()
	 * 			|new.getOrientation() == dir
	 */
	public void startMove(Direction dir){
		assert dir != null && dir != Direction.STALLED;
		assert this.initialHorizontalVelocity>0;
		this.direction = dir;
		this.lastMovingDirection = dir;
		this.setHorizontalVelocity(this.initialHorizontalVelocity*dir.getSign());
		return;
	}
	
	/**
	 * 
	 * @param dir	|the direction in which you want to stop moving
	 * @Pre		the given Direction dir cannot be empty (null), neither can it be Direction.STALLED.
	 * 			|(dir != null)&& (dir != Direction.STALLED)
	 * @Post	if the given direction is the direction in which mazub is moving, then mazub will not move when time advances.
	 * 			|if(this.getOrientation()==dir):
	 * 			|	then	new.getHorizontalVelocity() == 0
	 * 			|			new.getOrientation == Direction.STALLED
	 */
	public void endMove(Direction dir){
		//recht,links,links los->zou rehcts moeten wandelen, doet nu niet
		assert dir != null && dir != Direction.STALLED;
		if(dir.getSign()== Math.signum(getHorizontalVelocity())){
			this.setHorizontalVelocity(0.0d);
			this.direction = Direction.STALLED;
		}
		return;
	}
	
	/**
	 * @throws IllegalMazubStateException
	 * 			the grounState of mazub is the illegal value null
	 * 			|this.getGroundState()==null
	 * @Post	if mazub is on the ground, he'll get an upward velocity
	 * 			|if(this.getGroundState() == GroundState.GROUNDED
	 * 			| then 	new.getVerticalVelocity() == this.initialVerticalVelocity
	 * @Post	mazub will be noted as "being in the air". 
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
	 * @Post	if mazub has an upward velocity, this will be set 0.
	 * 			|if(this.getVerticalVelocity() > 0
	 * 			|	then	new.getVerticalVelocity() == 0
	 */
	public void endJump(){
		if(this.getVerticalVelocity()>0){
			this.setVerticalVelocity(0d);
		}
		return;
	}
	
	/**
	 * @Post	mazub will duck when time advances
	 * 			|new.getDuckState() = DuckState.DUCKED
	 */
	public void startDuck(){
		this.duckState = DuckState.DUCKED;
		return;
	}
	
	/**
	 * @Post	mazub will stand up (ot stay straight if he was already staying straight
	 * 			|new.getDuckState() == DuckState.STRAIGHT
	 */
	public void endDuck(){
		this.duckState = DuckState.STRAIGHT;
		return;
	}
	
	/**
	 * returns the index of the most left pixel used by mazub. Each pixel represents 0.01m
	 * @throws PositionOutOfBoundsException
	 * 			mazub has an illegal position (error can also be in Y position)
	 * 			| !hasValidPosition()
	 * @return	|(int)this.positionX*100
	 */
	@Basic //basic inspectors moeten basic zijn, nooit exception.
	public int getPixel_x()throws PositionOutOfBoundsException{
		if(!hasValidPosition()) throw new PositionOutOfBoundsException(positionX,positionY);
		return (int)(this.positionX*100);//
	}
	
	public boolean hasValidPosition(){// bij setter->class invar
		return ! (positionX<0 || positionX>= (gameWidth)/100.0d) || (positionY<0 || positionY >= gameHeight/100.0d);
	}
	/**
	 * returns the index of the lowest pixel used by mazub. Each pixel represents 0.01m
	 * @throws PositionOutOfBoundsException
	 * 			mazub has an illegal position (error can also be in X position)
	 * 			| !hasValidPosition()
	 * @return	|(int)this.positionY*100
	 */
	@Basic
	public int getPixel_y() throws PositionOutOfBoundsException{
		if(! hasValidPosition()) throw new PositionOutOfBoundsException(positionX,positionY);
		return (int)(this.positionY*100);
	}
	
	private boolean isValidState(Direction direction,GroundState groundState,DuckState duckState){
		return direction != null && groundState != null && duckState != null;
	}
	
	/**
	 * returns the current GroundState of mazub. This can be GROUNDED or AIR.
	 * @return	|this.groundState
	 */
	public GroundState getGroundState(){
		return this.groundState;
	}
	
	/**
	 * returns the current DuckState of mazub. this van be STRAIGHT or DUCKED
	 * @return	|this.DuckState()
	 */
	public DuckState getDuckState(){
		return this.duckState;
	}
	
	public static int getGameHeight(){
		return gameHeight;
	}
	
	public static int getGameWidth(){
		return gameWidth;
	}
}
