package asteroids.model.expressions;

import java.util.Map;

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
	public T getType(Map<String,T> tMap) {
		return new BooleanT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) 
			throws NullPointerException{
		return (getE1().getType(tMap).isBoolean() 
				&& getE2().getType(tMap).isBoolean());
	}
}
