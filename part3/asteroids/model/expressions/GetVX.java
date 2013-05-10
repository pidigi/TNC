package asteroids.model.expressions;

import asteroids.model.Ship;

public class GetVX extends Property {

	public GetVX(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(ship.getVelocity().getXComponent());
	}

}
