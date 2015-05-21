package jumpingalien.program.util;

import java.util.ArrayList;
import java.util.Random;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.TileObject;
import jumpingalien.model.Program.Direction;
import jumpingalien.program.internal.Expression;
import jumpingalien.program.internal.Value;

public enum ActionFor1 {
	READ{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			//if(value.getProgram().getGameObject().toString().contains("nr:2"))
				//System.out.println(value.evaluate(dt)+": "+value.getProgram().getVariable((String)value.evaluate(dt)).evaluate(dt));
			String variableName = (String)value.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return value.getProgram().getVariable(variableName);
			}else
				System.out.println("returned null with time: "+dt[0]);
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	SQRT{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			Double variable = (Double)value.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(Math.sqrt(variable));
			}else
				System.out.println("returned null with time: "+dt[0]);
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
			
		}
	},
	NEGATION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			Boolean variable = (Boolean)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(!variable);
			}else
				System.out.println("returned null with time: "+dt[0]);
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
			
		}
	},
	GETX{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Integer>((variable).getPosition().getPixelPosition()[0]);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	GETY{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Integer>((variable).getPosition().getPixelPosition()[1]);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	GETWIDTH{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			try{//TODO: remove try catch zodra getter gefixed is
				GameObject variable =(GameObject)value.evaluate(dt);
				if(dt[0]>0){
					expression.setDoneTrue(dt);
					return new Value<Integer>((variable).getSize()[0]);
				}else
					System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHEIGHT{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			try{//TODO: remove try catch zodra getter gefixed is
				GameObject variable = (GameObject)value.evaluate(dt);
				if(dt[0]>0){
					expression.setDoneTrue(dt);
					return new Value<Integer>((variable).getSize()[1]);
				}else
					System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHP{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Integer>((variable).getNbHitPoints());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	SEARCHOBJ{
		GameObject closestGameObject;
		
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			Direction variable= (Direction)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				switch(variable){
				case LEFT:
					closestGameObject = ClosestLeft(expression);
				case RIGHT:
					closestGameObject = ClosestRight(expression);
				case UP:
					closestGameObject = ClosestUp(expression);
				case DOWN:
					closestGameObject = ClosestDown(expression);
				}
				return new Value<GameObject>(closestGameObject);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
		
	
	public GameObject ClosestLeft(Value<?> expression){
		ArrayList<GameObject> gameObjects = expression.getProgram().getGameObject().getWorld().getAllGameObjects();
		GameObject thisGameObject = expression.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			//bottom needs to be between bottom and top of this
			if (gameObjects.get(i).getPerimeters()[1]>thisGameObject.getPerimeters()[1]
					&& gameObjects.get(i).getPerimeters()[1] < thisGameObject.getPerimeters()[3]){
				//gameobject links van this 
				if (gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[0]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[2] - thisGameObject.getPerimeters()[0]))<closestRangeTillNow){
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[2] - thisGameObject.getPerimeters()[0]);
						closestGameObject= gameObjects.get(i);
					}
				}
			}
		}
		return closestGameObject ;
	}
	
	public GameObject ClosestRight(Value<?> expression){
		ArrayList<GameObject> gameObjects = expression.getProgram().getGameObject().getWorld().getAllGameObjects();
		GameObject thisGameObject = expression.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			//bottom needs to be between bottom and top of this
			if (gameObjects.get(i).getPerimeters()[1]>thisGameObject.getPerimeters()[1]
					&& gameObjects.get(i).getPerimeters()[1] < thisGameObject.getPerimeters()[3]){
				//gameobject rechts van this 
				if (gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[0]){
					if ((gameObjects.get(i).getPerimeters()[0] - thisGameObject.getPerimeters()[2])<closestRangeTillNow){
						closestRangeTillNow= gameObjects.get(i).getPerimeters()[0] - thisGameObject.getPerimeters()[2] ;
						closestGameObject=gameObjects.get(i);
					}
				}
			}
		}
		return closestGameObject;
	}
	
	public GameObject ClosestUp(Value<?> expression){
		ArrayList<GameObject> gameObjects = expression.getProgram().getGameObject().getWorld().getAllGameObjects();
		GameObject thisGameObject = expression.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			//Left needs to be between Left and Right of this
			if (gameObjects.get(i).getPerimeters()[0]>thisGameObject.getPerimeters()[0]
					&& gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[2]){
				//gameobject above this 
				if (gameObjects.get(i).getPerimeters()[3] < thisGameObject.getPerimeters()[3]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[1] - thisGameObject.getPerimeters()[3]))<closestRangeTillNow){
						closestGameObject=gameObjects.get(i);
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[1] - thisGameObject.getPerimeters()[3]);
					}
				}
			}
		}
		return closestGameObject ;
	}
	
	public GameObject ClosestDown(Value<?> expression){
		ArrayList<GameObject> gameObjects = expression.getProgram().getGameObject().getWorld().getAllGameObjects();
		GameObject thisGameObject = expression.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			//Left needs to be between Left and Right of this
			if (gameObjects.get(i).getPerimeters()[0]>thisGameObject.getPerimeters()[0]
					&& gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[2]){
				//gameobject is beneath this gameobject
				if (gameObjects.get(i).getPerimeters()[3] < thisGameObject.getPerimeters()[1]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[3] - thisGameObject.getPerimeters()[1]))<closestRangeTillNow){
						closestGameObject=gameObjects.get(i);
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[3] - thisGameObject.getPerimeters()[1]) ;
					}
				}
			}
		}
		return closestGameObject ;
	}

	},
	ISDEAD{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable =(GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isDead());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISPASSABLE{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			TileObject variable = (TileObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isPassable());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISWATER{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			TileObject variable = (TileObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isWater());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISMAGMA{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			TileObject variable =(TileObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isMagma());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISAIR{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			TileObject variable = (TileObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isAir());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISDUCKING{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isDucking());//TODO: check if this has to be reversed or not (Wouter did this)
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISJUMPING{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){//TODO: check if this has to be reversed or not (Wouter did this)
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isJumping());
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},RANDOM{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			Random rand = new Random();
			Double variable = (Double)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Double>(rand.nextDouble()*variable);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	};
	
	public abstract Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt);
}
