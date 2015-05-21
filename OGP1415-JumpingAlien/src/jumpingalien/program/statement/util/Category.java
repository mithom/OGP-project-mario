package jumpingalien.program.statement.util;

import java.util.ArrayList;
import java.util.Comparator;

import jumpingalien.model.Mazub;
import jumpingalien.model.Program.Direction;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;

public enum Category {//TODO eerst evalueren, dan tijd controleren, dan pas uitvoeren!!!
	WHILE{
		public void execute(Statement statement, double[] dt){
			Boolean variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
			System.out.println(dt[0]+","+statement);
			while( dt[0]>0 && variable && ! statement.isDone()){
				System.out.println("begin in while"+ dt[0]+","+statement);
				statement.getNextStatements()[0].addPreviousStatement(statement);
				statement.getNextStatements()[0].executeNext(dt);
				
				variable = (Boolean)statement.getExpressions()[0].evaluate(dt);
				System.out.println("einde in while: "+dt[0]+","+statement);
			}
			System.out.println("achter while: "+dt[0]);
			if(dt[0]>0){
				System.out.println("er was tijd over!");
				statement.setDoneTrue();
				//System.out.println("uit while, niet door tijd");
				/*statement.getNextStatements()[1].addPreviousStatement(statement);//dit gebeurt automatisch in executeNext!
				statement.getNextStatements()[1].executeNext(dt);
				*/
			}/*else{
				System.out.println("uit de while door tijd");
			}*/
		};
	},
	ASSIGNMENT{
		public void execute(Statement statement, double[] dt){
			System.out.println("assignment: "+ (String)statement.getExpressions()[0].evaluate(dt));
			String variable1 = (String)statement.getExpressions()[0].evaluate(dt);
			switch(statement.getType().getType()){
			case BOOL:
				Boolean variable2 = (Boolean)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setBoolean(variable1,variable2);
				}
			case DIRECTION:
				Direction variable3 = (Direction)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setDirection(variable1,variable3);
				}
			case OBJECT:
				GameObject variable4 = (GameObject)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setObject(variable1,variable4);
				}
			case DOUBLE:
				Double variable5 = (Double)statement.getExpressions()[1].evaluate(dt);
				if(dt[0]>0){
					statement.setDoneTrue();
					dt[0]-=0.001d;
					statement.getProgram().setDouble(variable1,variable5);
				}
			}
			if(statement.getType()==null){
				System.out.println("invalid type in assignment evaluation");
			}
		};
	},
	FOREACH{
		public void execute(Statement statement, double[] dt){//TODO moet met lambda fucnties
			Kind kind = statement.getKind();
			Type type = statement.getType();
			String variable = (String)statement.getExpressions()[0].evaluate(new double[]{Double.POSITIVE_INFINITY});
			World world = statement.getProgram().getGameObject().getWorld();
			ArrayList<? extends GameObject> gameObjects;
			switch(kind){
			case MAZUB:
				ArrayList<Mazub> temp = new ArrayList<Mazub>();
				temp.add(world.getMazub());
				gameObjects = temp;
			case BUZAM:
				//TODO: need to implement buzam first
				gameObjects = new ArrayList<GameObject>();
			case SLIME:
				gameObjects = world.getSlimes();
			case SHARK:
				gameObjects = world.getSharks();
			case PLANT:
				gameObjects = world.getPlants();
			case TERRAIN:
				//TODO need to implement terrain first
				gameObjects = new ArrayList<GameObject>();
			case ANY:
				gameObjects = world.getAllGameObjects();//TODO terrain has to be in here too!!!!!!
			default:
				gameObjects = world.getAllGameObjects();//TODO terrain has to be in here too!!!!!!
			}
			gameObjects.removeIf(p -> {
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
					statement.getExpressions()[1].reset();
					Statement assignment = new Statement(ASSIGNMENT);
					assignment.addConditiond(new Value<String>(variable));
					assignment.addConditiond(new Value<GameObject>(a));
					assignment.setType(type);
					assignment.noReset();
					assignment.addProgram(statement.getProgram());
					assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
					double valueA = (double)statement.getExpressions()[2].evaluate(new double[]{Double.POSITIVE_INFINITY});
					
					statement.getExpressions()[1].reset();
					assignment = new Statement(ASSIGNMENT);
					assignment.addConditiond(new Value<String>(variable));
					assignment.addConditiond(new Value<GameObject>(b));
					assignment.setType(type);
					assignment.noReset();
					assignment.addProgram(statement.getProgram());
					assignment.executeNext(new double[]{Double.POSITIVE_INFINITY});
					double valueB = (double)statement.getExpressions()[2].evaluate(new double[]{Double.POSITIVE_INFINITY});
					if(valueA>valueB)
						return 1;
					if(valueB>valueA)
						return -1;
					return 0;
				}
			});
			dt[0]-=0.001d;
			int i = 0;
			while(i< gameObjects.size() && dt[0]>0.0d && ! statement.isDone()){
				statement.getNextStatements()[0].addPreviousStatement(statement);
				statement.getNextStatements()[0].executeNext(dt);
				if(dt[0]>0)
					i++;
			}
			if(dt[0]>0)
				statement.setDoneTrue();
			
		};
	},
	BREAK{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
			Statement prev = statement.getPreviousStatement();
			while(prev.getCategory() != Category.WHILE || prev.getCategory() != Category.FOREACH)
				prev = prev.getPreviousStatement();
			prev.setDoneTrue();//TODO reset
			prev.executeNext(dt);
		};
	},
	IF{
		public void execute(Statement statement, double[] dt){
			if((boolean)statement.getExpressions()[0].evaluate(dt)){
				statement.getNextStatements()[0].addPreviousStatement(statement);
				statement.getNextStatements()[0].executeNext(dt);
				if(dt[0]>0)
					statement.setDoneTrue();
			}else{
				statement.setDoneTrue();
				if(statement.getNextStatements()[1] != null){
					statement.getNextStatements()[1].executeNext(dt);
					if(dt[0]>0)
						statement.setDoneTrue();
				}else
					statement.setDoneTrue();
			}
		};
	},
	PRINT{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			System.out.println(statement.getExpressions()[0].evaluate(dt));
			dt[0]-=0.001d;
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
