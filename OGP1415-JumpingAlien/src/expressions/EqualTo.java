package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class EqualTo extends Comparison {

	public EqualTo(Object left, Object right, SourceLocation sourceLocation){
		super(left,right,sourceLocation);
	}

	@Override
	public Object evaluate() {
		return (getLeftValue() == getRightValue());
	}

}
