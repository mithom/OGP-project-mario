package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.util.Sprite;

public class Plant extends GameObject{
	
	public Plant(int x, int y, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(x,y,sprites);
	}
	
	
}
