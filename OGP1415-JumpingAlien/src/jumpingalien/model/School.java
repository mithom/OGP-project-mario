package jumpingalien.model;

import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * School is a class representing a group of slimes in a world
 * @author Meerten Wouter & Michiels Thomas
 * @version 1.0
 *
 */
public class School {
	private ArrayList<Slime> slimes = new ArrayList<Slime>();
	private World world;
	
	public School(){
		
	}
	
	/**
	 * 
	 * @param world | the world to which a school needs to be added
	 * @Post if the given world contains the school, the world of this school wil be set to world
	 * 		 | if world.getSchools().contains(this)
	 * 		 | 		then this.world = world
	 */
	public void addWorld(World world){
		if(world.getSchools().contains(this)){
			this.world = world;
		}
	}
	
	/**
	 * returns a list of slimes
	 * @return ArrayList<Slime>(slimes)
	 */
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<Slime>(slimes);
	}
	
	/**
	 * 
	 * @param slime | the slime that needs to be added to the school
	 * @Post if the slime belongs to this school, the slime will be added to the school
	 * 		| if slime.getSchool()=this
	 * 		|		then slimes.add(slime)
	 */
	public void addSlime(Slime slime){
		if(slime.getSchool()==this){
			slimes.add(slime);
		}
	}
	
	/**
	 * 
	 * @param slime | the slime that needs to be removed from the school
	 * @Post if this school has the given slime in it, it will be removed 
	 * 		| if this.hasAsSlime(slime)
	 * 		|		then slimes.remove(slime)
	 * @Post if a slime is removed and the size of this school is 0, the school will be removed
	 * 		| if this.hasAsSlime(slime) && getSize()=0
	 * 		|		then world.removeSchool(this)
	 */
	
	public void removeSlime(Slime slime){
		if(this.hasAsSlime(slime)){
			slimes.remove(slime);
			if(getSize()==0){
				world.removeSchool(this);
			}
		}
	}
	
	/**
	 * 
	 * @param slime | the slime that needs to be checked wether it is in this school or not	
	 * @return returns true if this school contains the given slime
	 * 			| if slimes.contains(slime)
	 * 			|	then true
	 * 			| else false
	 */
	
	private boolean hasAsSlime(Slime slime){
		if(slimes.contains(slime)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return returns the world that contains this school 
	 */
	@Basic 
	public World getWorld(){
		return world;
	}
	
	/**
	 * 
	 * @return returns the size of this school
	 */
	@Basic
	public int getSize(){
		return slimes.size();
	}

	public String toString(){
		return "school nr:"+ world.getSchools().indexOf(this)+"of size "+getSize();
	}
}
