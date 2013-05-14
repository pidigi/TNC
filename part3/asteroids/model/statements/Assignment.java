package asteroids.model.statements;

import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.expressions.*;
import asteroids.model.types.BooleanT;
import asteroids.model.types.DoubleT;
import asteroids.model.types.*;
import asteroids.model.types.T;

public class Assignment extends S{
	public Assignment(int line, int column, String variable, E rhs) {
		super(line,column);
		this.variable = variable;
		this.rhs = rhs;
	}
	
	private final String variable;
	private final E rhs;
	private E rhsEval;
	
	@Override
	public Map<String, E> updateGlobals(Map<String, E> currentGlobals) {
		if (rhsEval != null) {
			currentGlobals.put(variable,rhsEval);
			return currentGlobals;
		} else {
			throw new NullPointerException();
		}
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		// TODO: Niet echt goede code, maar kan geen strings zetten in de global expressions
		// want dan kan je geen verwijzingen naar elementen meer opslaan!! 
		if (rhs.getType(globalTypes) instanceof DoubleT) {
			double res = Double.parseDouble(rhs.evaluate(globalTypes, globalExpr));
			this.rhsEval = new DoubleLiteral(0,0,res);
		} else if(rhs.getType(globalTypes) instanceof BooleanT) {
			boolean res = Boolean.parseBoolean(rhs.evaluate(globalTypes, globalExpr));
			this.rhsEval = new BooleanLiteral(0,0,res);
		} else if(rhs.getType(globalTypes) instanceof EntityT) {
			this.rhsEval = new SEReference(0,0,rhs.getElement(globalTypes,globalExpr));
		} else {
			throw new IllegalArgumentException();
		}
	}
}
