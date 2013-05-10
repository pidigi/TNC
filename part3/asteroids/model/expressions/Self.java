package asteroids.model.expressions;

import asteroids.model.Ship;

public class Self extends EntityReference {

	public Self(int line, int column) {
		super(line, column);
	}

	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		return "self";
	}

}
