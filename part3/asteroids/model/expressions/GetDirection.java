package asteroids.model.expressions;

import asteroids.model.Ship;
import asteroids.model.types.T;

public class GetDirection extends E {

	public GetDirection(int line, int column) {
		super(line, column);
		throw new NullPointerException("Made GetDirection Object that is not defined properly.");
	}

	@Override
	public String evaluate(Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasValidType() {
		// TODO Auto-generated method stub
		return false;
	}

}
