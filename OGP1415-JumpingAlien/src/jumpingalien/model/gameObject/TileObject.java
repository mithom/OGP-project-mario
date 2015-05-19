package jumpingalien.model.gameObject;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

public class TileObject extends GameObject {
	private GeologicalFeature geologicalFeature;

	protected TileObject(int pixelLeftX, int pixelBottomY,GeologicalFeature geologicalFeature)
			throws PositionOutOfBoundsException {
		super(pixelLeftX, pixelBottomY, new Sprite[]{});
		this.geologicalFeature = geologicalFeature;
	}

	@Override @Deprecated
	public void addToWorld(World world) {
	}

	@Override @Deprecated
	public void advanceTime(double dt) throws NullPointerException,
			IllegalMovementException, IllegalMazubStateException,
			IllegalTimeException, PositionOutOfBoundsException,
			IllegalSizeException {
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
	
}
