package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class EqualTo extends Comparison {

	public EqualTo(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}
	
	public boolean evaluateEqualTo(){
		return (left.getValue() == right.getValue());
	}

}
