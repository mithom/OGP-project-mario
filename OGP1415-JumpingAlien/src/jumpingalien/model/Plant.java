package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.util.Sprite;
import jumpingalien.model.World;

public class Plant extends GameObject{
	
	public Plant(int x, int y, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(x,y,sprites);
	}
	
	@Override
	public void advanceTime(double dt){
		//TODO implement this function
	}
	
	@Override
	public void addToWorld(World world){
		if(this.world == null && canHaveAsWorld(world)){
			this.world = world;
			world.addPlant(this);
		}
	}
}
