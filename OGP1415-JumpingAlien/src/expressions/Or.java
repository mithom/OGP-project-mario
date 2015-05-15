package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class Or extends Expression {
	
	public Object leftExpression;
	public Object rightExpression;
	
	public Or(Object left, Object right, SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public Object getLeftExpression(){
		return leftExpression;
	}
	
	public Object getRightExpression(){
		return rightExpression;
	}

	@Override
	public Object evaluate()  {
		return (((BooleanExpression)getLeftExpression()).getValue() || ((BooleanExpression)getRightExpression()).getValue());
	}
	
	
}
