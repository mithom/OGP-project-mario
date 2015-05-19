package jumpingalien.model.gameObject;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

public class TyleObject extends GameObject {
	private GeologicalFeature geologicalFeature;

	protected TyleObject(int pixelLeftX, int pixelBottomY,GeologicalFeature geologicalFeature)
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

}
