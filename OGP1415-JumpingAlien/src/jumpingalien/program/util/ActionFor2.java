package jumpingalien.program.util;

import jumpingalien.model.Program.Direction;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Value;
import jumpingalien.program.internal.Expression;

public enum ActionFor2{//TODO check for overflow errors etc
	ADDITION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(variable1 +variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
			
		}
	},
	SUBSTRACTION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(variable1 -variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	MULTIPLICATION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(variable1 *variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	DIVISION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(variable1 /variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	CONJUNCTION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Boolean variable1 = (Boolean)left.evaluate(dt);
			Boolean variable2 = (Boolean)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 && variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	DISJUNCTION{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Boolean variable1 = (Boolean)left.evaluate(dt);
			Boolean variable2 = (Boolean)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 || variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	LT{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			System.out.println("in LT met tijd: "+dt[0]+"en viriabelen: "+variable1+","+variable2);
			if(dt[0]>0.0){
				System.out.println("setting Done!!!");
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 < variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	LE{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 <= variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	GT{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 > variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	GE{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 >= variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	EQ{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Object variable1 = (Object)left.evaluate(dt);
			Object variable2 = (Object)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 == variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	NE{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Object variable1 = (Object)left.evaluate(dt);
			Object variable2 = (Object)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1 != variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},
	ISINSTANCE{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			GameObject variable1 = (GameObject)left.evaluate(dt);
			Class<?> variable2 = (Class<?>)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(variable1.getClass() == variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},// 2different types(e and class)
	ISMOVING{
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){//TODO: implement this function
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			
			GameObject variable1 = (GameObject)left.evaluate(dt);
			Direction variable2 = (Direction)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Boolean>(true);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	},// 2different types (e and direction)
	GETTILE{//TODO:implement this function when tileObject is ready
		public Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt){
			Value<?> left = (Value<?>)expression.getExpressions()[0];
			Value<?> right = (Value<?>)expression.getExpressions()[1];
			Double variable1 = (Double)left.evaluate(dt);
			Double variable2 = (Double)right.evaluate(dt);
			if(dt[0]>0.0){
				expression.setDoneTrue(dt);
				return new Value<Double>(variable1 +variable2);
			}else
				System.out.println("returned null with time: "+dt[0]);return new Value<Object>(null);
		}
	};
	
	public abstract Value<?> evaluate(Expression<?,? extends Value<?>> expression,double[] dt);
}
