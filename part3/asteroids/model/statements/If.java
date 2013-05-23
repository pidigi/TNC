package asteroids.model.statements;

import java.util.Map;
import asteroids.model.expressions.E;
import asteroids.model.types.T;

public class If extends S{
	public If(int line, int column, E condition, S then, S otherwise) {
		super(line, column);
		this.condition = condition;
		this.then = then;
		this.otherwise = otherwise;
		this.simple = (otherwise.getLine() == this.getLine());
	}
	
	public E getCondition() {
		return this.condition;
	}
	
	private final E condition;
	
	public S getThen() {
		return this.then;
	}
	
	private final S then;
	
	public S getOtherwise() {
		return this.otherwise;
	}
	
	private final S otherwise;
	
	public boolean getSimple() {
		return this.simple;
	}
	
	private final boolean simple;
	
	public boolean getConditionEval() {
		return this.conditionEval;
	}
	
	private boolean conditionEval;
	
	public void setConditionEval(boolean conditionEval) {
		this.conditionEval = conditionEval;
	}
	
	public boolean getEnded() {
		return this.ended;
	}
	
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	private boolean ended = false;
	
	@Override
	public S getStatement(int line) {
		if (line == this.getLine()) {
			this.setEnded(false);
			return this;
		}
		if (this.getSimple() && line == this.getThen().getEndLine()) {
			this.setEnded(true);
			return this;
		}
		if (!this.getSimple() && this.getConditionEval() && line == this.getThen().getEndLine()) {
			this.setEnded(true);
			return this;
		}
		if (!this.getSimple() && line == this.getOtherwise().getEndLine()) {
			this.setEnded(true);
			return this;
		}
		S thenStatement = this.getThen().getStatement(line);
		if (thenStatement == null && !this.getSimple()) {
			return this.getOtherwise().getStatement(line);
		}
		return thenStatement;
	}
	
	@Override
	public int updateLine() {
		if (this.getEnded()) {
			if (this.getSimple()) {
				return this.getThen().getEndLine()+1;
			}
			return this.getOtherwise().getEndLine()+1;
		}
		if (!this.getConditionEval()) {
			if (this.getSimple()) {
				return this.getThen().getEndLine()+1;
			}
			return this.getOtherwise().getLine();
		}
		return this.getLine()+1;
	}
	
	@Override
	public void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr) 
			throws IllegalArgumentException, NullPointerException{
		if (!(this.getCondition().getType(globalTypes).isBoolean())) {
			throw new IllegalArgumentException();
		}
		this.setConditionEval((Boolean) this.getCondition().evaluate(globalTypes,globalExpr));
	}
	
	@Override
	public boolean typeCheck(Map<String, T> globalTypes) 
			throws NullPointerException{
		boolean bodyCheck = getThen().typeCheck(globalTypes) && getOtherwise().typeCheck(globalTypes);
		if (!this.getCondition().typeCheck(globalTypes))
			return false;
		boolean conditionCheck = this.getCondition().getType(globalTypes).isBoolean();
		return bodyCheck && conditionCheck;
	}
}
