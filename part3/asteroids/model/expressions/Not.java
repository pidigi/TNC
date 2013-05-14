package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.BooleanT;
import asteroids.model.types.T;

public class Not extends UnitaryE {

	public Not(int line, int column, E e) {
		super(line, column, e);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		boolean val = Boolean.parseBoolean(getE().evaluate(tMap, eMap));
		return String.valueOf(!val);
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new BooleanT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return getE().getType(tMap).isBoolean();
	}

}