package asteroids.model.expressions;

import asteroids.model.Ship;

public class GetVY extends Property {

	public GetVY(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(ship.getVelocity().getYComponent());
	}

}
