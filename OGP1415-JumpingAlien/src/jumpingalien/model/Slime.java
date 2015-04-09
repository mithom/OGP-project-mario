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
			int difference = 0;
			this.school.removeSlime(this);
			for(Slime slime:this.school.getSlimes()){
				if(!slime.hasMaxHp()){
					slime.addHp(1);
					difference-=1;
				}
			}
			for(Slime slime:school.getSlimes()){
				if(difference<(hitPoint.getMaximum()-hitPoint.getCurrent())){
					slime.loseHp(1);
					difference+=1;
				}
			}
			this.addHp(difference);
			
		}
		this.school = school;
		school.addSlime(this);
	}
	
	@Basic
	public School getSchool(){
		return school;
	}
	
	@Override
	public void advanceTime(double dt){
		//TODO implement this function
	}
	
	@Override
	public void addToWorld(World world){
		if(this.world == null && canHaveAsWorld(world)){
			this.world = world;
			world.addSlime(this);
		}
	}
	
	@Override
	protected boolean canHaveAsWorld(World world){
		if(!world.isTerminated() && !this.isTerminated() && this.world==null
				&& world.getSchools().contains(school))
			return true;
		return false;
	}
}
