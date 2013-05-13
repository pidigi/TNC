package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.types.DoubleT;
import asteroids.model.types.T;

public class GetDirection extends E {

	public GetDirection(int line, int column) {
		super(line, column);
		throw new NullPointerException("Made GetDirection Object that is not defined properly.");
	}

	// TODO beter inpassen in structuur...
	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		EntityReference selfObject = new Self(getLine(), getColumn());
		return String.valueOf(((Ship) selfObject.getElement(tMap, eMap)).getAngle());
	}

	@Override
	public T getType() {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType() {
		return true;
	}

}
