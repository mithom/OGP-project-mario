package jumpingalien.part3.programs;

import java.util.List;
import java.util.Map;

import ogp.framework.game.Game;
import jumpingalien.program.internal.Expression;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;
import jumpingalien.program.statement.util.Action;
import jumpingalien.program.statement.util.Category;
import jumpingalien.program.util.ActionFor1;
import jumpingalien.program.util.ActionFor2;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.gameObject.GameObject;

public class ProgramFactory implements IProgramFactory<Value<?>, Statement, Type, Program> {

	@Override
	public Value<?> createReadVariable(String variableName, Type variableType,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Value<Double>(0.0d);
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
	public Statement createAssignment(String variableName, Type variableType, Value<?> value,
			SourceLocation sourceLocation) {
		Statement assignment = new Statement(Category.ASSIGNMENT);
		assignment.addConditiond(new Value<String>(variableName));
		assignment.addConditiond(value);
		assignment.setType(variableType);
		return assignment;
	}

	@Override
	public Statement createWhile(Value<?> condition, Statement body, SourceLocation sourceLocation) {
		Statement whileStatement = new Statement(Category.WHILE);
		whileStatement.addConditiond(condition);
		whileStatement.addNextStatement(body);
		return whileStatement;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Value<?> where,
			Value<?> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		if(where == null)
			where = new Value<Boolean>(true);
		if(sort == null){
			sort = new Value<Double>(0.0d);
		}
		Statement foreachStatement = new Statement(jumpingalien.program.statement.util.Kind.getCorrespondingKind(variableKind));
		foreachStatement.addConditiond(new Value<String>(variableName));
		foreachStatement.addConditiond(where);
		foreachStatement.addConditiond(sort);
		if(sortDirection==SortDirection.DESCENDING)
			foreachStatement.sortDescending();
		if(sortDirection==SortDirection.ASCENDING)
			foreachStatement.sortAscending();
		foreachStatement.addNextStatement(body);
		return foreachStatement;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new Statement(Category.BREAK);
	}

	@Override
	public Statement createIf(Value<?> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		Statement ifStatement = new Statement(Category.IF);
		ifStatement.addConditiond(condition);
		ifStatement.addNextStatement(ifStatement);
		ifStatement.addNextStatement(elseBody);
		return ifStatement;
	}

	@Override
	public Statement createPrint(Value<?> value, SourceLocation sourceLocation) {
		Statement printStatement = new Statement(Category.PRINT);
		printStatement.addConditiond(value);
		return printStatement;
	}

	@Override
	public Statement createStartRun(Value<?> direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STARTRUN);
	}

	@Override
	public Statement createStopRun(Value<?> direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STOPRUN);
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STARTJUMP);
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STOPJUMP);
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STARTDUCK);
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.STOPDUCK);
	}

	@Override
	public Statement createWait(Value<?> duration, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.WAIT);
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new Statement(Action.WAIT);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		for(int i =0;i<statements.size()-1;i++)
			statements.get(i).addNextStatement(statements.get(i+1));
		return statements.get(0);
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
	public Program createProgram(Statement mainStatement, Map<String, Type> globalVariables) {
		// TODO complete
		Program program = new Program();
		program.addAllGlobals(globalVariables);
		//addStatements after they are created
		program.addStatement(mainStatement);
		//mainStatement.addProgram(program);
		return program;
	}

}
