package expressions;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetX extends Expression {
	
	public Object object;
	
	public GetX(Object object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	public double GetXCoordinate(){
		return ((GameObject) getObject()).getPositionX();
	}

}
