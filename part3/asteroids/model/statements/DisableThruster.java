package asteroids.model.statements;

import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.expressions.E;
import asteroids.model.types.T;

public class DisableThruster extends Action{
	public DisableThruster(int line, int column) {
		super(line, column);
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		ship.setThrusterActive(false);
	}
}
