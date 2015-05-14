package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class LessThan extends Comparison {

	public LessThan(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	public boolean evaluateLessThan(){
		return (left.getValue() < right.getValue());
	}

}
