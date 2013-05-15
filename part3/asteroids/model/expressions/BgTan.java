package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class BgTan extends Function {

	public BgTan(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		double val = (Double) getE().evaluate(tMap, eMap);
		return Math.atan(val);
	}
}
