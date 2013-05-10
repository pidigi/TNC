package asteroids.model.expressions;

import asteroids.model.Ship;

public class GetRadius extends Property {

	public GetRadius(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(ship.getRadius());
	}

}
