package asteroids.model.statements;

import java.util.Map;
import asteroids.model.expressions.*;
import asteroids.model.types.T;

public class While extends S{
	public While(int line, int column, E condition, S body) {
		super(line,column);
		this.condition = condition;
		this.body = body;
	}
	
	public E getCondition() {
		return this.condition;
	}
	
	private final E condition;
	
	public S getBody() {
		return this.body;
	}
	
	private final S body;
	
	public boolean getConditionEval() {
		return this.conditionEval;
	}
	
	public void setConditionEval(boolean conditionEval) {
		this.conditionEval = conditionEval;
	}
	
	private boolean conditionEval;
	
	@Override
	public S getStatement(int line,int column) {
		if (line == this.getLine() && column <=  this.getColumn()) {
			return this;
		}
		S insideStat = this.getBody().getStatement(line,column);
		if (insideStat != null) {
			return insideStat;
		}
		if (line == this.getBody().getEndLine() && column <= this.getBody().getEndColumn()) {
				return this;
		}
		return null;
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
	public int updateLine() {
		if (this.getConditionEval()) {
			return this.getLine();
		} 
		return this.getBody().getEndLine();
	}
	
	@Override
	public int updateColumn() {
		if (this.getConditionEval()) {
			return this.getColumn() + 1;
		} 
		return this.getBody().getEndColumn() + 1;
	}
	
	@Override
	public boolean typeCheck(Map<String, T> globalTypes) 
			throws NullPointerException{
		boolean bodyCheck = getBody().typeCheck(globalTypes);
		if(!this.getCondition().typeCheck(globalTypes))
			return false;
		boolean	conditionCheck =  this.getCondition().getType(globalTypes).isBoolean();
		return bodyCheck && conditionCheck;
	}
}