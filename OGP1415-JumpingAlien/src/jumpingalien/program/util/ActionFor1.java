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
	SELF{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<GameObject>(expression.getProgram().getGameObject());
			}else
				return new Value<Object>(null);
		}
	},
	READ{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			String variableName = (String)value.evaluate(new double[]{Double.POSITIVE_INFINITY});
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return value.getProgram().getVariable(variableName);
			}else
				return new Value<Object>(null);
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
				return new Value<Object>(null);
			
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
				return new Value<Object>(null);
			
		}
	},
	GETX{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Double>((Double)(double)(variable).getPosition().getPixelPosition()[0]);
			}else
				return new Value<Object>(null);
		}
	},
	GETY{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Double>((Double)(double)(variable).getPosition().getPixelPosition()[1]);
			}else
				return new Value<Object>(null);
		}
	},
	GETWIDTH{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			try{//TODO: remove try catch zodra getter gefixed is
				GameObject variable =(GameObject)value.evaluate(dt);
				if(dt[0]>0){
					expression.setDoneTrue(dt);
					return new Value<Double>((Double)(double)(variable).getSize()[0]);
				}else
					return new Value<Object>(null);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Double>();
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
					return new Value<Double>((Double)(double)(variable).getSize()[1]);
				}else
					return new Value<Object>(null);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Double>();
			}
		}
	},
	GETHP{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Double>((Double)(double)(variable).getNbHitPoints());
			}else
				return new Value<Object>(null);
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
					break;
				case RIGHT:
					closestGameObject = ClosestRight(expression);
					break;
				case UP:
					closestGameObject = ClosestUp(expression);
					break;
				case DOWN:
					closestGameObject = ClosestDown(expression);
					break;
				}
				return new Value<GameObject>(closestGameObject);
			}else
				return new Value<Object>(null);
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
				return new Value<Object>(null);
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
				return new Value<Object>(null);
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
				return new Value<Object>(null);
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
				return new Value<Object>(null);
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
				return new Value<Object>(null);
		}
	},
	ISDUCKING{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isDucking());
			}else
				return new Value<Object>(null);
		}
	},
	ISJUMPING{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt){
			Value<?> value = (Value<?>)expression.getExpressions()[0];
			GameObject variable = (GameObject)value.evaluate(dt);
			if(dt[0]>0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>((variable).isJumping());
			}else
				return new Value<Object>(null);
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
				return new Value<Object>(null);
		}
	};
	
	public abstract Value<?> evaluate(Expression<?,? extends Value<?>> expression, double[] dt);
}
