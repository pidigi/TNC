package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.types.*;

public abstract class Function extends UnitaryE {

	public Function(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return getE().getType(tMap).isDouble();
	}

}
