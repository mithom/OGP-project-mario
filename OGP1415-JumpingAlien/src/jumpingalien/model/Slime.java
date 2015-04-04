package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.util.Sprite;

public class Slime extends GameObject{
	private School school;
	
	public Slime(int x, int y, Sprite[] sprites,School school) throws PositionOutOfBoundsException{
		super(x,y,sprites);
		setSchool(school);
	}
	
	public void setSchool(School school){
		if(getSchool() != null){
			this.school.removeSlime(this);
		}
		this.school = school;
		school.addSlime(this);
	}
	
	@Basic
	public School getSchool(){
		return school;
	}
}
