package asteroids.model.expressions;

import asteroids.model.*;

public class LessThan extends Comparison {

	public LessThan(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}
	
	@Override
	public String evaluate(Ship ship) {
		if(!hasValidType())
			throw new IllegalArgumentException();
		double val1 = Double.parseDouble(getE1().evaluate(ship));
		double val2 = Double.parseDouble(getE1().evaluate(ship));
		return String.valueOf(val1 < val2);
	}

}
