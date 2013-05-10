package asteroids.model.expressions;

import asteroids.model.*;

public class GetX extends Property {

	public GetX(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public String evaluate(Ship ship) {
		return String.valueOf(ship.getPosition().getXComponent());
	}

}
