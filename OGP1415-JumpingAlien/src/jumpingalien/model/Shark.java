package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.util.Sprite;

public class Shark extends GameObject{
	public Shark(int x, int y, Sprite[] sprites)throws PositionOutOfBoundsException{
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
			world.addShark(this);
		}
	}
}
