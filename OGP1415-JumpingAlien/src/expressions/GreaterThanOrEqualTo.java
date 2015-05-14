package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class GreaterThanOrEqualTo extends Comparison {

	public GreaterThanOrEqualTo(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	public boolean evaluateGreaterThanOrEqualTo(){
		return (left.getValue() >= right.getValue());
	}

}