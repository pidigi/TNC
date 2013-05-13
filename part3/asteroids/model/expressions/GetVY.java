package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class GetVY extends Property {

	public GetVY(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return String.valueOf(getElement(tMap, eMap).getVelocity().getYComponent());
	}

}
