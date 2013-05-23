package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Or extends Logic {

	public Or(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public Boolean evaluate(Map<String,T> tMap, Map<String,Object> eMap) 
			throws IllegalArgumentException, NullPointerException{
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		boolean val1 = (Boolean) getE1().evaluate(tMap, eMap);
		boolean val2 = (Boolean) getE2().evaluate(tMap, eMap);
		return val1 || val2;
	}

}
