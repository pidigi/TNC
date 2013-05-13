package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Or extends Logic {

	public Or(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		boolean val1 = Boolean.parseBoolean(getE1().evaluate(tMap, eMap));
		boolean val2 = Boolean.parseBoolean(getE2().evaluate(tMap, eMap));
		return String.valueOf(val1 || val2);
	}

}
