package asteroids.model.expressions;

import asteroids.model.Ship;

public class Sqrt extends Function {

	public Sqrt(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		double val = Double.parseDouble(getE().evaluate(ship));
		return String.valueOf(Math.sqrt(val));
	}
}
