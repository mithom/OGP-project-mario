package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Division extends Expression {
	
	public DoubleConstant leftValue;
	public DoubleConstant rightValue;
	
	public Division(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
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
	
	public double evaluateDivision(){
		return (leftValue.getValue() + rightValue.getValue()) ;
	}
	
}
