package jumpingalien.model;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;

public class Program {
	
	private GameObject gameObject;
	private Statement statement;
	
	public Program(){
		
	}
	
	public void setGameObject(GameObject gameObject){//TODO add checkers
		this.gameObject = gameObject;
	}
	
	public GameObject getGameObject(){
		return gameObject;
	}
	
	private double executeNext(double dt){
		return statement.executeNext(dt);
	}
	
	public void executeTime(double dt){
		while(dt>0){
			dt = executeNext(dt);
		}
		//TODO: implement this function
	}
	
	public void addStatement(Statement statement){//TODO add checkers
		this.statement = statement;
		statement.addProgram(this);
	}
	
	public enum Direction {
		UP,LEFT,DOWN,RIGHT;
	}
	
	private HashMap<String, Boolean> booleans = new HashMap<String,Boolean>();
	private HashMap<String,GameObject> Objects = new HashMap<>();
	private HashMap<String,Double> directions = new HashMap<>();
	private HashMap<String,Direction> doubles = new HashMap<>();

	public void addGlobal(String text, Type type){
		Type.type kind = type.getType();
		if(kind == Type.type.BOOL){
			booleans.put(text,null);
		}
		if(kind == Type.type.DIRECTION){
			directions.put(text, null);
		}
		if(kind == Type.type.OBJECT){
			Objects.put(text,null);
		}
		if(kind == Type.type.DOUBLE){
			doubles.put(text, null);
		}
	}
	
	public void addAllGlobals(Map<String,Type> globals){
		for(String key:globals.keySet()){
			addGlobal(key, globals.get(key));
		}
	}
	
	public void resetGlobals(){
		
	}
}
