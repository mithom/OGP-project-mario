package statements;

import java.util.HashMap;
import java.util.Map;

import Program.Program;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.internal.Type;

public class Assignment extends Statement {
	
	public String name;
	public Object variableType;
	public Object value;

	public Assignment(String variableName, Object variableType, Object value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.name=variableName;
		this.variableType=variableType;
		this.value=value;
	}
	
	public String getName(){
		return name;
	}
	
	public Object getType(){
		return variableType;
	}
	
	public Object getValue(){
		return value;
	}
	
	public void setValue(Object assignmentValue){
		value = assignmentValue;
	}

	@Override
	public Object evaluate() {
		if (getType() == Type.type.DOUBLE){
			//TODO hoe krijgt ge key uit hashmap???
			Program.doubles.put(getProgram().getAllDoubles().get(getName()), getValue());
		}
		if (getType() == Type.type.OBJECT){
			//TODO hoe krijgt ge key uit hashmap???
			Program.Objects.put(getProgram().getAllDoubles().get(getName()), getValue());
		}
		if (getType() == Type.type.DIRECTION){
			//TODO hoe krijgt ge key uit hashmap???
			Program.directions.put(getProgram().getAllDoubles().get(getName()), getValue());
		}
		if (getType() == Type.type.BOOLEAN){
			//TODO hoe krijgt ge key uit hashmap???
			Program.booleans.put(getProgram().getAllDoubles().get(getName()), getValue());
		}
		return "Evaluation complete";
	}
	
}
