package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation; 

public class Self extends Expression {

	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	@Override
	public Object evaluate() {
		if (getProgram() != null)
			return this.getProgram().getGameObject();
		return null;
	}
	
}

