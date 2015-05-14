package expressions;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetY extends Expression {
	
	public Object object;
	
	public GetY(Object object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	public double GetYCoordinate(){
		return ((GameObject)getObject()).getPositionY();
	}


}
