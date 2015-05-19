package jumpingalien.program.util;

import java.util.ArrayList;
import java.util.Random;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.TileObject;
import jumpingalien.model.Program.Direction;
import jumpingalien.program.internal.Value;

public enum ActionFor1 {
	SQRT{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Double>(Math.sqrt((double)expression.evaluate(dt)));
		}
	},
	NEGATION{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(!(Boolean)expression.evaluate(dt));
		}
	},
	GETX{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getPosition().getPixelPosition()[0]);
		}
	},
	GETY{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getPosition().getPixelPosition()[1]);
		}
	},
	GETWIDTH{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			try{//TODO: remove try catch zodra getter gefixed is
				return new Value<Integer>(((GameObject)expression.evaluate(dt)).getSize()[0]);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHEIGHT{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			try{//TODO: remove try catch zodra getter gefixed is
				return new Value<Integer>(((GameObject)expression.evaluate(dt)).getSize()[1]);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHP{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getNbHitPoints());
		}
	},
	SEARCHOBJ{
		GameObject closestGameObject;
		
		public Value<?> evaluate(Value<?> expression, double[] dt){
			if ( ((Direction)expression.evaluate(dt)) == Direction.LEFT){
				closestGameObject = ClosestLeft(expression);
			}
			if (((Direction)expression.evaluate(dt)) == Direction.RIGHT){
				closestGameObject = ClosestRight(expression);
			}
			if (((Direction)expression.evaluate(dt)) == Direction.UP){
				closestGameObject = ClosestUp(expression);
			}
			if (((Direction)expression.evaluate(dt)) == Direction.DOWN){
				closestGameObject = ClosestDown(expression);
			}
			return new Value<GameObject>(closestGameObject);
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
	//TODO: controleren of dit direction MOET zijn, of ook ander object, en in richting van dat object
	ISDEAD{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	//TODO: ontbreekt er gene van de is<...> ?? terrain?
	ISPASSABLE{//TODO: implement this function
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(((TileObject)expression.evaluate(dt)).isPassable());
		}
	},
	ISWATER{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(((TileObject)expression.evaluate(dt)).isWater());
		}
	},
	ISMAGMA{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(((TileObject)expression.evaluate(dt)).isMagma());
		}
	},
	ISAIR{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(((TileObject)expression.evaluate(dt)).isAir());
		}
	},
	ISDUCKING{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isDucking());
		}
	},
	ISJUMPING{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isJumping());
		}
	},RANDOM{
		public Value<?> evaluate(Value<?> expression, double[] dt){
			Random rand = new Random();
			return new Value<Double>(rand.nextDouble()*(Double)expression.evaluate(dt));
		}
	};
	
	public abstract Value<?> evaluate(Value<?> expression, double[] dt);
}
