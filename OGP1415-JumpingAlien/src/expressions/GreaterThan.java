package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class GreaterThan extends Comparison {

	public GreaterThan(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	public boolean evaluateGreaterThan(){
		return (left.getValue() > right.getValue());
	}

}
