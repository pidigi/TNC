package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Sin extends Function {

	public Sin(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return Math.atan((Double) getE().evaluate(tMap, eMap));
	}
}
