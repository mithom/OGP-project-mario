package jumpingalien.part1.facade;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.Direction;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
/**
 *
 */
public class Facade implements IFacade{

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) 
			throws ModelException{
		try{
			return new Mazub(pixelLeftX,pixelBottomY, sprites);
		}catch(PositionOutOfBoundsException e){
			throw new ModelException("illegal possition");
		}
	};
	
	@Override
	public int[] getLocation(Mazub alien) throws ModelException{
		try{
			return new int[]{alien.getPixel_x(),alien.getPixel_y()};
		}catch(PositionOutOfBoundsException e){
			throw new ModelException("postion outside screen: " + e.getLocation().toString());
		}
	};

	@Override
	public double[] getVelocity(Mazub alien){
		//direction!!! statesign controleren!
		return new double[]{alien.getHorizontalVelocity(),alien.getVerticalVelocity()};
	};

	@Override
	public double[] getAcceleration(Mazub alien){
		//direction!!! statesign controleren!
		return new double[]{alien.getHorizontalAcceleration(),alien.getVerticalAcceleration()};
	};

	@Override
	public int[] getSize(GameObject alien) throws ModelException{
		try{
			return alien.getSize();
		}catch(NullPointerException e){
			throw new ModelException(e.getMessage());
		}catch(IllegalSizeException ex){
			throw new ModelException("illegal size of currentSprite: "+ ex.getSize().toString());
		}
	};

	@Override
	public Sprite getCurrentSprite(GameObject alien){
		return alien.getCurrentSprite();
	};

	@Override
	public void startJump(Mazub alien) throws ModelException{
		try{
			alien.startJump();
		}catch(IllegalMazubStateException e){
			throw new ModelException("groundState is null");
		}
	};

	@Override
	public void endJump(Mazub alien){
		alien.endJump();
	};

	@Override
	public void startMoveLeft(Mazub alien){
		alien.startMove(Direction.LEFT);
	};

	@Override
	public void endMoveLeft(Mazub alien){
		alien.endMove(Direction.LEFT);
	};

	@Override
	public void startMoveRight(Mazub alien){
		alien.startMove(Direction.RIGHT);
	};

	@Override
	public void endMoveRight(Mazub alien){
		alien.endMove(Direction.RIGHT);
	};

	@Override
	public void startDuck(Mazub alien){
		alien.startDuck();
	};

	@Override
	public void endDuck(Mazub alien){
		alien.endDuck();
	};

	@Override @Deprecated
	public void advanceTime(Mazub alien, double dt) throws ModelException{
		try{
			alien.advanceTime(dt);
		}catch(IllegalTimeException e){
			throw new ModelException("illegal argument for dt: " + e.getTime());
		}catch(IllegalMazubStateException ex){
			throw new ModelException("invalid state of mazub");
		}catch(IllegalMovementException exc){
			throw new ModelException(exc.getMessage());
		}catch(PositionOutOfBoundsException exce){
			throw new ModelException("failed");
		}
	}
}
