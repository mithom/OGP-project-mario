package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class GreaterThanOrEqualTo extends Comparison {

	public GreaterThanOrEqualTo(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}

	@Override
	public Object evaluate() throws NullPointerException, IllegalSizeException {
		return (getLeftValue() >= getRightValue());
	}

}