package asteroids.model.expressions;

import asteroids.model.*;
import asteroids.model.types.*;

public class DoubleLiteral extends E {
	
	private final double d;

	public DoubleLiteral(int line, int column, double d) {
		super(line, column);
		this.d = d;
	}

	public double getDouble() {
		return d;
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(getDouble());
	}

	@Override
	public DoubleT getType() {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType() {
		return true;
	}

}
