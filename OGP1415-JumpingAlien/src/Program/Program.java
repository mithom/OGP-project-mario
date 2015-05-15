package Program;

import java.util.HashMap;
import java.util.Map;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Type;

public class Program {

	public Program(){
		
	}
	
	public GameObject getGameObject(){
		return null;
	}
	
	public enum Direction {
		UP,LEFT,DOWN,RIGHT;
	}
	
	public HashMap<String, Boolean> booleans = new HashMap<String,Boolean>();
	public HashMap<String,GameObject> Objects = new HashMap<>();
	public HashMap<String,Double> doubles = new HashMap<>();
	public HashMap<String,Direction>  directions = new HashMap<>();

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
	
	public HashMap<String,Boolean> getAllBooleans(){
		return booleans;
	}
	
	public HashMap<String,GameObject> getAllObjects(){
		return Objects;
	}
	
	public HashMap<String,Direction> getAllDirections(){
		return directions;
	}
	
	public HashMap<String,Double> getAllDoubles(){
		return doubles;
	}
	 
	
}	
	
