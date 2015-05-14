package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class And extends Expression {

	public BooleanExpression leftExpression;
	public BooleanExpression rightExpression;
	
	public And(BooleanExpression left, BooleanExpression right, SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public BooleanExpression getLeftExpression(){
		return leftExpression;
	}
	
	public BooleanExpression getRightExpression(){
		return rightExpression;
	}
	
	public boolean evaluateAnd(){
		return (leftExpression.getValue() && rightExpression.getValue());
	}
	
}
