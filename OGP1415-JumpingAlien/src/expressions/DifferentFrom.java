package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class DifferentFrom extends Comparison {

	public DifferentFrom(Object left, Object right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}

	@Override
	public Object evaluate() {
		return (getLeftValue() != getRightValue());
	}

}