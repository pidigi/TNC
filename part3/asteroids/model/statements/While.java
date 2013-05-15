package asteroids.model.statements;

import java.util.Map;
import asteroids.model.expressions.*;
import asteroids.model.types.BooleanT;
import asteroids.model.types.T;
import asteroids.model.*;

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
	public S getStatement(int line) {
		if (line == this.getBody().getEndLine()) {
			return this;
		}
		if (line == this.getLine()) {
			return this;
		}
		return body.getStatement(line);
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
		if (!(this.getCondition().getType(globalTypes) instanceof BooleanT)) {
			throw new IllegalArgumentException();
		}
		this.setConditionEval((Boolean) this.getCondition().evaluate(globalTypes,globalExpr));
	}

	@Override
	public int updateLine() {
		if (this.getConditionEval()) {
			return this.getLine()+1;
		} else {
			return this.getEndLine() + 1;
		}
	}
	
	@Override
	public boolean typeCheck(Map<String, T> globalTypes) {
		// TODO: typecheck van condition moet eerst, want anders ga je niet getType 
		// kunnen toepassen als condition niet in de lijst staat. Goed?
		boolean bodyCheck = getBody().typeCheck(globalTypes);
		if(!this.getCondition().typeCheck(globalTypes))
			return false;
		boolean	conditionCheck =  this.getCondition().getType(globalTypes).isBoolean();
		return bodyCheck && conditionCheck;
	}
}