package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Variable extends Expression {
	
	public Object type;
	public String name;
	
	public Variable(String name, Object type, SourceLocation sourceLocation){
		super(sourceLocation);
		this.type=type;
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public Object getType(){
		return type;
	}
	
	
}
