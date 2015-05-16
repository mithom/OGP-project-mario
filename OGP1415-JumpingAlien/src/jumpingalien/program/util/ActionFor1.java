package jumpingalien.program.util;

import java.util.Random;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Value;

public enum ActionFor1 {
	SQRT{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Double>(Math.sqrt((double)expression.evaluate(dt)));
		}
	},
	NEGATION{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(!(Boolean)expression.evaluate(dt));
		}
	},
	GETX{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getPosition().getPixelPosition()[0]);
		}
	},
	GETY{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getPosition().getPixelPosition()[1]);
		}
	},
	GETWIDTH{
		public Value<?> evaluate(Value<?> expression, double dt){
			try{//TODO: remove try catch zodra getter gefixed is
				return new Value<Integer>(((GameObject)expression.evaluate(dt)).getSize()[0]);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHEIGHT{
		public Value<?> evaluate(Value<?> expression, double dt){
			try{//TODO: remove try catch zodra getter gefixed is
				return new Value<Integer>(((GameObject)expression.evaluate(dt)).getSize()[1]);
			}catch(IllegalSizeException e){
				System.out.println("getter moet nog gefixed worden");
				return new Value<Integer>();
			}
		}
	},
	GETHP{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Integer>(((GameObject)expression.evaluate(dt)).getNbHitPoints());
		}
	},
	SEARCHOBJ{
		public Value<?> evaluate(Value<?> expression, double dt){//TODO: implement this function
			return new Value<GameObject>();
		}
	},//TODO: controleren of dit direction MOET zijn, of ook ander object, en in richting van dat object
	ISDEAD{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	ISPASSABLE{//TODO: implement this function
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	ISWATER{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	ISMAGMA{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	ISAIR{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isDead());
		}
	},
	ISDUCKING{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isDucking());
		}
	},
	ISJUMPING{
		public Value<?> evaluate(Value<?> expression, double dt){
			return new Value<Boolean>(!((GameObject)expression.evaluate(dt)).isJumping());
		}
	},RANDOM{
		public Value<?> evaluate(Value<?> expression, double dt){
			Random rand = new Random();
			return new Value<Double>(rand.nextDouble()*(Double)expression.evaluate(dt));
		}
	};
	
	public abstract Value<?> evaluate(Value<?> expression, double dt);
}
