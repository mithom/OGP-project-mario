package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends Expression {
	
	public Object object;
	public Object direction;

	public IsMoving(Object object, Object direction, SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Object evaluate() {
		return ((GameObject)object. )
	}

}
