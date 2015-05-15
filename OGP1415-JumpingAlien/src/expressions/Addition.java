package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Addition extends Expression {
	
	public Object leftValue;
	public Object rightValue;
	
	public Addition(Object left, Object right, SourceLocation sourceLocation){
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
		return (((DoubleConstant)getRightValue()).getValue() + ((DoubleConstant)getLeftValue()).getValue()) ;
	}
}
