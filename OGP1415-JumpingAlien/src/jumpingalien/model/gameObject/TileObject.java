package jumpingalien.model.gameObject;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.state.Direction;
import jumpingalien.util.Sprite;

public class TileObject extends GameObject {
	private GeologicalFeature geologicalFeature;

	public TileObject(int pixelX, int pixelY,GeologicalFeature geologicalFeature){
		super(pixelX, pixelY, new Sprite[]{});
		this.geologicalFeature = geologicalFeature;
	}

	@Override @Deprecated
	public void addToWorld(World world) {
	}

	@Override @Deprecated
	public void advanceTime(double dt) throws NullPointerException,
			IllegalMovementException, IllegalMazubStateException,
			IllegalTimeException, PositionOutOfBoundsException {
	}

	@Override @Deprecated
	public void EffectOnCollisionWith(GameObject gameObject) {
	}
	
	public GeologicalFeature getGeologicalFeature(){
		return geologicalFeature;
	}
	
	public int getGeologicalFeatureNr(){
		return geologicalFeature.getEquivalentNumberType();
	}

	public boolean isPassable(){
		if (getGeologicalFeature() != GeologicalFeature.solidGround){
			return true;
		}
		return false;
	}
	
	public boolean isWater(){
		if (getGeologicalFeature() != GeologicalFeature.water){
			return true;
		}
		return false;
	}
	
	public boolean isMagma(){
		if (getGeologicalFeature() != GeologicalFeature.magma){
			return true;
		}
		return false;
	}
	
	public boolean isAir(){
		if (getGeologicalFeature() != GeologicalFeature.air){
			return true;
		}
		return false;
	}

	@Override @Deprecated
	public void startJump() {}

	@Override @Deprecated
	public void endJump() {}

	@Override @Deprecated
	public void startDuck() {}

	@Override @Deprecated
	public void endDuck() {}

	@Override @Deprecated
	public void startMove(Direction direction) {}

	@Override @Deprecated
	public void endMove(Direction direction) {}
	
	@Override
	public boolean isMoving(Program.Direction direction){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString(){
		return "tile of type: "+geologicalFeature.toString();
	}

	@Override @Deprecated
	public void EffectOnCollisionWithReversed(GameObject gameObject) {
		return ;
	}
	
	@Override
	public int[] getSize(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getNbHitPoints(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isDead(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isJumping(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isDucking(){
		throw new UnsupportedOperationException();
	}
	
}
