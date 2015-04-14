package jumpingalien.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Slime> slimes = new ArrayList<Slime>();
	private World world;
	
	public School(){
		
	}
	
	public void addWorld(World world){
		if(world.getSchools().contains(this)){
			this.world = world;
		}
	}
	
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<Slime>(slimes);
	}
	
	public void addSlime(Slime slime){
		if(slime.getSchool()==this){
			slimes.add(slime);
		}
	}
	
	public void removeSlime(Slime slime){
		if(this.hasAsSlime(slime)){
			slimes.remove(slime);
			if(getSize()==0){
				world.removeSchool(this);
			}
		}
	}
	
	private boolean hasAsSlime(Slime slime){
		if(slimes.contains(slime)){
			return true;
		}
		return false;
	}
	
	public World getWorld(){
		return world;
	}
	
	public int getSize(){
		return slimes.size();
	}
	
	public String toString(){
		return "school nr:"+ world.getSchools().indexOf(this)+"of size "+getSize();
	}
}
