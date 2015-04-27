package jumpingalien.model.gameObject;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * HitPoint is a class representing a the lifepoints of a character/GameObject in the game. 
 * @author Meerten Wouter & Michiels Thomas (both 2de fase ingenieurswetenschappen)
 * @version 1.0
 * @Invar 	the current hitPoints of a gameobject will never be more than the allowed maximum
 * 			or less than the minimum
 */
public class HitPoint {
	private final int minimum;
	private final int maximum;
	private int current;
	
	/**
	 * 
	 * @param min | the minimum number of hitpoints that is allowed	
	 * @param max | the maximum number of hitpoints that is allowed
	 * @param start | the number of hitpoints that a gameobject will start with
	 */
	
	public HitPoint(int min,int max,int start){
		minimum = min;
		maximum = max;
		current = start;
	}
	
	/**
	 * returns the maximum number of hitpoints
	 * @return |maximum
	 */
	@Basic
	public int getMaximum(){
		return maximum;
	}
	
	/**
	 * returns the minimum number of hitpoints
	 * @return |maximum
	 */
	
	@Basic
	public int getMinimum(){
		return minimum;
	}
	
	/**
	 * returns the current number of hitpoints
	 * @return |current
	 */
	
	@Basic
	public int getCurrent(){
		return current;
	}
	
	/**
	 * adds a certain amount of hitpoints if possible. If amount is negative, nothing will happen
	 * @param amount | the amount of hitpoints that needs to be added
	 * @Post if amount is positive, it will be added to the current number of hitpoints as long as the 
	 * 		 result isn't larger than the maximum allowed number of hitpoints. If this is the case, current
	 * 		 hitpoints will be set to the maximum amount
	 * 			| if amount>0 
	 * 			| 		then current= Math.min(current + amount, getMaximum())
	 */
	
	public void addHP(int amount){
		if(amount <0) amount =0;
		current = Math.min(current + amount, getMaximum());
	}
	
	/**
	 * removes a certain amount of hitpoints if possible. If amount is negative, nothing will happen
	 * @param amount | the amount of hitpoints that needs to be removed
	 * @Post if amount is positive, it will be removed from the current number of hitpoints as long as the 
	 * 		 result isn't less than the minimum allowed number of hitpoints. If this is the case, current
	 * 		 hitpoints will be set to the minimum amount
	 * 			| if amount>0 
	 * 			| 		then current= Math.min(current - amount, getMinimum())
	 */
	
	public void loseHP(int amount){
		if(amount < 0) amount = 0;
		current = Math.max(current - amount,getMinimum());
	}
	
	/**
	 * checks if the object is dead
	 * @return true if current hitpoints is less than or equal to 0 
	 * 			| if this.getCurrent<=0
	 * 			| 	then true
	 * 			| else   false
	 */
	public boolean isDead(){
		if(getCurrent()<= 0 )
			return true;
		return false;
	}
}
