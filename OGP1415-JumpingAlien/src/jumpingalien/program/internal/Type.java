package jumpingalien.program.internal;

public class Type {
	public enum type{
		BOOL,OBJECT,DOUBLE,DIRECTION;
	}
	
	public Type(type value){
		this.value = value;
	}
	
	private final type value;
	
	public type getType(){
		return value;
	}
}
