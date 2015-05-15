package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsJumping extends Expression {

	public Object object;
	
	public IsJumping(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object=object;
	}

	@Override
	public Object evaluate() {
		if ((GameObject)object instanceof Mazub){
			return ((Mazub)object).getVerticalVelocity() > 0;
		}	
		if ((GameObject)object instanceof Shark){
			return ((((Shark)object).getVerticalVelocity() > 0) && (((Shark)object).getVerticalAcceleration() == 0)) ;
		}
		return false;
	}

}
