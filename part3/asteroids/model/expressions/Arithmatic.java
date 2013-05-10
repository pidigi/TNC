package asteroids.model.expressions;

import asteroids.model.types.DoubleT;
import asteroids.model.types.T;

public abstract class Arithmatic extends BinaryE {

	public Arithmatic(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public T getType() {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType() {
		return (getE1().getType().isDouble() 
				&& getE2().getType().isDouble());
		// TODO is in principe hetzelfde als comparison
	}

}
