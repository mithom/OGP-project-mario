package expressions;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetHp extends Expression {
	public GameObject object;
	
	public GetHp(GameObject object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public GameObject getObject(){
		return object;
	}
	
	public double evaluateGetHp(){
		return getObject().getNbHitPoints();
	}
}
