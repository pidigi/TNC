package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class GetX extends Property {

	public GetX(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public Double evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		// TODO op null checken???
		return ((SpatialElement) getE().evaluate(tMap, eMap)).getPosition().getXComponent();
	}

}
