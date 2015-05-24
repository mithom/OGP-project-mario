package jumpingalien.program.statement.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import jumpingalien.model.Program.Direction;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;

public enum Category {
	WHILE{
		public void execute(Statement statement, double[] dt){
			while( dt[0]>0 && ! statement.isDone() && statement.getNoBreak()){
				Boolean variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
				if(variable != null ){
					if(variable){
					statement.getNextStatements()[0].addPreviousStatement(statement);
					statement.getNextStatements()[0].executeNext(dt);
					}
					else{
						statement.setDoneTrue();
						break;
					}
				}
			}
		};
	},
	ASSIGNMENT{
		public void execute(Statement statement, double[] dt){
			String variable1 = (String)statement.getExpressions()[0].evaluate(new double[]{Double.POSITIVE_INFINITY});
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
			}
		};
	},
	FOREACH{
		public void execute(Statement statement, double[] dt){
			
			Iterator<? extends GameObject> iterator = statement.getIterObjects();
			String variable = (String)statement.getExpressions()[0].evaluate(new double[]{Double.POSITIVE_INFINITY});
			if(iterator==null){
				Kind kind = statement.getKind();
				ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
				World world = statement.getProgram().getGameObject().getWorld();
				switch(kind){
				case MAZUB:
					gameObjects.add(world.getMazub());
					break;
				case BUZAM:
					gameObjects.add(world.getBuzam());
					break;
				case SLIME:
					gameObjects.addAll(world.getSlimes());
					break;
				case SHARK:
					gameObjects.addAll(world.getSharks());
					break;
				case PLANT:
					gameObjects.addAll(world.getPlants());
					break;
				case ANY:
					gameObjects.addAll(world.getAllGameObjects());//falltrough to terrain to add terrain to GameObjects
				case TERRAIN:
					gameObjects.addAll(statement.getProgram().getGameObject().getWorld().getAllTileObjects());
					break;
				}
				gameObjects.removeIf(p -> {
					statement.getExpressions()[1].reset();
					assignVariable(variable,p,statement);
					return !(Boolean)statement.getExpressions()[1].evaluate(new double[]{Double.POSITIVE_INFINITY});
				});
				gameObjects.sort(new Comparator<GameObject>() {
					public int compare(GameObject a,GameObject b){
						statement.getExpressions()[2].reset();
						assignVariable(variable,a,statement);
						double valueA = (double)statement.getExpressions()[2].evaluate(new double[]{Double.POSITIVE_INFINITY});
						
						statement.getExpressions()[2].reset();
						assignVariable(variable,b,statement);
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
				if(iterator.hasNext()){
					assignVariable(variable,iterator.next(),statement);
				}
			}
			dt[0]-=0.001d;
			while(dt[0]>0 && !statement.isDone() && statement.getNoBreak()){
				
				statement.getNextStatements()[0].addPreviousStatement(statement);
				boolean done = statement.getNextStatements()[0].executeNext(dt);
				if(done){
					if(iterator.hasNext()){
						assignVariable(variable,iterator.next(),statement);
					}
					else{
						statement.setDoneTrue();
					}
				}
			}
		};
	},
	BREAK{
		public void execute(Statement statement, double[] dt){
			if(dt[0]>0){
				statement.setDoneTrue();
				dt[0]-=0.001d;
				Statement prev = statement.getPreviousStatement();
				while(prev.getCategory() != Category.WHILE && prev.getCategory() != Category.FOREACH){
					if(prev.getCategory()==Category.IF)
						prev.setDoneTrue();
					prev = prev.getPreviousStatement();
				}
				prev.BreakDone();
				Statement next = prev.getNextStatements()[1];
				next.addPreviousStatement(statement);
				next.executeNext(dt);
			}
		};
	},
	IF{
		public void execute(Statement statement, double[] dt){
			Boolean variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
			if(dt[0]>0){
				if(variable){
					statement.getNextStatements()[0].addPreviousStatement(statement);
					boolean done =statement.getNextStatements()[0].executeNext(dt);
					if(done)
						statement.setDoneTrue();
				}else{
					if(statement.getNextStatements()[1].getCategory() != null){
						statement.getNextStatements()[1].addPreviousStatement(statement);
						boolean done =statement.getNextStatements()[1].executeNext(dt);
						if(done)
							statement.setDoneTrue();
					}else{
						statement.setDoneTrue();
					}
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
		};
	};
	public abstract void execute(Statement statement, double[] dt);
	
	protected void assignVariable(String variable, GameObject p,Statement statement){
		Statement assignment = new Statement(ASSIGNMENT);
		assignment.addConditiond(new Value<String>(variable));
		assignment.addConditiond(new Value<GameObject>(p));
		assignment.setType(new Type(Type.type.OBJECT));
		assignment.noReset();
		assignment.addProgram(statement.getProgram());
		assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
	}
}
