package asteroids.model.expressions;

import asteroids.model.Ship;

public class Or extends Logic {

	public Or(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		boolean val1 = Boolean.parseBoolean(getE1().evaluate(ship));
		boolean val2 = Boolean.parseBoolean(getE1().evaluate(ship));
		return String.valueOf(val1 || val2);
	}

}
