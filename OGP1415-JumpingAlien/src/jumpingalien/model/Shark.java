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
	
	public Shark(int x, int y, Sprite[] sprites)throws PositionOutOfBoundsException{
		super(x,y,sprites); 
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
		}

	@Override //TODO movement wanneer op de grond in orde brengen
	public void advanceTime(double dt) throws PositionOutOfBoundsException, NullPointerException, IllegalSizeException{
		while(!isTerminated() && dt >0){
			if(actionTime == actionDuration){
				switch(decideAction()){
				case 0:
					direction = Direction.RIGHT;
					if (isBottomInWater()){
						Random rand = new Random();
						randomAcceleration =  2*verticalMaxRandAcceleration*rand.nextDouble()-verticalMaxRandAcceleration;
					}else
						randomAcceleration = 0.0d;
					break;
				case 1:
					direction = Direction.LEFT;
					if (isBottomInWater()){
						Random rand = new Random();
						randomAcceleration =  2*verticalMaxRandAcceleration*rand.nextDouble()-verticalMaxRandAcceleration;
					}else
						randomAcceleration = 0.0d;
					break;
				case 2:
					direction = Direction.RIGHT;
					startJump();
			    	lastJumpActionNb = actionNb;
			    	randomAcceleration = 0.0d;
					break;
				case 3:
					direction = Direction.LEFT;
					startJump();
	    			lastJumpActionNb = actionNb;
	    			randomAcceleration = 0.0d;
					break;
				default:
					System.err.println("unsupported action");
					break;
				}
				//System.out.println("randomAcc: "+ randomAcceleration);
				actionNb += 1 ;
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
				if(this.overlapsWithWall()[0]==false && !isBottomInWater()){
					groundState = GroundState.AIR;
				}
				if(isBottomInWater() && getVerticalVelocity()<0 && groundState == GroundState.AIR){
					groundState= GroundState.GROUNDED;
					setVerticalVelocity(0.0d);//REDEN dat vissen ff op water blijven drijven, ik denk dat dit zo bedoeld is
				}
			}
			//top
			if( (overlapsWithWall()[2]==true || this.placeOverlapsWithGameObject()[3]==true) && getVerticalVelocity()>0){
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
				if(gameObject instanceof Slime || gameObject instanceof Mazub){
					setPositionX(oldPosition.getPositions()[0]);
					setPositionY(oldPosition.getPositions()[1]);
				}//don't bounce with plants
				EffectOnCollisionWith(gameObject);
				gameObject.EffectOnCollisionWith(this);
			}
			if(isInLava()){
				if(lastLavaHit < 0){
					loseHp(50);
					lastLavaHit = 0.2d;
				}else{
					lastLavaHit -= smallDt;
				}
			}else
				lastLavaHit=0.0d;
			if(isInAir()){
				if(lastWaterHit < 0){
					loseHp(2);
					lastWaterHit = 0.2d;
				}else{
					lastWaterHit -= smallDt;
				}
			}else{
				lastWaterHit =0.2d;
			}
		}
	}
	
	private int decideAction(){
		if(randomAcceleration==0){
			endJump();
		}
		setHorizontalVelocity(0.0d);
		Random rand = new Random();
		actionDuration = rand.nextDouble()*3.0d+1.0d;
		actionTime = 0.0d;
		if (actionNb >= (lastJumpActionNb+4) && (isBottomInWater()==true || this.overlapsWithWall()[0]==true)){
			 return rand.nextInt(4);
		}
		return rand.nextInt(2);
	}
	
	public boolean isBottomInWater() {
		double [] perimeters = this.getPerimeters();//order: left,bottom,right,top
		for(int i=0;i<perimeters.length;i++)perimeters[i]*=100;
		int [][] occupied_tiles = world.getTilePositionsIn((int) (perimeters[0]),(int)(perimeters[1]),(int)(perimeters[2]),(int)(perimeters[3]));
		for (int i=0 ; i < occupied_tiles.length ; i++){
			if (world.getGeologicalFeature(new int[]{occupied_tiles[i][0]*world.getTileLenght(),occupied_tiles[i][1]*world.getTileLenght()})==2){//TODO intern if-else{if-else{...}}, not if if if
				//check if tile is beneath character
				if (world.getBottomLeftPixelOfTile(occupied_tiles[i][0],occupied_tiles[i][1])[1] <= perimeters[1])
					 return true;
			}
		}
		return false;
	}
	
	public void startJump(){
		this.setVerticalVelocity(this.initVerticalVelocity);
		return;
	}
	
	public void endJump(){
		if(this.getVerticalVelocity()>0){
			this.setVerticalVelocity(0.0d);
		}
		return;
	}
	
	
	private void animate(double dt){
		if(direction == Direction.RIGHT)
			currentSpriteNumber=1;
		else
			currentSpriteNumber = 0;
	}
	
	private double moveVertical(double dt)throws PositionOutOfBoundsException,NullPointerException{
		//update position and speed (still need to compensate for velocity over max first time)
		//int stateSign =this.groundState.getSign();
		double newSpeed;
		if(overlapsWithWall()[0]==true)
			newSpeed = this.getVerticalVelocity() + (this.getVerticalAcceleration()+Math.max(0, randomAcceleration))*dt;
		else
			newSpeed = this.getVerticalVelocity() + (this.getVerticalAcceleration()+ randomAcceleration)*dt;
		double newPositiony = getPositionY() + travelledVerticalDistance(dt);
		/*
		if(newPositiony < 0){
			if(getVerticalVelocity()<=0.0d){
				this.groundState = GroundState.GROUNDED;
				setVerticalVelocity(0.0d);
			}
			return 0.0d;
		}else{
			if(newPositiony>(world.getHeight()-1)/100.0d){
				this.setVerticalVelocity(newSpeed);
				return ((world.getHeight()-1)/100.0d);
			}
		}*/
		this.setVerticalVelocity(newSpeed);
		return newPositiony;
	}
	
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
		if(getPositionX() <0){
			return 0.0d;
		}
		if(getPositionX()>(world.getWidth()-1)/100d)
			return (world.getWidth()-1)/100.0d;
		return getPositionX()+s;
	}
	
	private double travelledVerticalDistance(double dt){
		return this.getVerticalVelocity()*dt +
				this.getVerticalAcceleration()*Math.pow(dt, 2)/2;
	}
	
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
	
	private double travelledHorizontalDistance(double dt){
		return this.getHorizontalVelocity()*dt +
				this.getHorizontalAcceleration()*Math.pow(dt, 2)/2;
	}
	
	private void setVerticalVelocity(double velocity){
		this.verticalVelocity = velocity;
	}
	
	private void setHorizontalVelocity(double velocity){
		this.horizontalVelocity = velocity;
	}
	
	@Basic
	public double getVerticalVelocity(){
		return this.verticalVelocity;
	}
	
	@Basic
	public double getVerticalAcceleration(){
		if(isBottomInWater()){
			return 0;
		}
		return verticalAcceleration* groundState.getMultiplier();
	}
	
	@Basic
	public double getHorizontalVelocity(){
		if(Math.abs(horizontalVelocity)>getMaxHorizontalVelocity()){
			return getMaxHorizontalVelocity()*Math.signum(horizontalVelocity);
		}
		return horizontalVelocity;
	}
	
	@Basic
	public double getMaxHorizontalVelocity(){
		return maxHorizontalVelocity;
	}
	
	@Basic
	public double getHorizontalAcceleration(){
		if(getHorizontalVelocity()*direction.getMultiplier()==getMaxHorizontalVelocity() || direction == Direction.STALLED)
			return 0;
		return horizontalAcceleration*direction.getMultiplier();
	}
	
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			this.world = world;
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
	
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Mazub || gameObject instanceof Slime){
			if(!isImmune()){
				this.loseHp(50);
				this.imunityTime = 0.6d;
			}
		}
	}
}
