package jumpingalien.program.statement.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import jumpingalien.model.Mazub;
import jumpingalien.model.Program.Direction;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;

public enum Category {
	WHILE{
		public void execute(Statement statement, double[] dt){
			//System.out.println(dt[0]+","+statement);
			while( dt[0]>0 && ! statement.isDone()){
				Boolean variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
				if(variable != null ){
					if(variable){
					//System.out.println("begin in while"+ dt[0]+","+statement);
					statement.getNextStatements()[0].addPreviousStatement(statement);
					statement.getNextStatements()[0].executeNext(dt);
					//System.out.println("einde in while: "+dt[0]+","+statement);
					}
					else{
						statement.setDoneTrue();
						//System.out.println("einde van de while lus");
						break;
					}
				}
			}
		};
	},
	ASSIGNMENT{
		public void execute(Statement statement, double[] dt){
			//System.out.println("assignment: "+ (String)statement.getExpressions()[0].evaluate(dt)+":="+statement.getType().getType());
			String variable1 = (String)statement.getExpressions()[0].evaluate(dt);
			switch(statement.getType().getType()){
			case BOOL:
				Boolean variable2 = (Boolean)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setBoolean(variable1,variable2);
				}
				break;
			case DIRECTION:
				Direction variable3 = (Direction)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setDirection(variable1,variable3);
				}
				break;
			case OBJECT:
				//System.out.println("printing in object: "+statement.getExpressions()[1].evaluate(dt));
				GameObject variable4 = (GameObject)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setObject(variable1,variable4);
				}
				break;
			case DOUBLE:
				Double variable5 = (Double)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setDouble(variable1,variable5);
				}
				break;
			}
			if(statement.getType()==null){
				//System.out.println("invalid type in assignment evaluation");
			}
		};
	},
	FOREACH{//TODO eerst evalueren, dan tijd controleren, dan pas uitvoeren!!!
		//TODO manier vinden om de staat waarin hij zich bevindt, op te slaan!
		public void execute(Statement statement, double[] dt){
			Iterator<? extends GameObject> iterator = statement.getIterObjects();
			if(iterator==null){
				Kind kind = statement.getKind();
				Type type = statement.getType();
				ArrayList<? extends GameObject> gameObjects;
				String variable = (String)statement.getExpressions()[0].evaluate(new double[]{Double.POSITIVE_INFINITY});
				World world = statement.getProgram().getGameObject().getWorld();
				switch(kind){
				case MAZUB:
					ArrayList<Mazub> temp = new ArrayList<Mazub>();
					temp.add(world.getMazub());
					gameObjects = temp;
					break;
				case BUZAM:
					//TODO: need to implement buzam first
					gameObjects = new ArrayList<GameObject>();
					break;
				case SLIME:
					gameObjects = world.getSlimes();
					break;
				case SHARK:
					gameObjects = world.getSharks();
					break;
				case PLANT:
					gameObjects = world.getPlants();
					break;
				case TERRAIN:
					//TODO need to implement terrain first
					gameObjects = new ArrayList<GameObject>();
					break;
				case ANY:
					gameObjects = world.getAllGameObjects();//TODO terrain has to be in here too!!!!!!
					break;
				default:
					gameObjects = world.getAllGameObjects();//TODO terrain has to be in here too!!!!!!
					break;
				}
				gameObjects.removeIf(p -> {
					statement.getExpressions()[1].reset();
					Statement assignment = new Statement(ASSIGNMENT);
					assignment.addConditiond(new Value<String>(variable));
					assignment.addConditiond(new Value<GameObject>(p));
					assignment.setType(type);
					assignment.noReset();
					assignment.addProgram(statement.getProgram());
					assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
					return !(Boolean)statement.getExpressions()[1].evaluate(new double[]{Double.POSITIVE_INFINITY});
				});
				gameObjects.sort(new Comparator<GameObject>() {
					public int compare(GameObject a,GameObject b){
						statement.getExpressions()[2].reset();
						Statement assignment = new Statement(ASSIGNMENT);
						assignment.addConditiond(new Value<String>(variable));
						assignment.addConditiond(new Value<GameObject>(a));
						assignment.setType(type);
						assignment.noReset();
						assignment.addProgram(statement.getProgram());
						assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
						double valueA = (double)statement.getExpressions()[2].evaluate(new double[]{Double.POSITIVE_INFINITY});
						
						statement.getExpressions()[2].reset();
						assignment = new Statement(ASSIGNMENT);
						assignment.addConditiond(new Value<String>(variable));
						assignment.addConditiond(new Value<GameObject>(b));
						assignment.setType(type);
						assignment.noReset();
						assignment.addProgram(statement.getProgram());
						assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
						double valueB = (double)statement.getExpressions()[2].evaluate(new double[]{Double.POSITIVE_INFINITY});
						if(valueA>valueB){
							if(statement.getSortAscending())
								return -1;
							else
								return 1;
						}if(valueB>valueA){
							if(statement.getSortAscending())
								return 1;
							else
								return -1;
						}return 0;
					}
				});
				iterator = gameObjects.iterator();
				statement.setIterObjects(iterator);
				if(iterator.hasNext())
					iterator.next();//create assignment here
			}
			dt[0]-=0.001d;
			//int i = 0;
			//while(i< gameObjects.size() && dt[0]>0.0d && ! statement.isDone()){
			while(dt[0]>0 && !statement.isDone()){
				
				statement.getNextStatements()[0].addPreviousStatement(statement);
				boolean done = statement.getNextStatements()[0].executeNext(dt);
				if(done){
					if(iterator.hasNext())
						iterator.next();//create assignment here
					else
						statement.setDoneTrue();
				}
			}
		};
	},
	BREAK{
		public void execute(Statement statement, double[] dt){
			if(dt[0]>0){
				//System.out.println("break");
				statement.setDoneTrue();
				dt[0]-=0.001d;
				Statement prev = statement.getPreviousStatement();
				while(prev.getCategory() != Category.WHILE || prev.getCategory() != Category.FOREACH)
					prev = prev.getPreviousStatement();
				prev.setDoneTrue();//TODO reset
				//prev.executeNext(dt);
				Statement next = prev.getNextStatements()[1];
				next.addPreviousStatement(statement);
				next.executeNext(dt);
			}else
				System.out.println("no time-> no break");
		};
	},
	IF{
		public void execute(Statement statement, double[] dt){
			Boolean variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
			if(dt[0]>0){
				if(variable){
					statement.getNextStatements()[0].addPreviousStatement(statement);
					boolean done =statement.getNextStatements()[0].executeNext(dt);
					//if(dt[0]>0)
					if(done)
						statement.setDoneTrue();
				}else{
					statement.setDoneTrue();
					if(statement.getNextStatements()[1] != null){
						boolean done =statement.getNextStatements()[1].executeNext(dt);
						//if(dt[0]>0)
						if(done)
							statement.setDoneTrue();
					}else
						statement.setDoneTrue();
				}
			}
		};
	},
	PRINT{
		public void execute(Statement statement, double[] dt){
			Object variable = statement.getExpressions()[0].evaluate(dt);
			if(dt[0]>0){
				statement.setDoneTrue();
				System.out.println(variable);
				dt[0]-=0.001d;
			}
		};
	},
	ACTION{
		public void execute(Statement statement, double[] dt){
			statement.getAction().execute(statement,dt);
			//System.out.println("action: "+statement.getAction());
		};
	}/*,
	SEQUENCE{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	}*/;
	public abstract void execute(Statement statement, double[] dt); 
}
