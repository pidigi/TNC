package asteroids.model.expressions;

import asteroids.model.Ship;

public class GetY extends Property {

	public GetY(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(ship.getPosition().getYComponent());
	}

}
