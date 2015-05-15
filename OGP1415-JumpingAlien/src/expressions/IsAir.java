package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.GeologicalFeature;
import jumpingalien.part3.programs.SourceLocation;

public class IsAir extends Expression {

	public Object object;

	public IsAir(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}

	@Override
	public Object evaluate()  {
		return  object == GeologicalFeature.air;
	}

}
