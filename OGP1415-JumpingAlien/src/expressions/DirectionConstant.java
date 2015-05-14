package expressions;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class DirectionConstant extends Expression {
	
	public Direction direction;
	
	public DirectionConstant(SourceLocation sourceLocation, Direction direction){
		super(sourceLocation);
		this.direction=direction;
	}
	
	public Direction getDirection(){
		return direction;
	}
}
