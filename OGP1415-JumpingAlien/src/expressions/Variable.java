package expressions;

import Program.Program;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.internal.Type;

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

	@Override
	public Object evaluate() {
		if (getType() == Type.type.DOUBLE){
			return (Object)(this.getProgram().getAllDoubles().get(getName()));
		}
		if (getType() == Type.type.OBJECT){
			return (Object)(this.getProgram().getAllObjects().get(getName()));
		}
		if (getType() == Type.type.DIRECTION){
			return (Object)(this.getProgram().getAllDirections().get(getName()));
		}
		if (getType() == Type.type.BOOL){
			return (Object)(this.getProgram().getAllBooleans().get(getName()));
		}
		return "error: variable isn't a supported type";
	}
	
	
}
