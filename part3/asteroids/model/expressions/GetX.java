package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class GetX extends Property {

	public GetX(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return String.valueOf(getElement(tMap, eMap).getPosition().getXComponent());
	}

}
