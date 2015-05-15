package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class Multiplication extends Expression {
	
	public Object leftValue;
	public Object rightValue;
	
	public Multiplication(Object left, Object right, SourceLocation sourceLocation){
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
		return (((DoubleConstant)getRightValue()).getValue() * ((DoubleConstant)getRightValue()).getValue()) ;
	}

}
