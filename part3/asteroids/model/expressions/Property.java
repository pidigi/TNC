package asteroids.model.expressions;

import asteroids.model.types.*;

public abstract class Property extends UnitaryE {

	public Property(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public T getType() {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType() {
		return getE().getType().isEntity();
		// TODO exacte type checken via String compare? (wss niet)
	}

}
