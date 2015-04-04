package jumpingalien.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Slime> slimes = new ArrayList<Slime>();
	
	public School(){
		
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
		}
	}
	
	private boolean hasAsSlime(Slime slime){
		if(slimes.contains(slime)){
			return true;
		}
		return false;
	}
}
