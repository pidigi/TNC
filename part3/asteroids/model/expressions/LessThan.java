package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class LessThan extends Comparison {

	public LessThan(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}
	
	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		double val1 = Double.parseDouble(getE1().evaluate(tMap, eMap));
		double val2 = Double.parseDouble(getE2().evaluate(tMap, eMap));
		return String.valueOf(val1 < val2);
	}

}
