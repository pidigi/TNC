package asteroids.model.expressions;

import asteroids.model.Ship;

public class Null extends EntityReference {

	public Null(int line, int column) {
		super(line, column);
	}

	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		return "null";
	}

}
