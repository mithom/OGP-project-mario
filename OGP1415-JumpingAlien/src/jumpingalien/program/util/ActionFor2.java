package jumpingalien.program.util;

import jumpingalien.program.internal.Value;

public enum ActionFor2{//TODO check for overflow errors etc
	ADDITION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Double>((double)left.evaluate(dt)+(double)right.evaluate(dt));
		}
	},
	SUBSTRACTION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Double>((double)left.evaluate(dt)-(double)right.evaluate(dt));
		}
	},
	MULTIPLICATION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Double>((double)left.evaluate(dt)*(double)right.evaluate(dt));
		}
	},
	DIVISION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Double>((double)left.evaluate(dt)/(double)right.evaluate(dt));
		}
	},
	CONJUNCTION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((Boolean)left.evaluate(dt) && (Boolean)right.evaluate(dt));
		}
	},
	DISJUNCTION{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((Boolean)left.evaluate(dt) || (Boolean)right.evaluate(dt));
		}
	},
	LT{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) < (double)right.evaluate(dt));
		}
	},
	LE{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) <= (double)right.evaluate(dt));
		}
	},
	GT{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) > (double)right.evaluate(dt));
		}
	},
	GE{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) >= (double)right.evaluate(dt));
		}
	},
	EQ{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) == (double)right.evaluate(dt));
		}
	},
	NE{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((double)left.evaluate(dt) != (double)right.evaluate(dt));
		}
	},
	ISINSTANCE{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Boolean>((Class<?>)left.evaluate(dt) == right.evaluate(dt));
		}
	},// 2different types(e and class)
	ISMOVING{
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){//TODO: implement this function
			return new Value<Boolean>(true);
		}
	},// 2different types (e and direction)
	GETTILE{//TODO:implement this function when tileObject is ready
		public Value<?> evaluate(Value<?> left,Value<?> right, double[] dt){
			return new Value<Double>((double)left.evaluate(dt)+(double)right.evaluate(dt));
		}
	};
	
	public abstract Value<?> evaluate(Value<?> left,Value<?> right, double[] dt);
}
