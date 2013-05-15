package asteroids.model.programs.parsing;

import java.util.List;

import asteroids.model.expressions.*;
import asteroids.model.statements.*;
import asteroids.model.types.*;

public class ProgramFactoryImpl implements ProgramFactory<E,S,T> {

	@Override
	public E createDoubleLiteral(int line, int column, double d) {
		return new DoubleLiteral(line,column,d);
	}

	@Override
	public E createBooleanLiteral(int line, int column, boolean b) {
		return new BooleanLiteral(line,column,b);
	}

	@Override
	public E createAnd(int line, int column, E e1, E e2) {
		return new And(line,column,e1,e2);
	}

	@Override
	public E createOr(int line, int column, E e1, E e2) {
		return new Or(line,column,e1,e2);
	}

	@Override
	public E createNot(int line, int column, E e) {
		return new Not(line,column,e);
	}

	@Override
	public E createNull(int line, int column) {
		return new Null(line,column);
	}

	@Override
	public E createSelf(int line, int column) {
		return new Self(line,column);
	}

	@Override
	public E createGetX(int line, int column, E e) {
		return new GetX(line,column,e);
	}

	@Override
	public E createGetY(int line, int column, E e) {
		return new GetY(line,column,e);
	}

	@Override
	public E createGetVX(int line, int column, E e) {
		return new GetVX(line,column,e);
	}

	@Override
	public E createGetVY(int line, int column, E e) {
		return new GetVY(line,column,e);
	}

	@Override
	public E createGetRadius(int line, int column, E e) {
		return new GetRadius(line,column,e);
	}

	@Override
	public E createVariable(int line, int column, String name) {
		return new Variable(line,column,name);
	}

	@Override
	public E createLessThan(int line, int column, E e1, E e2) {
		return new LessThan(line,column,e1,e2);
	}

	@Override
	public E createGreaterThan(int line, int column, E e1, E e2) {
		return new GreaterThan(line,column,e1,e2);
	}

	@Override
	public E createLessThanOrEqualTo(int line, int column, E e1, E e2) {
		return new LessThanOrEqualTo(line,column,e1,e2);
	}

	@Override
	public E createGreaterThanOrEqualTo(int line, int column, E e1, E e2) {
		return new GreaterThanOrEqualTo(line,column,e1,e2);
	}

	@Override
	public E createEquality(int line, int column, E e1, E e2) {
		return new Equality(line,column,e1,e2);
	}

	@Override
	public E createInequality(int line, int column, E e1, E e2) {
		return new Inequality(line,column,e1,e2);
	}

	@Override
	public E createAdd(int line, int column, E e1, E e2) {
		return new Add(line,column,e1,e2);
	}

	@Override
	public E createSubtraction(int line, int column, E e1, E e2) {
		return new Subtraction(line,column,e1,e2);
	}

	@Override
	public E createMul(int line, int column, E e1, E e2) {
		return new Mul(line,column,e1,e2);
	}

	@Override
	public E createDivision(int line, int column, E e1, E e2) {
		return new Division(line,column,e1,e2);
	}

	@Override
	public E createSqrt(int line, int column, E e) {
		return new Sqrt(line,column,e);
	}

	@Override
	public E createGetDirection(int line, int column) {
		return new GetDirection(line,column);
	}

	@Override
	public E createSin(int line, int column, E e) {
		return new Sin(line,column,e);
	}

	@Override
	public E createCos(int line, int column, E e) {
		return new Cos(line,column,e);
	}

	@Override
	public S createEnableThruster(int line, int column) {
		return new EnableThruster(line,column);
	}

	@Override
	public S createDisableThruster(int line, int column) {
		return new DisableThruster(line,column);
	}

	@Override
	public S createFire(int line, int column) {
		return new Fire(line,column);
	}

	@Override
	public S createTurn(int line, int column, E angle) {
		return new Turn(line,column,angle);
	}

	@Override
	public S createAssignment(int line, int column, String variable, E rhs) {
		return new Assignment(line,column,variable,rhs);
	}

	@Override
	public S createIf(int line, int column, E condition, S then, S otherwise) {
		return new If(line,column,condition,then,otherwise);
	}

	@Override
	public S createWhile(int line, int column, E condition, S body) {
		return new While(line,column,condition,body);
	}

	@Override
	public S createForeach(int line, int column,
			asteroids.model.programs.parsing.ProgramFactory.ForeachType type,
			String variableName, S body) {
		return new Foreach(line,column,type,variableName,body);
	}

	@Override
	public S createSkip(int line, int column) {
		return new Skip(line,column);
	}

	@Override
	public S createSequence(int line, int column, List<S> statements) {
		return new Sequence(line,column,statements);
	}

	@Override
	public S createPrint(int line, int column, E e) {
		return new Print(line,column,e);
	}

	@Override
	public T createDoubleType() {
		return new DoubleT();
	}

	@Override
	public T createBooleanType() {
		return new BooleanT();
	}

	@Override
	public T createEntityType() {
		return new EntityT();
	}
	
}
