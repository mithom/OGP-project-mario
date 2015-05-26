package jumpingalien.model;

import java.security.KeyException;
import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;
import jumpingalien.program.statement.util.Category;

public class Program {
	private GameObject gameObject;
	private Statement statement;
	private boolean error = false;
	public enum Direction {
		UP{
			public int getSign(){return 1;};
		},LEFT{
			public int getSign(){return -1;};
		},DOWN{
			public int getSign(){return -1;};
		},RIGHT{
			public int getSign(){return 1;};
		};
		public static Direction getEquivalent(IProgramFactory.Direction dir){
			switch(dir){
			case UP:
				return UP;
			case DOWN:
				return DOWN;
			case LEFT:
				return LEFT;
			case RIGHT:
				return RIGHT;
			default:
				System.out.println("should never happen");
				return RIGHT;
			}
		}
		public abstract int getSign();
	}
	
	private HashMap<String, Boolean> booleans = new HashMap<String,Boolean>();
	private HashMap<String,GameObject> objects = new HashMap<>();
	private HashMap<String,Direction> directions = new HashMap<>();
	private HashMap<String,Double> doubles = new HashMap<>();

	public Program(){
		
	}
	
	public void setError(){
		error = true;
	}
	
	public boolean hasError(){
		return error;
	}
	
	public void setGameObject(GameObject gameObject){//TODO add checkers
		this.gameObject = gameObject;
	}
	
	public GameObject getGameObject(){
		return gameObject;
	}
	
	private void executeNext(double[] dt){
		statement.executeNext(dt);
	}
	
	public double executeTime(double dt){
		double[] dt2 = new double[]{dt};
		while(dt2[0]>0 && !hasError()){
			executeNext(dt2);
		}
		return dt2[0];
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
			objects.put(text,null);
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
		for(String key:booleans.keySet()){
			booleans.put(key,null);
		}
		for(String key:doubles.keySet()){
			doubles.put(key,null);
		}
		for(String key:objects.keySet()){
			objects.put(key,null);
		}
		for(String key:directions.keySet()){
			directions.put(key,null);
		}
	}
	
	
	public Boolean getBoolean(String key) {
		return booleans.get(key);
	}

	public void setBoolean(String key,Boolean value)throws KeyException {
		if(!booleans.containsKey(key))
			throw new KeyException(key + "doesn't exist as boolean variable");
		this.booleans.put(key, value);
	}

	public GameObject getObject(String key) {
		return objects.get(key);
	}

	public void setObject(String key,GameObject value)throws KeyException {
		if(!objects.containsKey(key))
			throw new KeyException(key + "doesn't exist as gameObject variable");
		this.objects.put(key, value);
	}
	public Direction getDirection(String key) {
		return directions.get(key);
	}

	public void setDirection(String key,Direction value) throws KeyException {
		if(!directions.containsKey(key))
			throw new KeyException(key + "doesn't exist as direction variable");
		this.directions.put(key, value);
	}
	public Double getDouble(String key) {
		return doubles.get(key);
	}

	public void setDouble(String key,Double value) throws KeyException {
		if(!doubles.containsKey(key))
			throw new KeyException(key + "doesn't exist as double variable");
		this.doubles.put(key, value);
	}
	
	@Override
	public String toString(){
		String str = "globals... ";
		for(String bool:booleans.keySet())
			str+=bool+": " + booleans.get(bool);
		for(String bool:doubles.keySet())
			str+=bool+": " +doubles.get(bool);
		for(String bool:objects.keySet())
			str+=bool+": " +objects.get(bool);
		for(String bool:directions.keySet())
			str+=bool+": " +directions.get(bool);
		return str;
	}

	public Value<?> getVariable(String key){
		if(doubles.containsKey(key))
			return new Value<Double>(doubles.get(key));
		if(directions.containsKey(key))
			return new Value<Direction>(directions.get(key));
		if(booleans.containsKey(key))
			return new Value<Boolean>(booleans.get(key));
		if(objects.containsKey(key))
			return new Value<GameObject>(objects.get(key));
		return new Value<Object>(null);
	}
	
	public boolean isWellFormed(){
		return isStatementChainWellFormed(0, 0,statement, null);
	}
	
	public boolean isStatementChainWellFormed(int amountOfLoops, int amountOfFors,Statement toCheck, Statement prevStatement){
		if(toCheck.getCategory()==null){
			if(! (prevStatement.getCategory() == Category.IF && toCheck == prevStatement.getNextStatements()[1]))
				return false;
		}else{
			switch(toCheck.getCategory()){
			case BREAK://break moet in loop en einde van sequence zijn (anders heb je unreachable code)
				if(amountOfLoops<=0){
					return false;
				}
				for(Statement mustBeNull:toCheck.getNextStatements()){
					if(mustBeNull != null)
						return false;
				}
				break;
			case FOREACH:
				amountOfFors += 1;
			case WHILE://doorloop van while
				amountOfLoops+=1;
				break;
			case ACTION:
				if(amountOfFors>0)
					return false;
				break;
			default:
				break;
			}
		}
		for(int i=0;i<3;i++){
			Statement nextStatement = toCheck.getNextStatements()[i];
			if(nextStatement != null){
				if(toCheck.getCategory() == Category.WHILE || toCheck.getCategory()==Category.FOREACH){
					if(i==0){
						if(! isStatementChainWellFormed(amountOfLoops, amountOfFors,nextStatement, toCheck))
							return false;
					}else{
						if(toCheck.getCategory()==Category.FOREACH){
							if(! isStatementChainWellFormed(amountOfLoops-1, amountOfFors-1,nextStatement, toCheck))
								return false;
						}else
							if(! isStatementChainWellFormed(amountOfLoops-1, amountOfFors,nextStatement, toCheck))
								return false;
					}
				}else{
					if(! isStatementChainWellFormed(amountOfLoops, amountOfFors,nextStatement, toCheck))
						return false;
				}
			}
		}
		return true;
	}
}
