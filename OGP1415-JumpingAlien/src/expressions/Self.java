package expressions;

import jumpingalien.part3.programs.SourceLocation; 

public class Self extends Expression {

	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	
	public Object getSelf(){
		if (getProgram() != null)
			return this.getProgram().getGameObject();
		return null;
	}
	
}

