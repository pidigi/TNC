package asteroids.model.expressions;

import asteroids.model.Ship;

public class Sin extends Function {

	public Sin(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		double val = Double.parseDouble(getE().evaluate(ship));
		return String.valueOf(Math.sin(val));
	}
}
