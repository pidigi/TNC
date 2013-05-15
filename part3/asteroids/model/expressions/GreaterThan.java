package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class GreaterThan extends Comparison {

	public GreaterThan(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public Boolean evaluate(Map<String,T> tMap, Map<String,Object> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		double val1 = (Double) getE1().evaluate(tMap, eMap);
		double val2 = (Double) getE2().evaluate(tMap, eMap);
		return val1 > val2;
	}

}
