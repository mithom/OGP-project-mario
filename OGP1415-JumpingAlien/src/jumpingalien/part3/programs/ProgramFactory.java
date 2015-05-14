package jumpingalien.part3.programs;

import java.util.List;
import java.util.Map;

import ogp.framework.game.Game;
import jumpingalien.program.internal.Expression;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;
import jumpingalien.program.util.ActionFor1;
import jumpingalien.program.util.ActionFor2;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.gameObject.GameObject;

public class ProgramFactory<S> implements IProgramFactory<Value<?>, S, Type, Program> {

	@Override
	public Value<?> createReadVariable(String variableName, Type variableType,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value<?> createDoubleConstant(double value, SourceLocation sourceLocation) {
		return new Value<Double>(value);
	}

	@Override
	public Value<?> createTrue(SourceLocation sourceLocation) {
		return new Value<Boolean>(true);
	}

	@Override
	public Value<?> createFalse(SourceLocation sourceLocation) {
		return new Value<Boolean>(false);
	}

	@Override
	public Value<?> createNull(SourceLocation sourceLocation) {
		return new Value<GameObject>(null);
	}

	@Override
	public Value<?> createSelf(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value<?> createDirectionConstant(Direction value,SourceLocation sourceLocation) {
		return new Value<Direction>(value);
	}

	@Override
	public Value<?> createAddition(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.ADDITION);
	}

	@Override
	public Value<?> createSubtraction(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.SUBSTRACTION);
	}

	@Override
	public Value<?> createMultiplication(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.MULTIPLICATION);
	}

	@Override
	public Value<?> createDivision(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.DIVISION);
	}

	@Override
	public Value<?> createSqrt(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)expr, ActionFor1.SQRT);
	}

	@Override
	public Value<?> createRandom(Value<?> maxValue, SourceLocation sourceLocation) {
		return new Expression<Double, Value<Double>>((Value<Double>)maxValue, ActionFor1.RANDOM);
	}

	@Override
	public Value<?> createAnd(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Boolean>>((Value<Boolean>)left, (Value<Boolean>)right, ActionFor2.CONJUNCTION);
	}

	@Override
	public Value<?> createOr(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Boolean>>((Value<Boolean>)left, (Value<Boolean>)right, ActionFor2.DISJUNCTION);
	}

	@Override
	public Value<?> createNot(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Boolean>>((Value<Boolean>)expr, ActionFor1.NEGATION);
	}

	@Override
	public Value<?> createLessThan(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.LT);
	}

	@Override
	public Value<?> createLessThanOrEqualTo(Value<?> left, Value<?> right,
			SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.LE);
	}

	@Override
	public Value<?> createGreaterThan(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.GT);
	}

	@Override
	public Value<?> createGreaterThanOrEqualTo(Value<?> left, Value<?> right,
			SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.GE);
	}

	@Override
	public Value<?> createEquals(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.EQ);
	}

	@Override
	public Value<?> createNotEquals(Value<?> left, Value<?> right, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<Double>>((Value<Double>)left, (Value<Double>)right, ActionFor2.NE);
	}

	@Override
	public Value<?> createGetX(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Integer, Value<GameObject>>((Value<GameObject>)expr, ActionFor1.GETX);
	}

	@Override
	public Value<?> createGetY(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Integer, Value<GameObject>>((Value<GameObject>)expr, ActionFor1.GETY);
	}

	@Override
	public Value<?> createGetWidth(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Integer, Value<GameObject>>((Value<GameObject>)expr, ActionFor1.GETWIDTH);
	}

	@Override
	public Value<?> createGetHeight(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Integer, Value<GameObject>>((Value<GameObject>)expr, ActionFor1.GETHEIGHT);
	}

	@Override
	public Value<?> createGetHitPoints(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Integer, Value<GameObject>>((Value<GameObject>)expr, ActionFor1.GETHP);
	}

	@Override
	public Value<?> createGetTile(Value<?> x, Value<?> y, SourceLocation sourceLocation) {
		return new Expression<Game, Value<Double>>((Value<Double>)x,(Value<Double>)y, ActionFor2.GETTILE);
	}

	@Override
	public Value<?> createSearchObject(Value<?> direction, SourceLocation sourceLocation) {
		return new Expression<GameObject, Value<Direction>>((Value<Direction>)direction, ActionFor1.SEARCHOBJ);
	}

	@Override
	public Value<?> createIsMazub(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,new Value<Class<?>>(Mazub.class), ActionFor2.ISINSTANCE);
	}

	@Override
	public Value<?> createIsShark(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,new Value<Class<?>>(Shark.class), ActionFor2.ISINSTANCE);
	}

	@Override
	public Value<?> createIsSlime(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,new Value<Class<?>>(Slime.class), ActionFor2.ISINSTANCE);
	}

	@Override
	public Value<?> createIsPlant(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,new Value<Class<?>>(Plant.class), ActionFor2.ISINSTANCE);
	}

	@Override
	public Value<?> createIsDead(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISDEAD);
	}

	@Override
	public Value<?> createIsTerrain(Value<?> expr, SourceLocation sourceLocation) {//TODO: change to terrain when object is ready
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,new Value<Class<?>>(Mazub.class), ActionFor2.ISINSTANCE);
	}

	@Override
	public Value<?> createIsPassable(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISPASSABLE);
	}

	@Override
	public Value<?> createIsWater(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISWATER);
	}

	@Override
	public Value<?> createIsMagma(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISMAGMA);
	}

	@Override
	public Value<?> createIsAir(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISAIR);
	}

	@Override
	public Value<?> createIsMoving(Value<?> expr, Value<?> direction, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr,(Value<Direction>)direction, ActionFor2.ISMOVING);
	}

	@Override
	public Value<?> createIsDucking(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISDUCKING);
	}

	@Override
	public Value<?> createIsJumping(Value<?> expr, SourceLocation sourceLocation) {
		return new Expression<Boolean, Value<?>>((Value<GameObject>)expr, ActionFor1.ISJUMPING);
	}

	@Override
	public S createAssignment(String variableName, Type variableType, Value<?> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createWhile(Value<?> condition, S body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Value<?> where,
			Value<?> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			S body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createIf(Value<?> condition, S ifBody, S elseBody,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createPrint(Value<?> value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartRun(Value<?> direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopRun(Value<?> direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createWait(Value<?> duration, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createSequence(List<S> statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getDoubleType() {
		return new Type(Type.type.DOUBLE);
	}

	@Override
	public Type getBoolType() {
		return new Type(Type.type.BOOL);
	}

	@Override
	public Type getGameObjectType() {
		return new Type(Type.type.OBJECT);
	}

	@Override
	public Type getDirectionType() {
		return new Type(Type.type.DIRECTION);
	}

	@Override
	public Program createProgram(S mainStatement, Map<String, Type> globalVariables) {
		// TODO complete
		Program program = new Program();
		program.addAllGlobals(globalVariables);
		//addStatements after they are created
		return null;
	}

}
