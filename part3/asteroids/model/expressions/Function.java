package asteroids.model.expressions;

import asteroids.model.Ship;
import asteroids.model.types.*;

public abstract class Function extends UnitaryE {

	public Function(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public abstract String evaluate(Ship ship);

	@Override
	public T getType() {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType() {
		return getE().getType().isDouble();
	}

}
