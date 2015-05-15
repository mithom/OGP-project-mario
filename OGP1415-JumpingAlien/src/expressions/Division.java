package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Division extends Expression {
	
	public Object leftValue;
	public Object rightValue;
	
	public Division(Object left, Object right, SourceLocation sourceLocation){
		super(sourceLocation);
		this.leftValue = left;
		this.rightValue = right;
	}
	
	public Object getLeftValue(){
		return leftValue;
	}
	
	public Object getRightValue(){
		return rightValue;
	}

	@Override
	public Object evaluate() {
		return (((DoubleConstant)getLeftValue()).getValue() / ((DoubleConstant)getRightValue()).getValue()) ;
		
	}
	
}
