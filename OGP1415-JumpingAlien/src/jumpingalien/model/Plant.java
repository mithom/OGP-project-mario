package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.HitPoint;
import jumpingalien.state.Direction;
import jumpingalien.util.Sprite;
import jumpingalien.model.World;

public class Plant extends GameObject{
	private Direction direction;
	private final double horizontalVelocity=0.5d;
	private double actionTimer;
	
	public Plant(int x, int y, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(x,y,sprites);
		hitPoint = new HitPoint(0, 1, 1);
		actionTimer=0.0d;
		direction = Direction.RIGHT;
	}
	
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
	
	private void animate(){
		if(direction == Direction.RIGHT)
			currentSpriteNumber=1;
		else
			currentSpriteNumber=0;
	}
	
	private void moveHorizontal(double dt) throws PositionOutOfBoundsException{
		if(actionTimer+dt < 0.5d){
			actionTimer += dt;
			double oldPositionX = getPositionX();
			setPositionX(oldPositionX +dt*direction.getMultiplier()*horizontalVelocity);
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
	
	private void consume(Mazub alien){
		if(alien.hasMaxHp()==false && terminated == false){
			alien.addHp(50);
			loseHp(1);
		}
	}
	
	@Override
	public void addToWorld(World world){
		if(this.world == null && canHaveAsWorld(world)){
			this.world = world;
			world.addPlant(this);
		}
	}
	
	public void EffectOnCollisionWith(GameObject gameObject){
		if(gameObject instanceof Mazub){
			consume((Mazub)gameObject);
		}
	}
}
