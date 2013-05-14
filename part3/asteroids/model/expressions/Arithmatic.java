package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.types.DoubleT;
import asteroids.model.types.T;

public abstract class Arithmatic extends BinaryE {
	public Arithmatic(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return (getE1().getType(tMap).isDouble() 
				&& getE2().getType(tMap).isDouble());
		// TODO is in principe hetzelfde als comparison
	}
}
