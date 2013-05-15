package asteroids.model.statements;

import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.expressions.*;
import asteroids.model.types.*;

public class Assignment extends S{
	public Assignment(int line, int column, String variable, E rhs) {
		super(line,column);
		this.variable = variable;
		this.rhs = rhs;
	}
	
	private final String variable;
	private final E rhs;
	private Object rhsEval;
	
	@Override
	public Map<String, Object> updateGlobals(Map<String, Object> currentGlobals) {
		//TODO geen voorwaarden???
		currentGlobals.put(variable,rhsEval);
		return currentGlobals;
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
		// TODO: Niet echt goede code, maar kan geen strings zetten in de global expressions
		// want dan kan je geen verwijzingen naar elementen meer opslaan!! 
		if (rhs.getType(globalTypes) instanceof T) {
			this.rhsEval = rhs.evaluate(globalTypes, globalExpr);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean typeCheck(Map<String, T> globalTypes) {
		boolean corrVar = globalTypes.containsKey(this.variable);
		if (!corrVar)
			return false;
		boolean match = globalTypes.get(this.variable).equals(rhs.getType(globalTypes));
		boolean corrE = rhs.typeCheck(globalTypes);
		return match && corrE;
	}
}
