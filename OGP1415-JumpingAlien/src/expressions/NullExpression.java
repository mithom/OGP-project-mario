package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class NullExpression extends Expression {
	
	public NullExpression(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public Object evaluate() {
		return null;
	}
}
