package jumpingalien.program.util;

import jumpingalien.program.internal.Value;

public enum ActionFor2{//TODO check for overflow errors etc
	ADDITION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Double>((double)left.evaluate()+(double)right.evaluate());
		}
	},
	SUBSTRACTION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Double>((double)left.evaluate()-(double)right.evaluate());
		}
	},
	MULTIPLICATION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Double>((double)left.evaluate()*(double)right.evaluate());
		}
	},
	DIVISION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Double>((double)left.evaluate()/(double)right.evaluate());
		}
	},
	CONJUNCTION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((Boolean)left.evaluate() && (Boolean)right.evaluate());
		}
	},
	DISJUNCTION{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((Boolean)left.evaluate() || (Boolean)right.evaluate());
		}
	},
	LT{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() < (double)right.evaluate());
		}
	},
	LE{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() <= (double)right.evaluate());
		}
	},
	GT{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() > (double)right.evaluate());
		}
	},
	GE{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() >= (double)right.evaluate());
		}
	},
	EQ{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() == (double)right.evaluate());
		}
	},
	NE{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((double)left.evaluate() != (double)right.evaluate());
		}
	},
	ISINSTANCE{
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Boolean>((Class<?>)left.evaluate() == right.evaluate());
		}
	},// 2different types(e and class)
	ISMOVING{
		public Value<?> evaluate(Value<?> left,Value<?> right){//TODO: implement this function
			return new Value<Boolean>(true);
		}
	},// 2different types (e and direction)
	GETTILE{//TODO:implement this function when tileObject is ready
		public Value<?> evaluate(Value<?> left,Value<?> right){
			return new Value<Double>((double)left.evaluate()+(double)right.evaluate());
		}
	};
	
	public abstract Value<?> evaluate(Value<?> left,Value<?> right);
}
