package expressions;
import java.util.ArrayList;

import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;


public class SearchObject extends Expression {
	
	public Direction direction;
	public double closestRange = 0.0d ;
	
	public SearchObject(Direction direction, SourceLocation sourceLocation){
		super( sourceLocation);
		this.direction= direction;
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public Object evaluate(){
		if (getDirection() == Direction.LEFT){
			closestRange = ClosestLeft();
		}
		if (getDirection() == Direction.RIGHT){
			closestRange = ClosestRight();
		}
		if (getDirection() == Direction.UP){
			closestRange = ClosestUp();
		}
		if (getDirection() == Direction.DOWN){
			closestRange = ClosestDown();
		}
		return closestRange;
	}
	
	
	public double ClosestLeft(){
		ArrayList<GameObject> gameObjects = World.getAllGameObjects();
		GameObject thisGameObject = this.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			double[] perimeters= gameObjects.get(i).getPerimeters();
			//bottom needs to be between bottom and top of this
			if (gameObjects.get(i).getPerimeters()[1]>thisGameObject.getPerimeters()[1]
					&& gameObjects.get(i).getPerimeters()[1] < thisGameObject.getPerimeters()[3]){
				//gameobject links van this 
				if (gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[0]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[2] - thisGameObject.getPerimeters()[0]))<closestRangeTillNow){
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[2] - thisGameObject.getPerimeters()[0]);
					}
				}
			}
		}
		return closestRangeTillNow ;
	}
	
	public double ClosestRight(){
		ArrayList<GameObject> gameObjects = World.getAllGameObjects();
		GameObject thisGameObject = this.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			double[] perimeters= gameObjects.get(i).getPerimeters();
			//bottom needs to be between bottom and top of this
			if (gameObjects.get(i).getPerimeters()[1]>thisGameObject.getPerimeters()[1]
					&& gameObjects.get(i).getPerimeters()[1] < thisGameObject.getPerimeters()[3]){
				//gameobject rechts van this 
				if (gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[0]){
					if ((gameObjects.get(i).getPerimeters()[0] - thisGameObject.getPerimeters()[2])<closestRangeTillNow){
						closestRangeTillNow= gameObjects.get(i).getPerimeters()[0] - thisGameObject.getPerimeters()[2] ;
					}
				}
			}
		}
		return closestRangeTillNow ;
	}
	
	public double ClosestUp(){
		ArrayList<GameObject> gameObjects = World.getAllGameObjects();
		GameObject thisGameObject = this.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			double[] perimeters= gameObjects.get(i).getPerimeters();
			//Left needs to be between Left and Right of this
			if (gameObjects.get(i).getPerimeters()[0]>thisGameObject.getPerimeters()[0]
					&& gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[2]){
				//gameobject above this 
				if (gameObjects.get(i).getPerimeters()[3] < thisGameObject.getPerimeters()[3]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[1] - thisGameObject.getPerimeters()[3]))<closestRangeTillNow){
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[1] - thisGameObject.getPerimeters()[3]);
					}
				}
			}
		}
		return closestRangeTillNow ;
	}
	
	public double ClosestDown(){
		ArrayList<GameObject> gameObjects = World.getAllGameObjects();
		GameObject thisGameObject = this.getProgram().getGameObject();
		double closestRangeTillNow = Double.POSITIVE_INFINITY;
		for (int i = 0; i < gameObjects.size(); i++){
			double[] perimeters= gameObjects.get(i).getPerimeters();
			//Left needs to be between Left and Right of this
			if (gameObjects.get(i).getPerimeters()[0]>thisGameObject.getPerimeters()[0]
					&& gameObjects.get(i).getPerimeters()[0] < thisGameObject.getPerimeters()[2]){
				//gameobject is beneath this gameobject
				if (gameObjects.get(i).getPerimeters()[3] < thisGameObject.getPerimeters()[1]){
					if ((Math.abs(gameObjects.get(i).getPerimeters()[3] - thisGameObject.getPerimeters()[1]))<closestRangeTillNow){
						closestRangeTillNow= Math.abs(gameObjects.get(i).getPerimeters()[3] - thisGameObject.getPerimeters()[1]) ;
					}
				}
			}
		}
		return closestRangeTillNow ;
	}

}
	
