package jumpingalien.model.gameObject;

import be.kuleuven.cs.som.annotate.Basic;

public class HitPoint {
	private final int minimum;
	private final int maximum;
	private int current;
	
	public HitPoint(int min,int max,int start){
		minimum = min;
		maximum = max;
		current = start;
	}
	
	@Basic
	public int getMaximum(){
		return maximum;
	}
	
	@Basic
	public int getMinimum(){
		return minimum;
	}
	
	@Basic
	public int getCurrent(){
		return current;
	}
	
	public void addHP(int amount){
		if(amount <0) amount =0;
		current = Math.min(current + amount, getMaximum());
	}
	
	public void loseHP(int amount){
		if(amount < 0) amount = 0;
		current = Math.max(current - amount,getMinimum());
	}
	
	public boolean isDead(){
		if(getCurrent()<= 0 )
			return true;
		return false;
	}
}
