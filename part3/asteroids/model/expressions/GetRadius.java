package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class GetRadius extends Property {

	public GetRadius(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,Object> eMap) 
			throws IllegalArgumentException, NullPointerException{
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		// TODO op null checken??? --> evt daarvoor try catch in program
		return ((SpatialElement) getE().evaluate(tMap, eMap)).getRadius();
	}
}
