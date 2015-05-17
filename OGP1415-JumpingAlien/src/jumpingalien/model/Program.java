package jumpingalien.model;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;

public class Program {
	private GameObject gameObject;
	private Statement statement;
	public enum Direction {
		UP,LEFT,DOWN,RIGHT;
	}
	
	private HashMap<String, Boolean> booleans = new HashMap<String,Boolean>();
	private HashMap<String,GameObject> Objects = new HashMap<>();
	private HashMap<String,Direction> directions = new HashMap<>();
	private HashMap<String,Double> doubles = new HashMap<>();

	public Program(){
		
	}
	
	public void setGameObject(GameObject gameObject){//TODO add checkers
		this.gameObject = gameObject;
	}
	
	public GameObject getGameObject(){
		return gameObject;
	}
	
	private void executeNext(double[] dt){
		//System.out.println("tijd bij ingaan"+dt[0]);
		statement.executeNext(dt);
		//System.out.println("tijd bij uitgaan:"+dt[0]);
	}
	
	public void executeTime(double dt){
		double[] dt2 = new double[]{dt};
		//System.out.println("begin program");
		while(dt2[0]>0){
			//System.out.println("tijd niet op");
			//System.out.println(dt2[0]);
			executeNext(dt2);
			//System.out.println("tijd bij buitenkomen"+dt2[0]);
		}
	}
	
	public void addStatement(Statement statement){//TODO add checkers
		this.statement = statement;
		statement.addProgram(this);
	}

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
		//TODO: implement this function
	}
	
	
	public Boolean getBoolean(String key) {
		return booleans.get(key);
	}

	public void setBoolean(String key,Boolean value) {
		this.booleans.put(key, value);
	}

	public GameObject getObject(String key) {
		return Objects.get(key);
	}

	public void setObject(String key,GameObject value) {
		this.Objects.put(key, value);
	}
	public Direction getDirection(String key) {
		return directions.get(key);
	}

	public void setDirection(String key,Direction value) {
		this.directions.put(key, value);
	}
	public Double getDouble(String key) {
		return doubles.get(key);
	}

	public void setDouble(String key,Double value) {
		this.doubles.put(key, value);
	}
}
