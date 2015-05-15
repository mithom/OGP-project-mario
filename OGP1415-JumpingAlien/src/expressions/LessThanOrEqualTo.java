package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class LessThanOrEqualTo extends Comparison {

	public LessThanOrEqualTo(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	@Override
	public Object evaluate() {
		return (getLeftValue() <= getRightValue());
	}

	
}
