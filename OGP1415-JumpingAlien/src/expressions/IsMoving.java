package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends Expression {
	
	public Object object;
	public Object direction;

	public IsMoving(Object object, Object direction, SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Object evaluate() {
		if ((GameObject)object instanceof Mazub){
			return ((Mazub)object).getHorizontalVelocity() != 0;
		}
		
		if ((GameObject)object instanceof Shark){
			return ((Shark)object).getHorizontalVelocity() != 0;
		}
		if ((GameObject)object instanceof Slime){
			return ((Slime)object).getHorizontalVelocity() != 0;
		}
		//Plant is always moving
		if ((GameObject)object instanceof Plant && ((GameObject)object).isTerminated()==false){
			return true;
		}
		return false;
	}

}
