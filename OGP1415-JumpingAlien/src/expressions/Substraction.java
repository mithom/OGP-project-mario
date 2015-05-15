package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class Substraction extends Expression {
	
	public Object leftValue;
	public Object rightValue;
	
	public Substraction(Object left, Object right, SourceLocation sourceLocation){
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
	public Object evaluate() throws NullPointerException, IllegalSizeException {
		return (((DoubleConstant)getLeftValue()).getValue() - ((DoubleConstant)getRightValue()).getValue()) ;
	}

}
