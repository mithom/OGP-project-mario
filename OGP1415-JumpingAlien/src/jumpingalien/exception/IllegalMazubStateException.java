package jumpingalien.exception;

import jumpingalien.model.gameObject.GameObject;
import be.kuleuven.cs.som.annotate.Basic;

public class IllegalMazubStateException extends IllegalStateException{
	private GameObject alien;
	
	public IllegalMazubStateException(GameObject alien){
		this.alien  = alien;
	}
	static final long serialVersionUID = -1848914673093119416L;
	
	@Basic
	public GameObject getMazub(){
		return this.alien;
	}
}
