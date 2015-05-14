package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends Expression {
	
	public DoubleConstant x;
	public DoubleConstant y;
	public double[] position;
	
	public GetTile(DoubleConstant x, DoubleConstant y, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.x= x;
		this.y= y;
	}
	
	
	public DoubleConstant getX(){
		return x;
	}
	
	public DoubleConstant getY(){
		return y;
	}
	
	public double[] getPosition(){
		position[0]=getX().getValue();
		position[1]=getY().getValue();
		return position;
	}
	
	public int[] evaluateGetTile(){
		return World.getTileOfPosition((this.getProgram().getGameObject()).getPosition());
	}
}
