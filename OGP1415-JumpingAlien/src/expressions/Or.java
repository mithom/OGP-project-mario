package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Or extends Expression {
	
	public BooleanExpression leftExpression;
	public BooleanExpression rightExpression;
	
	public Or(BooleanExpression left, BooleanExpression right, SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public BooleanExpression getLeftExpression(){
		return leftExpression;
	}
	
	public BooleanExpression getRightExpression(){
		return rightExpression;
	}
	
	public boolean evaluateOr(){
		return (leftExpression.getValue() || rightExpression.getValue());
	}
	
	
}
