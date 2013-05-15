package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Cos extends Function {
	
	public Cos(int line, int column, E e) {
		super(line, column, e);
	}
	
	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return Math.cos((Double) getE().evaluate(tMap, eMap));
	}

}
