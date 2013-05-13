package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Cos extends Function {
	
	public Cos(int line, int column, E e) {
		super(line, column, e);
	}
	
	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		double val = Double.parseDouble(getE().evaluate(tMap, eMap));
		return String.valueOf(Math.cos(val));
	}

}
