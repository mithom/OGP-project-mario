package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Mazub;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.GeologicalFeature;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.state.DuckState;

public class IsDucking extends Expression {

	public Object object;
	public Object direction;

	public IsDucking(Object object, Object direction, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
		this.direction = direction;
	}

	@Override
	public Object evaluate()  {
		if ((GameObject)object instanceof Mazub){
			return ((Mazub)object).getDuckState() == DuckState.DUCKED;
			}
		return false;  
	}

}
