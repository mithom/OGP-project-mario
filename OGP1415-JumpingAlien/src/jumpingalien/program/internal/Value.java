package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Value<R extends Object> {
	private Program program;
	private boolean done;
	private R value;
	
	public Value(R value){
		done=false;
		this.value = value;
	}
	
	public Value(){
		done=false;
	};
	
	public R evaluate(double[] dt){
		if(!isDone()){
			if(dt[0]>0.0){
				setDoneTrue(dt);
				return value;
			}else{
				return null;
			}
		}
		return value;
	}
	
	public void reset(){
		done = false;
	}
	
	public void addProgram(Program program){
		this.program = program;
	}
	
	public Program getProgram(){
		return program;
	}
	
	protected Value<R> Copy(){
		Value<R> copy = new Value<R>(value);
		copy.addProgram(program);
		copy.done = done;
		return copy;
	}
	
	public void setDoneTrue(double[] dt){
		//dt[0]-= 0.001d; //base values don't need to consume time to read
		done = true;
	}
	
	protected void setDoneFalse(){
		done=false;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public String toString(){
		return "value: "+ value;
	}
}
