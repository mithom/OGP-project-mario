package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class DifferentFrom extends Comparison {

	public DifferentFrom(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	public boolean evaluateDifferentFrom(){
		return (left.getValue() != right.getValue());
	}

}