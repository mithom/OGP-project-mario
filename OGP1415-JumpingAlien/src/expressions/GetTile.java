package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends Expression {
	
	public Object x;
	public Object y;
	public double[] position;
	
	public GetTile(Object x, Object y, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.x= x;
		this.y= y;
	}
	
	
	public Object getX(){
		return x;
	}
	
	public Object getY(){
		return y;
	}
	
	public double[] getPosition(){
		position[0]=((DoubleConstant)getX()).getValue();
		position[1]=((DoubleConstant)getY()).getValue();
		return position;
	}
	
	//TODO deze static dingen moeten nog eens later bekeken worden tegoei

	@Override
	public Object evaluate(){
		return World.getTileOfPosition((this.getProgram().getGameObject()).getPosition());
	}
}
