package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class Not extends Expression {
	
	public Object expression;
	
	public Not(Object expression, SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public Object getExpression(){
		return expression;
	}

	@Override
	public Object evaluate() {
		return !(((BooleanExpression)getExpression()).getValue());
	}
	
}
