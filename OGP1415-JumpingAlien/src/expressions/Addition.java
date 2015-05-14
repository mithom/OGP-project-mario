package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Addition extends Expression {
	
	public DoubleConstant leftValue;
	public DoubleConstant rightValue;
	
	public Addition(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(sourceLocation);
		this.leftValue = left;
		this.rightValue = right;
	}
	
	public DoubleConstant getLeftValue(){
		return leftValue;
	}
	
	public DoubleConstant getRightValue(){
		return rightValue;
	}
	
	public double evaluateAddition(){
		return (leftValue.getValue() + rightValue.getValue()) ;
	}
}
