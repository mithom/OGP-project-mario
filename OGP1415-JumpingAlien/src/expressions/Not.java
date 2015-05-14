package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Not extends Expression {
	
	public BooleanExpression expression;
	
	public Not(BooleanExpression expression, SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public BooleanExpression getExpression(){
		return expression;
	}
	
	public boolean evaluateNot(){
		return !(expression.getValue());
	}
	
}
