package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class Null extends EntityReference {

	public Null(int line, int column) {
		super(line, column);
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return "null";
	}

	@Override
	public SpatialElement getElement(Map<String, T> tMap, Map<String, E> eMap) {
		return null;
	}
}
