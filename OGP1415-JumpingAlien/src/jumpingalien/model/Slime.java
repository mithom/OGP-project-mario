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
	
	public Slime(int x, int y, Sprite[] sprites,School school) throws PositionOutOfBoundsException{
		super(x,y,sprites);
		setSchool(school);
		actionTime = 0.0d;actionDuration = 0.0d;
		direction = Direction.STALLED;
	}
	
	@Override
	public void advanceTime(double dt)throws PositionOutOfBoundsException{
		while(!isTerminated() && dt >0){
			decideAction();
			double smallDt = Math.min(calculateCorrectDt(dt),actionDuration-actionTime);
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
				if(gameObject instanceof Slime){
					Slime slime = (Slime)gameObject;
					if(slime.getSchool().getSize()> getSchool().getSize()){
						setSchool(slime.getSchool());
					}else{
						if(slime.getSchool().getSize()< getSchool().getSize()){
							slime.setSchool(getSchool());
						}
					}
				}if(gameObject instanceof Mazub || gameObject instanceof Shark){
					//other instance then slime
					//TODO blocking movement against each other, not further away!, setting imunity
					if(!isImmune()){
						this.schoolHpLoss();
						this.loseHp(50);
						this.imunityTime = 0.6d;
					}
					//TODO 2sided bounce!
				}
				setPositionX(oldPosition.getPositions()[0]);
				setPositionY(oldPosition.getPositions()[1]);
			}
		}
	}
	
	private void decideAction(){
		if(actionTime == actionDuration){
			setHorizontalVelocity(0.0d);
			Random rand = new Random();
			actionDuration = rand.nextDouble()*4.0d+2.0d;
			actionTime = 0.0d;
		    int randomNum = rand.nextInt((1 - 0) + 1) + 0;
		    switch(randomNum){
		    case 0:
		    	direction = Direction.RIGHT;
		    	break;
		    case 1:
		    	direction = Direction.LEFT;
		    	break;
		    }
		}
	}
	
	private void animate(double dt){
		//TODO implement this function
	}
	
	private double moveVertical(double dt)throws PositionOutOfBoundsException{
		//update position and speed (still need to compensate for velocity over max first time)
		int stateSign =this.groundState.getSign(); 
		double newSpeed = this.getVerticalVelocity() + this.getVerticalAcceleration()*dt*stateSign;
		
		double newPositiony = getPositionY() + travelledVerticalDistance(dt);
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
		}
		this.setVerticalVelocity(newSpeed);
		return newPositiony;
	}
	
	private double moveHorizontal(double dt) throws IllegalMovementException,PositionOutOfBoundsException{
		int dirSign =this.direction.getSign(); 
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
	
	public double calculateCorrectDt(double dt){
		return Math.min(0.1d, dt);
		//TODO implement this function
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
		return Slime.verticalAcceleration* groundState.getSign();
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
		if(getHorizontalVelocity()*direction.getSign()==getMaxHorizontalVelocity() || direction == Direction.STALLED)
			return 0;
		return horizontalAcceleration*direction.getSign();
	}
	
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
					if(difference<(hitPoint.getMaximum()-hitPoint.getCurrent())){
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
	
	@Basic
	public School getSchool(){
		return school;
	}
	
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			this.world = world;
			world.addSchool(getSchool());
			world.addSlime(this);
			if(overlapsWithWall()[0]){
				groundState = GroundState.GROUNDED;
			}else{
				groundState = GroundState.AIR;
			}
		}
	}
	
	@Override
	protected boolean canHaveAsWorld(World world){
		if(!world.isTerminated() && !this.isTerminated() && this.world==null
				//&& world.getSchools().contains(school)
				)
			return true;
		return false;
	}
	
	public String toString(){
		return "slime of( " + getSchool() + ") with hp: " + hitPoint.getCurrent();
	}
	
	public void schoolHpLoss(){
		for(Slime slime:school.getSlimes()){
			if(slime != this)
				slime.loseHp(1);
		}
	}
}
