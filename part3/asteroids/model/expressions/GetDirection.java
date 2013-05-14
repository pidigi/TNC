package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.types.DoubleT;
import asteroids.model.types.T;

public class GetDirection extends Property {

	public GetDirection(int line, int column) {
		super(line, column, new Self(line, column));
//		throw new NullPointerException("Made GetDirection Object that is not defined properly.");
	}

	// TODO beter inpassen in structuur...
	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return String.valueOf(((Ship) getElement(tMap, eMap)).getAngle());
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return true;
	}

}