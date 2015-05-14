package expressions;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetHp extends Expression {
	
	public Object object;
	
	public GetHp(Object object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	public double evaluateGetHp(){
		return ((GameObject)getObject()).getNbHitPoints();
	}
}
