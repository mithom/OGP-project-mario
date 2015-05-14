package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class LessThanOrEqualTo extends Comparison {

	public LessThanOrEqualTo(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	
	public boolean evaluateLessThanOrEqualTo(){
		return (left.getValue() <= right.getValue());
	}

	
}
