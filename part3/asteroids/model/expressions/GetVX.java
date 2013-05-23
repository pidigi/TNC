package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class GetVX extends Property {

	public GetVX(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,Object> eMap) 
			throws IllegalArgumentException, NullPointerException{
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return ((SpatialElement) getE().evaluate(tMap, eMap)).getVelocity().getXComponent();
	}

}
