package jumpingalien.model;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.Direction;
import jumpingalien.util.Sprite;

public class Buzam extends GameObject {
	
	public Buzam(int X, int Y, Sprite[] sprites)throws PositionOutOfBoundsException {
		super(X,Y,sprites);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addToWorld(World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void advanceTime(double dt) throws NullPointerException,
			IllegalMovementException, IllegalMazubStateException,
			IllegalTimeException, PositionOutOfBoundsException,
			IllegalSizeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void EffectOnCollisionWith(GameObject gameObject) {
		// TODO Auto-generated method stub

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
	public void startMove(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMove(Direction direction) {
		// TODO Auto-generated method stub
		
	}

}
