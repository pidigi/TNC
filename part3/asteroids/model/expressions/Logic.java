package asteroids.model.expressions;

import asteroids.model.types.BooleanT;
import asteroids.model.types.T;

// The NOT operator is defined seperately since it only
// needs one argument. This class can be used later on if
// XOR / NAND / NOR gates would be introduced.

public abstract class Logic extends BinaryE{

	public Logic(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public T getType() {
		return new BooleanT();
	}

	@Override
	public boolean hasValidType() {
		return (getE1().getType().isBoolean() 
				&& getE2().getType().isBoolean());
	}


}
