package jumpingalien.program.internal;

public class Value<R> {
	
	private R value;
	
	public Value(R value){
		this.value = value;
	}
	
	public Value(){};
	
	public R evaluate(){
		return value;
	}
}
