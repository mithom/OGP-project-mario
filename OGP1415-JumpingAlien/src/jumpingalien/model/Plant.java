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
				if(gameObject instanceof Mazub){
					consume((Mazub)gameObject);
				}
			}
		}
	}
	
	private void moveHorizontal(double dt) throws PositionOutOfBoundsException{
		if(actionTimer+dt < 0.5d){
			actionTimer += dt;
			double oldPositionX = getPositionX();
			setPositionX(oldPositionX +dt*direction.getSign()*horizontalVelocity);
			if(this.overlapsWithWall()[1]==true && direction.getSign()<0){
				setPositionX(oldPositionX);
				direction = Direction.RIGHT;
				actionTimer = 0.0d;
			}
			if( overlapsWithWall()[3]==true && direction.getSign()>0){
				setPositionX(oldPositionX);
				direction = Direction.LEFT;
				actionTimer = 0.0d;
			}
		}else{
			actionTimer = (actionTimer+dt)-0.5d;//TODO exacte omkering maken, dit is niet exact
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
			//loseHp(1);
			world.removeGameObject(this);
			world = null;
			terminated = true;
		}
	}
	
	@Override
	public void addToWorld(World world){
		if(this.world == null && canHaveAsWorld(world)){
			this.world = world;
			world.addPlant(this);
		}
	}
}
